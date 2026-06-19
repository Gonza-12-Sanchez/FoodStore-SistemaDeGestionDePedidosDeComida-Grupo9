package integrador.dao;

import integrador.entities.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import integrador.config.DatabaseConfig;

public class CategoriaDao {

    public static boolean post(Categoria categoria){
        // query incompleta
        String query = "INSERT INTO categorias (id_categoria, nombre, descripcion, eliminado, created_at) VALUES (?, ?, ?, ?, ?)";


        // 1] conexion y statment
        try (Connection con = DatabaseConfig.conectar();PreparedStatement pstmt = con.prepareStatement(query)) {


            // 2] completamos la query con los datos del obejto categoria
            pstmt.setString(1, categoria.getId());
            pstmt.setString(2, categoria.getNombre());
            pstmt.setString(3, categoria.getDescripcion());
            pstmt.setBoolean(4, categoria.isEliminado());
            pstmt.setString(5, categoria.getCreatedAt().toString());

            // 3] ejecutar query
            pstmt.executeUpdate();

            System.out.println("[BD]: Subida de categoria "+categoria.getNombre()+" exitosa!");
            return true;

        } catch (SQLException err) {
            System.err.println("[Error al insertar]: " + err.getMessage());
            return false;
        }
    }

    public static boolean getTodas() {
        // metodo para traer todas las categorias
    }



}
