import integrador.config.DatabaseConfig;
import integrador.dao.CategoriaDao;
import integrador.entities.Categoria;
import integrador.entities.Producto;

public class Main{
    public static void main(String[] args) {

        Categoria categoria_elect = new Categoria("Electrodomesticos","maquinitas de la cocina");
        Categoria categoria_comida = new Categoria("Comida","de esas cosas que se ingieren");

        DatabaseConfig.crearTablaUsuarios();

        CategoriaDao.post(categoria_elect);
        CategoriaDao.post(categoria_comida);
    }
}
