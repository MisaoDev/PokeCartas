package controller;

import dao.ProductoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Producto;
import org.json.simple.JSONObject;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
@WebServlet(name = "AjaxController", urlPatterns = {"/AjaxController"})
public class AjaxController extends HttpServlet {

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
    
    request.setCharacterEncoding("UTF-8");
    
    JSONObject json = new JSONObject();
    String requestType = request.getParameter("requestType");
    
    switch (requestType) {
      case "TraerProducto": {
        
        String pid = request.getParameter("pid");
        int id = Integer.parseInt(pid);
        
        Producto p = new ProductoDAO().buscar(id);
        if (p != null) {
          
          json.put("id",           id);
          json.put("tipo",         p.getTipo().name());
          json.put("descripción",  p.getDescripción());
          json.put("precio",       p.getPrecio());
          json.put("descuento",    p.getDescuento());
          json.put("stock",        p.getStock());
          json.put("precioFinal",  p.getValorFinal());
          
        }
        
        break;
      }
      
      default: {
        throw new AssertionError();
      }
    }
    
    response.setContentType("application/json");
    response.getWriter().write(json.toJSONString());
    
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
