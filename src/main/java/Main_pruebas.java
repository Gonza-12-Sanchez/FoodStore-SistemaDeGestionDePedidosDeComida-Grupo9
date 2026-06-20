import integrador.config.DatabaseConfig;
import integrador.dao.CategoriaDao;
import integrador.dao.ProductoDao;
import integrador.entities.Categoria;
import integrador.entities.Producto;

public class Main_pruebas {
    public static void main(String[] args) {

        DatabaseConfig.crearTablaCategorias();
        DatabaseConfig.crearTablaProductos();

        Categoria cat = new Categoria("Comida","Comida que se ingiere");
        Producto prod = new Producto("pepsi",15.0,"Pepsi cola",15,"imagen",cat);

        CategoriaDao.insertarCategoria(cat);
        ProductoDao.insertarProcuto(prod);

    }
}
