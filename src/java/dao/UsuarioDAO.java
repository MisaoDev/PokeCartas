package dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import model.Usuario;
import util.Enums.TipoUsuario;
import static util.Enums.TipoUsuario.*;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class UsuarioDAO {
  
  private static ArrayList<Usuario> data;
  private static boolean initialized = false;
  
  public ArrayList<Usuario> getList() {
    return data;
  }
  
  public void init(String filepath) {
    if (initialized) {
      return;
    }
    data = new ArrayList<>();
    
    try ( FileReader reader         = new FileReader(filepath);
          BufferedReader csvReader  = new BufferedReader(reader) ) {

      String row;
      boolean firstRow = true;
      while ((row = csvReader.readLine()) != null) {
        
        if (firstRow) {
          firstRow = false; continue;
        }
        
        String[] r = row.split(",");
        
        String rut                = r[0];
        String nombre             = r[1];
        String apellidoPaterno    = r[2];
        String apellidoMaterno    = r[3];
        LocalDate fechaNacimiento = LocalDate.parse(r[4], DateTimeFormatter.ISO_DATE);
        String email              = r[5];
        String nombreFoto         = r[6];
        TipoUsuario tipo          = TipoUsuario.valueOf(r[7]);
        String username           = r[8];
        String password           = r[9];
        
        Usuario usuario = new Usuario(rut, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento,
          email, nombreFoto, tipo, username, null);
        guardarContraseña(usuario, password.getBytes(StandardCharsets.UTF_8));
        
        data.add(usuario);
        initialized = true;

      }
    } catch (IOException e) {

    }
  }
  
  public Usuario buscar(String username) {
    for (Usuario u : data) {
      if (u.getUsername().equals(username)) {
        return u;
      }
    }
    return null;
  }
  
  public boolean eliminar(String username) {
    for (Usuario u : data) {
      if (u.getUsername().equals(username)) {
        data.remove(u);
        return true;
      }
    }
    return false;
  }
  
  public boolean insertar(Usuario usuario) {
    return data.add(usuario);
  }
  
  public boolean actualizar(String username, Usuario usuario) {
    if (usuario == null) return false;
    
    int i = 0;
    for (Usuario u : data) {
      if (u.getUsername().equals(username)) {
        data.set(i, usuario);
        return true;
      }
      i++;
    }
    return false;
  }
  
  /**
   * 
   * @param username  Nombre de usuario ingresado
   * @param input     Contraseña ingresada
   * @return          -1 si no se encuentra el usuario
   *                   0 si la contraseña es incorrecta
   *                   1 si la contraseña es correcta
   */
  public int checkPassword(String username, byte[] input) {
    Usuario user = buscar(username);

    if (user != null) {

      //  Verificar que la contraseña corresponde al hash del usuario
      byte[] hash = user.getPasswordHash();
      BCrypt.Result result = BCrypt.verifyer().verify(input, hash);
      
      //  Limpiar la contraseña original de la memoria
      Arrays.fill(input, (byte) 0);
      
      return result.verified ? 1 : 0;

    } else {
      //  Si el usuario no existe
      return -1;
    }
  }
  
  public void guardarContraseña(Usuario usuario, byte[] password) {
    
    //  Generar un hash para la contraseña y guardarlo
    byte[] hash = BCrypt.withDefaults().hash(10, password);
    usuario.setPasswordHash(hash);
    
    //  Limpiar la contraseña original de la memoria
    Arrays.fill(password, (byte) 0);
    
  }
  
}
