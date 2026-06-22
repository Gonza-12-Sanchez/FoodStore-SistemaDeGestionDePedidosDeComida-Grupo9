package integrador.dao;

import integrador.config.DatabaseConfig;

import integrador.entities.Categoria;

import integrador.entities.Producto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductoDao {

    public static boolean insertarProducto(Producto producto) {
        String query = "INSERT INTO productos (id_producto,id_categoria,nombre,precio,descripcion,stock,imagen,eliminado,created_at) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DatabaseConfig.conectar(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, producto.getId());
            pstmt.setString(2, producto.getCategoria().getId());
            pstmt.setString(3, producto.getNombre());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setString(5, producto.getDescripcion());

            pstmt.setInt(6,producto.getStock());
            pstmt.setString(7, producto.getImagen());
            pstmt.setBoolean(8,producto.getEliminado());
            pstmt.setString(9,producto.getCreatedAt().toString());


            pstmt.executeUpdate();

            System.out.println("[BD]: Subida de producto " + producto.getNombre() + " exitosa");
            return true;
        } catch (SQLException err) {
            System.err.println("[Error al insertar]: " + err.getMessage());
            return false;
        }
    }


    public static ArrayList<Producto> obtenerTodos() {
        ArrayList<Producto> listaProductos = new ArrayList<>();
        String query = "SELECT p.id_producto, p.nombre AS prod_nombre, p.precio, p.descripcion AS prod_desc, "
                + "p.stock, p.imagen, p.eliminado AS prod_elim, p.created_at AS prod_date, "
                + "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc, "
                + "c.eliminado AS cat_elim, c.created_at AS cat_date "
                + "FROM productos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria";


        try (Connection con = DatabaseConfig.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getString("id_categoria"),
                        rs.getBoolean("cat_elim"),
                        LocalDateTime.parse(rs.getString("cat_date")),
                        rs.getString("cat_nombre"),
                        rs.getString("cat_desc")
                );

                Producto producto = new Producto(
                        rs.getString("id_producto"),
                        rs.getBoolean("prod_elim"),
                        LocalDateTime.parse(rs.getString("prod_date")),
                        rs.getString("prod_nombre"),
                        rs.getDouble("precio"),
                        rs.getString("prod_desc"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        categoria
                );

                listaProductos.add(producto);
            }

        } catch (SQLException err) {
            System.err.println("[Error al obtener los productos]: " + err.getMessage());
        }

        return listaProductos;
    }


    public static ArrayList<Producto> buscarPorNombre(String busqueda) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        String query = "SELECT p.id_producto, p.nombre AS prod_nombre, p.precio,p.descripcion,p.stock,p.imagen, p.eliminado, p.created_at, "
                + "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc , c.eliminado AS cat_eliminado, c.created_at AS cat_created_at "
                + "FROM productos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE p.nombre LIKE ? AND p.eliminado = 0";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, "%" + busqueda + "%");

            try( ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {

                    // armamos el objeto categoria
                    Categoria categoria = new Categoria(
                            rs.getString("id_categoria"),
                            rs.getBoolean("cat_eliminado"),
                            LocalDateTime.parse(rs.getString("cat_created_at")),
                            rs.getString("cat_nombre"),
                            rs.getString("cat_desc")
                    );

                    // armamos el objeto Producto
                    Producto producto = new Producto(rs.getString("id_producto"),
                            rs.getBoolean("eliminado"),
                            LocalDateTime.parse(rs.getString("created_at")),
                            rs.getString("prod_nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            categoria
                    );

                    // agregamos a la lista
                    listaProductos.add(producto);
                }
            }

        } catch (SQLException err) {
            System.err.println("[Error al obtener todas las categorías]: " + err.getMessage());
        }
        // devolvemos lista
        return listaProductos;
    }

    public static boolean actualizarPrecioYStock(String idProducto, double nuevoPrecio, int nuevoStock) {
        String query = "UPDATE productos SET precio = ?, stock = ? WHERE id_producto = ?";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setInt(2, nuevoStock);

            pstmt.setString(3, idProducto);

            int filasAfectadas = pstmt.executeUpdate();


            return filasAfectadas > 0;
        } catch (SQLException err) {
            System.err.println("[Error al actualizar el producto " + idProducto + "]: " + err.getMessage());
            return false;
        }
    }

    public static boolean eliminar(String idProducto) {
        String query = "UPDATE productos SET eliminado = 1 WHERE id_producto = ?";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, idProducto);

            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException err) {
            System.err.println("[Error al dar de baja el producto " + idProducto + "]: " + err.getMessage());
            return false;
        }
    }



}
