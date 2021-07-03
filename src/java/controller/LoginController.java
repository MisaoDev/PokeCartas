package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import static util.Enums.TipoUsuario.*;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
    
    //  Establecer encoding a UTF-8 para capturar los parámetros correctamente.
    request.setCharacterEncoding("UTF-8");
    
    HttpSession session = request.getSession();
    Usuario user = (Usuario) session.getAttribute("user");
    
    String url = null;
    String action = request.getParameter("action");
    
    switch (action) {
      case "login": {
        
        //  Si el usuario ya está logueado, no hacer nada.
        if (user != null && user.getTipo() != INVITADO) {
          response.sendRedirect("/index.jsp");
          return;
        }
        
        String username = request.getParameter("iUsername");
        byte[] password = request.getParameter("iPassword").getBytes(StandardCharsets.UTF_8);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int loginResult = usuarioDAO.checkPassword(username, password);
        
        switch (loginResult) {
          case -1: {
            url = "/login.jsp";
            request.setAttribute("message", "El usuario ingresado no existe.");
            break;
          }
          
          case 0: {
            url = "/login.jsp";
            request.setAttribute("message", "La contraseña ingresada es incorrecta.");
            break;
          }
          
          case 1: {
            url = "/index.jsp";
            
            //  Capturar el objeto usuario y guardarlo en la sesión
            user = usuarioDAO.buscar(username);
            session.setAttribute("user", user);
            break;
          }
        }
        break;
      }
      
      case "logout": {
        user = new Usuario();
        user.setTipo(INVITADO);
        session.setAttribute("user", user);
        
        url = "/login.jsp";
        break;
      }
      
      case "register": {
        String username = request.getParameter("iUsername");
        
        url = String.format("/register.jsp?username=%s", username);
        response.sendRedirect(url);
        return;
      }
    }
    
    getServletContext().getRequestDispatcher(url).forward(request, response);
    return;
    
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
