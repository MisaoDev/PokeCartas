package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Venta implements Serializable {
  
  private int id;
  private String username;
  private LocalDate fecha;
  private LocalTime hora;
  private Producto producto;
  private int precioUnitario;
  private int cantidad;

  public Venta() {
  }

  public Venta(int id, String username, LocalDate fecha, LocalTime hora, Producto producto, int precioUnitario, int cantidad) {
    this.id = id;
    this.username = username;
    this.fecha = fecha;
    this.hora = hora;
    this.producto = producto;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public LocalTime getHora() {
    return hora;
  }

  public void setHora(LocalTime hora) {
    this.hora = hora;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public int getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(int precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }
  
}
