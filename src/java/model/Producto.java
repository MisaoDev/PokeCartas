package model;

import java.io.Serializable;
import static util.Enums.*;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Producto implements Serializable {
  
  int id;
  String nombre;
  String descripción;
  TipoProducto tipo;
  int precio;
  int descuento;
  int stock;

  public Producto() {
  }

  public Producto(int id, String nombre, String descripción, TipoProducto tipo, int precio, int descuento, int stock) {
    this.id = id;
    this.nombre = nombre;
    this.descripción = descripción;
    this.tipo = tipo;
    this.precio = precio;
    this.descuento = descuento;
    this.stock = stock;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripción() {
    return descripción;
  }

  public void setDescripción(String descripción) {
    this.descripción = descripción;
  }

  public TipoProducto getTipo() {
    return tipo;
  }

  public void setTipo(TipoProducto tipo) {
    this.tipo = tipo;
  }

  public int getPrecio() {
    return precio;
  }

  public void setPrecio(int precio) {
    this.precio = precio;
  }

  public int getDescuento() {
    return descuento;
  }

  public void setDescuento(int descuento) {
    this.descuento = descuento;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
  
  public int getValorDescuento() {
    return Math.floorDiv(precio * descuento, 100);
  }
  
  public int getValorFinal() {
    return precio - getValorDescuento();
  }
  
}
