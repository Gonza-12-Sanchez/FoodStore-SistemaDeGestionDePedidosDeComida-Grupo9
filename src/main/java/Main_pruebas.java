import integrador.config.DatabaseConfig;
import integrador.dao.CategoriaDao;
import integrador.dao.PedidoDao;
import integrador.dao.ProductoDao;
import integrador.dao.UsuarioDao;
import integrador.entities.*;
import integrador.enums.Estado;
import integrador.enums.FormatoPago;
import integrador.enums.Rol;

public class Main_pruebas {
    public static void main(String[] args) {
        DatabaseConfig.crearTablaCategorias();
        DatabaseConfig.crearTablaProductos();
        DatabaseConfig.crearTablaUsuarios();

        Categoria cat = new Categoria("Comida","Comida que se ingiere");
        CategoriaDao.insertarCategoria(cat);

        Producto prod = new Producto("Cocacola",15.0,"coca loca de 1 litro",5,"imagen",cat);
        Producto prod2 = new Producto("Pepsi 350ml",20.0,"Pepsi cola 350 miligramos",20,"imagen",cat);
        Producto prod3 = new Producto("Pepsi 1.5l",10.0,"Pepsi cola 1.5 litros",10,"imagen",cat);
        ProductoDao.insertarProcuto(prod);
        ProductoDao.insertarProcuto(prod2);
        ProductoDao.insertarProcuto(prod3);

        for (Producto p: ProductoDao.buscarPorNombre("eps")){
            System.out.println(p.getNombre()+" --> "+p.getDescripcion());
        }


        DatabaseConfig.crearTablaPedido();
        DatabaseConfig.crearTablaDetalles();

        Usuario usu = new Usuario("walas","wonky","walasgaminb@gmail.com","12345678","wonka123", Rol.USUARIO);
        UsuarioDao.insertarUsuario(usu);

        Pedido pedido = new Pedido(FormatoPago.EFECTIVO);
        pedido.addDetallePedido(2,prod);
        pedido.addDetallePedido(3,prod2);
        pedido.setUsuario(usu);

        // insertar pedido
        PedidoDao.insertarPedido(pedido);

        for (Pedido p: PedidoDao.obtenerTodos()){
            System.out.println(p.toString());
        }


        for (Producto p: ProductoDao.obtenerTodos()){
            System.out.println(p.getNombre()+" --> "+p.getDescripcion());
        }


    }
}
