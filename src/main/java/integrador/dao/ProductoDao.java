package integrador.dao;

import integrador.config.DatabaseConfig;
import integrador.entities.Categoria;
import integrador.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDao {

    public static boolean insertarProcuto(Producto producto){
        String query = "INSERT INTO productos (id_producto,id_categoria,nombre,precio,descripcion,stock,eliminado,created_at) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DatabaseConfig.conectar(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1,producto.getId());
            pstmt.setString(2,producto.getCategoria().getId());
            pstmt.setString(3, producto.getNombre());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setString(5, producto.getDescripcion());
            pstmt.setInt(6,producto.getStock());
            pstmt.setBoolean(7,producto.isEliminado());
            pstmt.setString(8,producto.getCreatedAt().toString());

            pstmt.executeUpdate();

            System.out.println("[BD]: Subida de producto "+producto.getNombre()+" exitosa");
            return true;
        } catch (SQLException err) {
            System.err.println("[Error al insertar]: " + err.getMessage());
            return false;
        }
    }




}
