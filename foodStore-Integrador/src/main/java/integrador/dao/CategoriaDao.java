package integrador.dao;

import integrador.entities.Categoria;

import java.sql.*;

import java.util.ArrayList;

import integrador.config.DatabaseConfig;

public class CategoriaDao {

    public static boolean insertarCategoria(Categoria categoria){
        // query incompleta
        String query = "INSERT INTO categorias (id_categoria, nombre, descripcion, eliminado, created_at) VALUES (?, ?, ?, ?, ?)";


        // 1] conexion y statment
        try (Connection con = DatabaseConfig.conectar();PreparedStatement pstmt = con.prepareStatement(query)) {


            // 2] completamos la query con los datos del obejto categoria
            pstmt.setString(1, categoria.getId());
            pstmt.setString(2, categoria.getNombre());
            pstmt.setString(3, categoria.getDescripcion());
            pstmt.setBoolean(4, categoria.isEliminado());
            pstmt.setString(5, categoria.getCreatedAt());

            // 3] ejecutar query
            pstmt.executeUpdate();

            System.out.println("[BD]: Subida de categoria "+categoria.getNombre()+" exitosa!");
            return true;

        } catch (SQLException err) {
            System.err.println("[Error al insertar]: " + err.getMessage());
            return false;
        }
    }

    public static ArrayList<Categoria> obtenerTodas() {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();
        String query = "SELECT id_categoria,created_at, eliminado,nombre,descripcion FROM categorias";

        try (Connection con = DatabaseConfig.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                // Extraemos los datos de la fila actual
                String id = rs.getString("id_categoria");
                String createdAt = rs.getString("created_at");
                boolean eliminado = rs.getBoolean("eliminado");

                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");



                // 4. Creamos el objeto Categoria usando el constructor completo
                Categoria categoria = new Categoria(id,eliminado,createdAt, nombre, descripcion );

                // 5. Agregamos el objeto a nuestra lista
                listaCategorias.add(categoria);
            }

        } catch (SQLException err) {
            System.err.println("[Error al obtener todas las categorías]: " + err.getMessage());
        }

        return listaCategorias;
    }



}
