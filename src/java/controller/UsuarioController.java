package controller;

import dao.UsuarioDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Usuario;
import util.Enums.TipoUsuario;
import static util.Enums.TipoUsuario.*;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
@MultipartConfig
@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
    // Establecer encoding a UTF-8 para capturar los parámetros correctamente.
    request.setCharacterEncoding("UTF-8");
    
    HttpSession session = request.getSession();
    Usuario user = (Usuario) session.getAttribute("user");
    
    String url = null;
    String action = request.getParameter("action");
    
    // Si el usuario no tiene permiso se redirecciona (a menos que intente registrarse)
    if (  (!action.equals("register")) && 
          (user == null || !user.getTipo().isCanEditUsers()) ) {
      
      url = "/error.jsp";
      request.setAttribute("message", 
        "Tu cuenta no tiene permiso para acceder a este módulo.");
      request.setAttribute("returnLink", "/index.jsp");
      request.setAttribute("returnText", "Volver al inicio");
      getServletContext().getRequestDispatcher(url).forward(request, response);
      return;
      
    }
    
    switch (action) {
      case "register": {
        
        // Si el usuario ya está logueado, no hacer nada.
        if (user != null && user.getTipo() != INVITADO) {
          response.sendRedirect("index.jsp");
          return;
        }
        
        String pRut = request.getParameter("iRut");
        String pNombre = request.getParameter("iNombre");
        String pApellido1 = request.getParameter("iApellido1");
        String pApellido2 = request.getParameter("iApellido2");
        String pBirthDate = request.getParameter("iBirthDate");
        String pEmail = request.getParameter("iEmail");
        
        String username = request.getParameter("iUsername");
        
        // Si el usuario existe, devolver la solicitud
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.buscar(username) != null) {
          request.setAttribute("iRut", pRut);
          request.setAttribute("iNombre", pNombre);
          request.setAttribute("iApellido1", pApellido1);
          request.setAttribute("iApellido2", pApellido2);
          request.setAttribute("iBirthDate", pBirthDate);
          request.setAttribute("iEmail", pEmail);
          request.setAttribute("errUserExists", true);
          url = "/register.jsp";
          break;
        }
        
        LocalDate fechaNacimiento = pBirthDate.equals("") ? 
          null : LocalDate.parse(pBirthDate, DateTimeFormatter.ISO_DATE);
        
        // Foto
        String nombreFoto = "";
        Part filePart           = request.getPart("iFoto");
        if (filePart.getSize() != 0) {
          String fileName         = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
          InputStream fileContent = filePart.getInputStream();

          //File folder = new File(getServletContext().getContextPath().concat("/img/users/"));
          File folder = new File(getServletContext().getRealPath("/img/users"));
          File file   = new File(folder, fileName);
          Files.copy(fileContent, file.toPath());
          nombreFoto = fileName;
        }
        
        user = new Usuario(pRut, pNombre, pApellido1, pApellido2, fechaNacimiento, pEmail, nombreFoto, 
          CLIENTE, username, null);
        
        byte[] password = request.getParameter("iPassword").getBytes(StandardCharsets.UTF_8);
        usuarioDAO.guardarContraseña(user, password);
        boolean success = usuarioDAO.insertar(user);
        
        // Enviar al nuevo usuario al inicio
        if (success) {
          session.setAttribute("user", user);
          
          url = "/success.jsp";
          request.setAttribute("title",       "Listo");
          request.setAttribute("message",     "Tu cuenta ha sido creada. Ahora puedes empezar a comprar :)");
          request.setAttribute("returnLink",  "/index.jsp");
          request.setAttribute("returnText",  "Ir al inicio");
          request.setAttribute("refreshTime", 8);
          request.setAttribute("refreshPage", "/index.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al crear tu cuenta. Por favor vuelve a intentarlo.");
          request.setAttribute("returnLink", "/register.jsp");
          request.setAttribute("returnText", "Volver al formulario");
          break;
        }
      }
      
      case "eliminar": {
        
        String[] usernames = request.getParameterValues("cUsername");
        ArrayList<Usuario> selectedItems = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        if (usernames == null || usernames.length < 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar al menos un usuario para eliminar.");
          request.setAttribute("returnLink", "/usuario/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de usuarios");
          break;
        }
        
        for (String username : usernames) {
          Usuario u = usuarioDAO.buscar(username);
          selectedItems.add(u);
        }
        
        session.setAttribute("selectedItems", selectedItems);
        url = "/usuario/eliminar.jsp";
        break;
      }
      
      case "confirmarEliminar": {
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<Usuario> selectedItems = (ArrayList<Usuario>) session.getAttribute("selectedItems");
        
        for (Usuario usuario : selectedItems) {
          usuarioDAO.eliminar(usuario.getUsername());
        }
        
        session.removeAttribute("selectedItems");
        
        url = "/usuario/lista.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "nuevo": {
        url = "/usuario/crear.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "editar": {
        String[] usernames = request.getParameterValues("cUsername");
        
        if (usernames == null || usernames.length != 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar un único usuario para editar.");
          request.setAttribute("returnLink", "/usuario/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de usuarios");
          break;
        }
        
        String username = usernames[0];
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.buscar(username);
        
        url = "/usuario/crear.jsp";
        request.setAttribute("iRut",        u.getRut());
        request.setAttribute("iNombre",     u.getNombre());
        request.setAttribute("iApellido1",  u.getApellidoPaterno());
        request.setAttribute("iApellido2",  u.getApellidoMaterno());
        request.setAttribute("iBirthDate",  u.getFechaNacimiento());
        request.setAttribute("iEmail",      u.getEmail());
        request.setAttribute("iTipo",       u.getTipo());
        request.setAttribute("iFoto",       u.getNombreFoto());
        request.setAttribute("iUsername",   username);
        request.setAttribute("modoEditar", true);
        break;
      }
      
      case "crearUsuario": {
        
        String pRut       = request.getParameter("iRut");
        String pNombre    = request.getParameter("iNombre");
        String pApellido1 = request.getParameter("iApellido1");
        String pApellido2 = request.getParameter("iApellido2");
        String pBirthDate = request.getParameter("iBirthDate");
        String pEmail     = request.getParameter("iEmail");
        String pTipo      = request.getParameter("iTipo");
        
        String username   = request.getParameter("iUsername");
        
        // Si el usuario existe, devolver la solicitud
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.buscar(username) != null) {
          request.setAttribute("iRut",        pRut);
          request.setAttribute("iNombre",     pNombre);
          request.setAttribute("iApellido1",  pApellido1);
          request.setAttribute("iApellido2",  pApellido2);
          request.setAttribute("iBirthDate",  pBirthDate);
          request.setAttribute("iEmail",      pEmail);
          request.setAttribute("iTipo",       pTipo);
          request.setAttribute("iUsername",   username);
          request.setAttribute("errUsuarioExiste", true);
          url = "/usuario/crear.jsp";
          break;
        }
        
        LocalDate fechaNacimiento = pBirthDate.equals("") ? 
          null : LocalDate.parse(pBirthDate, DateTimeFormatter.ISO_DATE);
        
        // Foto
        String nombreFoto = "";
        Part filePart           = request.getPart("iFoto");
        if (filePart.getSize() != 0) {
          String fileName         = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
          InputStream fileContent = filePart.getInputStream();

          //File folder = new File(getServletContext().getContextPath().concat("/img/users/"));
          File folder = new File(getServletContext().getRealPath("/img/users"));
          File file   = new File(folder, fileName);
          Files.copy(fileContent, file.toPath());
          nombreFoto = fileName;
        }
        
        user = new Usuario(pRut, pNombre, pApellido1, pApellido2, fechaNacimiento, pEmail, nombreFoto, 
          TipoUsuario.valueOf(pTipo), username, null);
        
        byte[] password = request.getParameter("iPassword").getBytes(StandardCharsets.UTF_8);
        usuarioDAO.guardarContraseña(user, password);
        boolean success = usuarioDAO.insertar(user);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Usuario creado");
          request.setAttribute("message",     "Se ha creado el nuevo usuario.");
          request.setAttribute("returnLink",  "/usuario/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de usuarios");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/usuario/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al crear el usuario. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/usuario/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de usuarios");
          break;
        }
        
      }
      
      case "actualizarUsuario": {
        
        String pRut       = request.getParameter("iRut");
        String pNombre    = request.getParameter("iNombre");
        String pApellido1 = request.getParameter("iApellido1");
        String pApellido2 = request.getParameter("iApellido2");
        String pBirthDate = request.getParameter("iBirthDate");
        String pEmail     = request.getParameter("iEmail");
        String pTipo      = request.getParameter("iTipo");
        String pUsername  = request.getParameter("iUsername");
        
        LocalDate fechaNacimiento = pBirthDate.equals("") ? 
          null : LocalDate.parse(pBirthDate, DateTimeFormatter.ISO_DATE);
        
        // Foto
        String nombreFoto = null;
        Part filePart           = request.getPart("iFoto");
        if (filePart.getSize() != 0) {
          String fileName         = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
          InputStream fileContent = filePart.getInputStream();

          //File folder = new File(getServletContext().getContextPath().concat("/img/users/"));
          File folder = new File(getServletContext().getRealPath("/img/users"));
          File file   = new File(folder, fileName);
          Files.copy(fileContent, file.toPath());
          nombreFoto = fileName;
        }
        
        Usuario usuarioNuevo = new Usuario(pRut, pNombre, pApellido1, pApellido2, fechaNacimiento, pEmail, 
          nombreFoto, TipoUsuario.valueOf(pTipo), pUsername, null);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscar(pUsername);
        
        usuarioNuevo.setVentas(usuario.getVentas());
        usuarioNuevo.setPasswordHash(usuario.getPasswordHash());
        
        if (nombreFoto == null) {
          usuarioNuevo.setNombreFoto(usuario.getNombreFoto());
        }
        
        boolean success = usuarioDAO.actualizar(pUsername, usuarioNuevo);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Usuario creado");
          request.setAttribute("message",     "Se ha creado el nuevo usuario.");
          request.setAttribute("returnLink",  "/usuario/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de usuarios");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/usuario/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al crear el usuario. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/usuario/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de usuarios");
          break;
        }
        
      }
      
      default: {
        throw new AssertionError();
      }
    }
    
    getServletContext().getRequestDispatcher(url).forward(request, response);
    
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
