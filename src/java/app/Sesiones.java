package app;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.Usuario;
import util.Enums.TipoUsuario;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Sesiones implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    
    //  Crear un usuario INVITADO para la nueva sesión
    HttpSession session = se.getSession();
    Usuario user = new Usuario();
    user.setTipo(TipoUsuario.INVITADO);
    session.setAttribute("user", user);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    //  Hacer nada
  }
  
}
