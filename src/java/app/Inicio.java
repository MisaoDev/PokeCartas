package app;

import dao.VentaDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Inicio implements ServletContextListener {
  
  @Override
  public void contextInitialized(ServletContextEvent event) {
    
    ServletContext context = event.getServletContext();
    String path;
    
    //  Inicializar las tablas con datos de relleno
    path = context.getRealPath("/WEB-INF/data/usuarios.csv");
    new UsuarioDAO().init(path);
    
    path = context.getRealPath("/WEB-INF/data/productos.csv");
    new ProductoDAO().init(path);
    
    path = context.getRealPath("/WEB-INF/data/ventas.csv");
    new VentaDAO().init(path);
    
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent event) {
    
    //  Limpieza
    
  }
  
}
