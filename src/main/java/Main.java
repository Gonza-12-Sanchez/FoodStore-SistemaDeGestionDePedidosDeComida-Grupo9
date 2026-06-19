import integrador.config.DatabaseConfig;
import integrador.dao.CategoriaDao;
import integrador.dao.UsuarioDao;
import integrador.entities.Categoria;
import integrador.entities.Usuario;
import integrador.enums.Rol;


public class Main{
    public static void main(String[] args) {


        for(Usuario usuario : UsuarioDao.buscarPorNombre("it")){
            System.out.println(usuario.getId()+" "+usuario.getNombre());
        }


    }
}
