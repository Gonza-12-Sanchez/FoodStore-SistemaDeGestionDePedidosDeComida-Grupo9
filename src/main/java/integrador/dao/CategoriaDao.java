package integrador.dao;

import integrador.entities.Categoria;

import java.sql.*;

import java.time.LocalDateTime;
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
            pstmt.setString(5, categoria.getCreatedAt().toString()); //Convertimos el objeto fecha a string

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
        String query = "SELECT id_categoria,created_at, eliminado,nombre,descripcion FROM categorias WHERE eliminado = 0";

        try (Connection con = DatabaseConfig.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id_categoria");

                String fechaStr = rs.getString("created_at");
                LocalDateTime createdAt = LocalDateTime.parse(fechaStr);

                boolean eliminado = rs.getBoolean("eliminado");

                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");


                Categoria categoria = new Categoria(id,eliminado,createdAt, nombre, descripcion );

                listaCategorias.add(categoria);
            }

        } catch (SQLException err) {
            System.err.println("[Error al obtener todas las categorías]: " + err.getMessage());
        }
        return listaCategorias;
    }

    public static boolean editarCategoria(String idCategoria, String nuevoNombre, String nuevaDescripcion) {
        String query = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id_categoria = ?";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevaDescripcion);
            pstmt.setString(3, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[BD]: Categoría con ID " + idCategoria + " actualizada con éxito.");
                return true;
            } else {
                System.out.println("[BD]: No se encontró ninguna esa categoría");
                return false;
            }

        } catch (SQLException err) {
            System.err.println("[Error al editar categoría]: " + err.getMessage());
            return false;
        }
    }


    public static boolean eliminarCategoria(String idCategoria) {
        String query = "UPDATE categorias SET eliminado = 1 WHERE id_categoria = ?";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[BD]: Categoría eliminada.");
                return true;
            } else {
                System.out.println("[BD]: No se encontró la categoría.");
                return false;
            }

        } catch (SQLException err) {
            System.err.println("[Error al eliminar categoría]: " + err.getMessage());
            return false;
        }
    }

}
