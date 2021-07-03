package controller;

import dao.ProductoDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Producto;
import model.Usuario;
import util.Enums.TipoProducto;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
@WebServlet(name = "ProductoController", urlPatterns = {"/ProductoController"})
public class ProductoController extends HttpServlet {

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
    
    // Si el usuario no tiene permiso se redirecciona
    if ((user == null) || !user.getTipo().isCanEditProducts()) {
      
      url = "/error.jsp";
      request.setAttribute("message", 
        "Tu cuenta no tiene permiso para acceder a este módulo.");
      request.setAttribute("returnLink", "/index.jsp");
      request.setAttribute("returnText", "Volver al inicio");
      getServletContext().getRequestDispatcher(url).forward(request, response);
      return;
      
    }
    
    switch (action) {
      case "eliminar": {
        
        String[] pids = request.getParameterValues("pid");
        ArrayList<Producto> selectedItems = new ArrayList<>();
        ProductoDAO productoDAO = new ProductoDAO();
        
        if (pids == null || pids.length < 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar al menos un producto para eliminar.");
          request.setAttribute("returnLink", "/producto/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de productos");
          break;
        }
        
        for (String pid : pids) {
          Producto p = productoDAO.buscar(Integer.parseInt(pid));
          selectedItems.add(p);
        }
        
        session.setAttribute("selectedItems", selectedItems);
        url = "/producto/eliminar.jsp";
        break;
      }
      
      case "confirmarEliminar": {
        
        ProductoDAO productoDAO = new ProductoDAO();
        ArrayList<Producto> selectedItems = (ArrayList<Producto>) session.getAttribute("selectedItems");
        
        for (Producto producto : selectedItems) {
          productoDAO.eliminar(producto.getId());
        }
        
        session.removeAttribute("selectedItems");
        
        url = "/producto/lista.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "nuevo": {
        url = "/producto/crear.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "editar": {
        String[] pids = request.getParameterValues("pid");
        
        if (pids == null || pids.length != 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar un único producto para editar.");
          request.setAttribute("returnLink", "/producto/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de productos");
          break;
        }
        
        int id = Integer.parseInt(pids[0]);
        ProductoDAO productoDAO = new ProductoDAO();
        Producto p = productoDAO.buscar(id);
        
        url = "/producto/crear.jsp";
        request.setAttribute("iPid",          id);
        request.setAttribute("iNombre",       p.getNombre());
        request.setAttribute("iDescripción",  p.getDescripción());
        request.setAttribute("iTipo",         p.getTipo());
        request.setAttribute("iPrecio",       p.getPrecio());
        request.setAttribute("iDescuento",    p.getDescuento());
        request.setAttribute("iStock",        p.getStock());
        request.setAttribute("modoEditar", true);
        break;
      }
      
      case "crearProducto": {
        
        String pPid         = request.getParameter("iPid");
        String pNombre      = request.getParameter("iNombre");
        String pDescripción = request.getParameter("iDescripción");
        String pTipo        = request.getParameter("iTipo");
        String pPrecio      = request.getParameter("iPrecio");
        String pDescuento   = request.getParameter("iDescuento");
        String pStock       = request.getParameter("iStock");
        
        int id = Integer.parseInt(pPid);
        
        // Si el producto existe, devolver la solicitud
        ProductoDAO productoDAO = new ProductoDAO();
        if (productoDAO.buscar(id) != null) {
          request.setAttribute("iPid", pPid);
          request.setAttribute("iNombre", pNombre);
          request.setAttribute("iDescripción", pDescripción);
          request.setAttribute("iTipo", pTipo);
          request.setAttribute("iPrecio", pPrecio);
          request.setAttribute("iDescuento", pDescuento);
          request.setAttribute("iStock", pStock);
          request.setAttribute("errProductoExiste", true);
          url = "/producto/crear.jsp";
          break;
        }
        
        TipoProducto tipo = TipoProducto.valueOf(pTipo);
        int precio        = Integer.parseInt(pPrecio);
        int descuento     = Integer.parseInt(pDescuento);
        int stock         = Integer.parseInt(pStock);
        
        Producto producto = new Producto(id, pNombre, pDescripción, tipo, precio, descuento, stock);
        boolean success = productoDAO.insertar(producto);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Producto creado");
          request.setAttribute("message",     "Se ha creado el nuevo producto.");
          request.setAttribute("returnLink",  "/producto/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de productos");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/producto/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al crear el producto. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/producto/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de productos");
          break;
        }
        
      }
      
      case "actualizarProducto": {
        
        String pPid         = request.getParameter("iPid");
        String pNombre      = request.getParameter("iNombre");
        String pDescripción = request.getParameter("iDescripción");
        String pTipo        = request.getParameter("iTipo");
        String pPrecio      = request.getParameter("iPrecio");
        String pDescuento   = request.getParameter("iDescuento");
        String pStock       = request.getParameter("iStock");
        
        TipoProducto tipo = TipoProducto.valueOf(pTipo);
        int id            = Integer.parseInt(pPid);
        int precio        = Integer.parseInt(pPrecio);
        int descuento     = Integer.parseInt(pDescuento);
        int stock         = Integer.parseInt(pStock);
        
        Producto producto = new Producto(id, pNombre, pDescripción, tipo, precio, descuento, stock);
        ProductoDAO productoDAO = new ProductoDAO();
        boolean success = productoDAO.actualizar(id, producto);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Producto creado");
          request.setAttribute("message",     "Se ha actualizado el producto.");
          request.setAttribute("returnLink",  "/producto/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de productos");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/producto/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al actualizar el producto. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/producto/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de productos");
          break;
        }
        
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
