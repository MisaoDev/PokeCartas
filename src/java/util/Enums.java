package util;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Enums {

  private static final boolean CAN_VIEW_PRODUCTS  = true;
  private static final boolean CAN_BUY            = true;
  private static final boolean CAN_EDIT_PRODUCTS  = true;
  private static final boolean CAN_EDIT_ORDERS    = true;
  private static final boolean CAN_EDIT_USERS     = true;
  private static final boolean CAN_VIEW_REPORTS   = true;

  // Tipos de usuario. Acá se definen los permisos individuales que tiene cada uno.
  public enum TipoUsuario {
    INVITADO      ("Invitado", CAN_VIEW_PRODUCTS,  false,  false,  false,  false,  false),
    CLIENTE       ("Cliente", CAN_VIEW_PRODUCTS,  CAN_BUY,  false,  false,  false,  false),
    OPERARIO      ("Operario", CAN_VIEW_PRODUCTS,  false,  CAN_EDIT_PRODUCTS,  CAN_EDIT_ORDERS,  false,  false),
    ADMINISTRADOR ("Administrador", CAN_VIEW_PRODUCTS,  false,  CAN_EDIT_PRODUCTS,  CAN_EDIT_ORDERS,  CAN_EDIT_USERS,
            CAN_VIEW_REPORTS);

    private final String nombre;
    private final boolean canViewProducts;
    private final boolean canBuy;
    private final boolean canEditProducts;
    private final boolean canEditOrders;
    private final boolean canEditUsers;
    private final boolean canViewReports;

    private TipoUsuario(String nombre, boolean canViewProducts, boolean canBuy, boolean canEditProducts, boolean canEditOrders, boolean canEditUsers, boolean canViewReports) {
      this.nombre = nombre;
      this.canViewProducts = canViewProducts;
      this.canBuy = canBuy;
      this.canEditProducts = canEditProducts;
      this.canEditOrders = canEditOrders;
      this.canEditUsers = canEditUsers;
      this.canViewReports = canViewReports;
    }

    public String getNombre() {
      return nombre;
    }
    
    public boolean isCanViewProducts() {
      return canViewProducts;
    }

    public boolean isCanBuy() {
      return canBuy;
    }

    public boolean isCanEditProducts() {
      return canEditProducts;
    }

    public boolean isCanEditOrders() {
      return canEditOrders;
    }

    public boolean isCanEditUsers() {
      return canEditUsers;
    }

    public boolean isCanViewReports() {
      return canViewReports;
    }

  }
  
  // Tipos de producto. Con el título de la categoría y una pequeña descripción.
  public enum TipoProducto {
    CARTA   ( "Cartas sueltas",
              "Compra las cartas individuales que necesites para armar tus deck o coleccionar."),
    SELLADO ( "Productos sellados",
              "Productos oficiales del juego de cartas de Pokémon."),
    INSUMO  ( "Insumos TCG",
              "Accesorios para cartas, dados, monedas, alfombras y todo lo que necesitas para jugar.");
  
    private final String nombre;
    private final String descripción;

    private TipoProducto(String nombre, String descripción) {
      this.nombre = nombre;
      this.descripción = descripción;
    }

    public String getNombre() {
      return nombre;
    }

    public String getDescripción() {
      return descripción;
    }
  }

}
