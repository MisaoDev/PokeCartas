package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Venta;
import model.Producto;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class VentaDAO {
  
  private static ArrayList<Venta> data;
  private static boolean initialized = false;
  
  public ArrayList<Venta> getList() {
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
        
        int id              = Integer.parseInt(r[0]);
        String username     = r[1];
        LocalDate fecha     = LocalDate.parse(r[2], DateTimeFormatter.ISO_DATE);
        LocalTime hora      = LocalTime.parse(r[3], DateTimeFormatter.ISO_TIME);
        int idProducto      = Integer.parseInt(r[4]);
        Producto producto   = new ProductoDAO().buscar(idProducto);
        int precioUnitario  = producto.getValorFinal();
        int cantidad        = Integer.parseInt(r[5]);
        
        Venta venta = new Venta(id, username, fecha, hora, producto, precioUnitario, cantidad);
        data.add(venta);
        initialized = true;

      }
    } catch (IOException e) {

    }
  }
  
  public Venta buscar(int id) {
    for (Venta o : data) {
      if (o.getId() == id) {
        return o;
      }
    }
    return null;
  }
  
  public boolean eliminar(int id) {
    for (Venta o : data) {
      if (o.getId() == id) {
        data.remove(o);
        return true;
      }
    }
    return false;
  }
  
  public boolean insertar(Venta orden) {
    return data.add(orden);
  }
  
  public boolean actualizar(int id, Venta orden) {
    if (orden == null) return false;
    
    int i = 0;
    for (Venta o : data) {
      if (o.getId() == id) {
        data.set(i, orden);
        return true;
      }
      i++;
    }
    return false;
  }
  
}
