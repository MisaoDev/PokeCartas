package controller;

import dao.ProductoDAO;
import dao.VentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Producto;
import model.Venta;
import model.Usuario;
import util.Enums;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
@WebServlet(name = "VentaController", urlPatterns = {"/VentaController"})
public class VentaController extends HttpServlet {

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
    if (  (user == null) ||
          (action.equals("generarReporte") && !user.getTipo().isCanViewReports()) ||
          (!action.equals("generarReporte") && !user.getTipo().isCanEditOrders())
       ){
      
      url = "/error.jsp";
      request.setAttribute("message", "Tu cuenta no tiene permiso para acceder a este módulo.");
      request.setAttribute("returnLink", "/index.jsp");
      request.setAttribute("returnText", "Volver al inicio");
      getServletContext().getRequestDispatcher(url).forward(request, response);
      return;
      
    }
    
    switch (action) {
      case "eliminar": {
        
        String[] ids = request.getParameterValues("idVenta");
        ArrayList<Venta> selectedItems = new ArrayList<>();
        VentaDAO ventaDAO = new VentaDAO();
        
        if (ids == null || ids.length < 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar al menos una venta para eliminar.");
          request.setAttribute("returnLink", "/venta/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de ventas");
          break;
        }
        
        for (String id : ids) {
          Venta v = ventaDAO.buscar(Integer.parseInt(id));
          selectedItems.add(v);
        }
        
        session.setAttribute("selectedItems", selectedItems);
        url = "/venta/eliminar.jsp";
        break;
      }
      
      case "confirmarEliminar": {
        
        VentaDAO ventaDAO = new VentaDAO();
        ArrayList<Venta> selectedItems = (ArrayList<Venta>) session.getAttribute("selectedItems");
        
        for (Venta venta : selectedItems) {
          ventaDAO.eliminar(venta.getId());
        }
        
        session.removeAttribute("selectedItems");
        
        url = "/venta/lista.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "nuevo": {
        url = "/venta/crear.jsp";
        response.sendRedirect(url);
        return;
      }
      
      case "editar": {
        String[] ids = request.getParameterValues("idVenta");
        
        if (ids == null || ids.length != 1) {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Debe seleccionar una única venta para editar.");
          request.setAttribute("returnLink", "/venta/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de ventas");
          break;
        }
        
        int id = Integer.parseInt(ids[0]);
        VentaDAO ventaDAO = new VentaDAO();
        Venta v = ventaDAO.buscar(id);
        
        url = "/venta/crear.jsp";
        request.setAttribute("iId",             id);
        request.setAttribute("iUsername",       v.getUsername());
        request.setAttribute("iFecha",          v.getFecha());
        request.setAttribute("iHora",           v.getHora());
        request.setAttribute("iPrecioUnitario", v.getPrecioUnitario());
        request.setAttribute("iCantidad",       v.getCantidad());
        request.setAttribute("iTotal",          v.getPrecioUnitario() * v.getCantidad());
        
        Producto p = v.getProducto();
        request.setAttribute("iPid",                  p.getId());
        request.setAttribute("iProductoTipo",         p.getTipo());
        request.setAttribute("iProductoDescripción",  p.getDescripción());
        request.setAttribute("iProductoPrecio",       p.getPrecio());
        request.setAttribute("iProductoDescuento",    p.getDescuento());
        request.setAttribute("iProductoStock",        p.getStock());
        
        request.setAttribute("modoEditar", true);
        break;
      }
      
      case "crearVenta": {
        
        String pId              = request.getParameter("iId");
        String pUsername        = request.getParameter("iUsername");
        String pFecha           = request.getParameter("iFecha");
        String pHora            = request.getParameter("iHora");
        String pPid             = request.getParameter("iProducto");
        String pPrecioUnitario  = request.getParameter("iPrecioUnitario");
        String pCantidad        = request.getParameter("iCantidad");
        
        int id  = Integer.parseInt(pId);
        int pid = Integer.parseInt(pPid);
        
        // Si la venta existe, devolver la solicitud
        VentaDAO ventaDAO = new VentaDAO();
        if (ventaDAO.buscar(id) != null) {
          request.setAttribute("iId",             id);
          request.setAttribute("iUsername",       pUsername);
          request.setAttribute("iFecha",          pFecha);
          request.setAttribute("iHora",           pHora);
          request.setAttribute("iPrecioUnitario", pPrecioUnitario);
          request.setAttribute("iCantidad",       pCantidad);
          request.setAttribute("errVentaExiste", true);
          url = "/venta/crear.jsp";
          break;
        }
        
        LocalDate fecha = LocalDate.parse(pFecha);
        LocalTime hora  = LocalTime.parse(pHora);
        int precioUnitario  = Integer.parseInt(pPrecioUnitario);
        int cantidad        = Integer.parseInt(pCantidad);
        Producto producto = new ProductoDAO().buscar(pid);
        
        Venta venta = new Venta(id, pUsername, fecha, hora, producto, precioUnitario, cantidad);
        boolean success = ventaDAO.insertar(venta);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Venta creada");
          request.setAttribute("message",     "Se ha creado la venta.");
          request.setAttribute("returnLink",  "/venta/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de ventas");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/venta/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al crear la venta. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/venta/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de ventas");
          break;
        }
        
      }
      
      case "actualizarVenta": {
        
        String pId              = request.getParameter("iId");
        String pUsername        = request.getParameter("iUsername");
        String pFecha           = request.getParameter("iFecha");
        String pHora            = request.getParameter("iHora");
        String pPid             = request.getParameter("iProducto");
        String pPrecioUnitario  = request.getParameter("iPrecioUnitario");
        String pCantidad        = request.getParameter("iCantidad");
        
        int id  = Integer.parseInt(pId);
        int pid = Integer.parseInt(pPid);
        LocalDate fecha = LocalDate.parse(pFecha);
        LocalTime hora  = LocalTime.parse(pHora);
        int precioUnitario  = Integer.parseInt(pPrecioUnitario);
        int cantidad        = Integer.parseInt(pCantidad);
        Producto producto = new ProductoDAO().buscar(pid);
        
        Venta venta = new Venta(id, pUsername, fecha, hora, producto, precioUnitario, cantidad);
        VentaDAO ventaDAO = new VentaDAO();
        boolean success = ventaDAO.actualizar(id, venta);
        
        if (success) {
          url = "/success.jsp";
          request.setAttribute("title",       "Venta creado");
          request.setAttribute("message",     "Se ha actualizado la venta.");
          request.setAttribute("returnLink",  "/venta/lista.jsp");
          request.setAttribute("returnText",  "Volver a la lista de ventas");
          request.setAttribute("refreshTime", 4);
          request.setAttribute("refreshPage", "/venta/lista.jsp");
          break;
          
        } else {
          url = "/error.jsp";
          request.setAttribute("message", 
            "Ha ocurrido un error al actualizar la venta. Por favor vuelva a intentarlo.");
          request.setAttribute("returnLink", "/venta/lista.jsp");
          request.setAttribute("returnText", "Volver a la lista de ventas");
          break;
        }
        
      }
      
      case "generarReporte": {
        String pFechaDesde = request.getParameter("iFechaDesde");
        String pFechaHasta = request.getParameter("iFechaHasta");
        LocalDate fechaDesde = LocalDate.parse(pFechaDesde);
        LocalDate fechaHasta = LocalDate.parse(pFechaHasta);
        
        ArrayList<Venta> listaVentas = new ArrayList<>();
        
        for (Venta venta : new VentaDAO().getList()) {
          LocalDate fecha = venta.getFecha();
          
          if (  (fecha.isEqual(fechaDesde) || fecha.isAfter(fechaDesde)) && 
                (fecha.isEqual(fechaHasta) || fecha.isBefore(fechaHasta)) ) {
            listaVentas.add(venta);
          }
          
        }
        
        url = "/venta/lista.jsp";
        request.setAttribute("isReporte",   true);
        request.setAttribute("listaVentas", listaVentas);
        request.setAttribute("fechaDesde",  fechaDesde);
        request.setAttribute("fechaHasta",  fechaHasta);
        break;
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
