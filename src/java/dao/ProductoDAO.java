package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Producto;
import util.Enums.TipoProducto;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class ProductoDAO {
  
  private static ArrayList<Producto> data;
  private static boolean initialized = false;
  
  public ArrayList<Producto> getList() {
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
        
        int id              = r[0] == "" ? 0 : Integer.parseInt(r[0]);
        String nombre       = r[1];
        String descripción  = r[2];
        TipoProducto tipo   = TipoProducto.valueOf(r[3]);
        int precio          = Integer.parseInt(r[4]);
        int descuento       = Integer.parseInt(r[5]);
        int stock           = Integer.parseInt(r[6]);
        
        Producto producto = new Producto(id, nombre, descripción, tipo, precio, descuento, stock);
        data.add(producto);
        initialized = true;

      }
    } catch (IOException e) {

    }
  }
  
  public Producto buscar(int id) {
    for (Producto p : data) {
      if (p.getId() == id) {
        return p;
      }
    }
    return null;
  }
  
  public boolean eliminar(int id) {
    for (Producto p : data) {
      if (p.getId() == id) {
        data.remove(p);
        return true;
      }
    }
    return false;
  }
  
  public boolean insertar(Producto producto) {
    return data.add(producto);
  }
  
  public boolean actualizar(int id, Producto producto) {
    if (producto == null) return false;
    
    int i = 0;
    for (Producto p : data) {
      if (p.getId() == id) {
        data.set(i, producto);
        return true;
      }
      i++;
    }
    return false;
  }
  
}
