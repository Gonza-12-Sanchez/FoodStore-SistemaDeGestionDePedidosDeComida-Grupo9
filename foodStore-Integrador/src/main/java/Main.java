import integrador.config.DatabaseConfig;
import integrador.entities.Categoria;
import integrador.entities.DetallePedido;
import integrador.entities.Producto;
public class Main{
    public static void main(String[] args) {

        Categoria cat_comida = new Categoria("Comida","Comida que se come");


        Producto prod1 = new Producto("Lata Pepsi 350ml",200.00,"Lata de aluminio Pepsi de 350 ml",32,"url/img",cat_comida);

        System.out.println(prod1.getNombre());
        System.out.println(prod1.getCreatedAt());
        System.out.println(prod1.getCategoria().getNombre());

//        DetallePedido det1 = new DetallePedido(0,prod1); // probar con 0 para que tire error propio
//
//        System.out.println(det1.getSubtotal());

        DatabaseConfig.crearTablaUsuarios();
    }
}
