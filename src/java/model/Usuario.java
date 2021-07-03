package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import util.Enums.TipoUsuario;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Usuario implements Serializable {
  
  private String rut;
  private String nombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private LocalDate fechaNacimiento;
  private String email;
  
  private String nombreFoto;
  private TipoUsuario tipo;
  
  private String username;
  private byte[] passwordHash;
  
  private ArrayList<Venta> ventas;
  
  public Usuario() {
  }

  public Usuario(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String nombreFoto, TipoUsuario tipo, String username, byte[] passwordHash) {
    this.rut = rut;
    this.nombre = nombre;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.fechaNacimiento = fechaNacimiento;
    this.email = email;
    this.nombreFoto = nombreFoto;
    this.tipo = tipo;
    this.username = username;
    this.passwordHash = passwordHash;
    this.ventas = new ArrayList<>();
  }

  public String getRut() {
    return rut;
  }

  public void setRut(String rut) {
    this.rut = rut;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getNombreFoto() {
    return nombreFoto;
  }

  public void setNombreFoto(String nombreFoto) {
    this.nombreFoto = nombreFoto;
  }

  public TipoUsuario getTipo() {
    return tipo;
  }

  public void setTipo(TipoUsuario tipo) {
    this.tipo = tipo;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public byte[] getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(byte[] passwordHash) {
    this.passwordHash = passwordHash;
  }

  public ArrayList<Venta> getVentas() {
    return ventas;
  }

  public void setVentas(ArrayList<Venta> ventas) {
    this.ventas = ventas;
  }
  
}
