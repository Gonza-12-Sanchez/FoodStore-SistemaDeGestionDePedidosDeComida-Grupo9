package integrador.dao;

import integrador.config.DatabaseConfig;
import integrador.entities.*;
import integrador.enums.Estado;
import integrador.enums.FormatoPago;
import integrador.enums.Rol;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PedidoDao {

    public static boolean insertarPedido(Pedido pedido) {
        // query incompleta
        String query = "INSERT INTO pedidos (id_pedido, fecha,estado,total,formato_pago,id_usuario, eliminado, created_at) VALUES (?,?,?,?,?,?,?,?)";


        // 1] conexion y statment
        try (Connection con = DatabaseConfig.conectar(); PreparedStatement pstmt = con.prepareStatement(query)) {


            // 2] completamos la query con los datos del objeto usuario
            pstmt.setString(1, pedido.getId());
            pstmt.setString(2, pedido.getFecha().toString());
            pstmt.setString(3, pedido.getEstado().name());
            pstmt.setDouble(4, pedido.getTotal());
            pstmt.setString(5, pedido.getFormatoPago().name());
            pstmt.setString(6, pedido.getUsuario().getId());
            pstmt.setBoolean(7, pedido.isEliminado());
            pstmt.setString(8, pedido.getCreatedAt().toString());


            // 3] ejecutar query
            pstmt.executeUpdate();

            System.out.println("[DATABASE]: Subida de pedido " + pedido.getId() + " exitosa!");
            return true;

        } catch (SQLException err) {
            System.err.println("[!] Error al insertar " + err.getMessage());
            return false;

        }
    }

    public static ArrayList<Pedido> obtenerTodos() {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();

        // 1. Query principal con JOIN para traer los datos del Pedido y su Usuario
        String query = "SELECT p.id_pedido, p.fecha, p.estado, p.total, p.formato_pago, p.eliminado, p.created_at, "
                + "u.id_usuario, u.nombre AS u_nombre, u.apellido AS u_apellido, u.email AS u_email,u.celular AS u_celular,u.contrasenia AS u_contrasenia, u.rol AS u_rol,u.eliminado AS u_eliminado, u.created_at AS u_created_at "
                + "FROM pedidos p "
                + "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario WHERE p.eliminado = 0";

        try (Connection con = DatabaseConfig.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                // objeto Usuario
                Usuario usuario = new Usuario(
                        rs.getString("id_usuario"),
                        rs.getBoolean("u_eliminado"),
                        LocalDateTime.parse(rs.getString("u_created_at")),
                        rs.getString("u_nombre"),
                        rs.getString("u_apellido"),
                        rs.getString("u_email"),
                        rs.getString("u_celular"),
                        rs.getString("u_contrasenia"),
                        Rol.valueOf(rs.getString("u_rol").toUpperCase())
                );


                // extraemos los datos básicos del Pedido
                String idPedido = rs.getString("id_pedido");
                LocalDate fecha = LocalDate.parse(rs.getString("fecha"));
                Estado estado = Estado.valueOf(rs.getString("estado").toUpperCase());
                double total = rs.getDouble("total");
                FormatoPago formatoPago = FormatoPago.valueOf(rs.getString("formato_pago").toUpperCase());
                boolean eliminado = rs.getBoolean("eliminado");
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));



                ArrayList<DetallePedido> detalles = obtenerDetallesDePedido(idPedido, con);
                Pedido pedido = new Pedido(idPedido, eliminado, createdAt,fecha, estado, total,detalles, formatoPago , usuario );

                listaPedidos.add(pedido);
            }

        } catch (SQLException err) {
            System.err.println("[Error al obtener todos los pedidos]: " + err.getMessage());
        }

        return listaPedidos;
    }


    private static ArrayList<DetallePedido> obtenerDetallesDePedido(String idPedido, Connection con) throws SQLException {
        ArrayList<DetallePedido> listaDetalles = new ArrayList<>();

        // Seleccionamos absolutamente todos los campos necesarios usando alias para evitar colisiones de nombres
        String queryDetalles = "SELECT "
                + "d.id_detalle, d.cantidad, d.subtotal, d.eliminado AS det_eliminado, d.created_at AS det_created, "
                + "p.id_producto, p.nombre AS prod_nombre, p.precio AS prod_precio, p.descripcion AS prod_desc, p.stock AS prod_stock, p.imagen AS prod_img, p.eliminado AS prod_eliminado, p.created_at AS prod_created, "
                + "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.eliminado AS cat_eliminado, c.created_at AS cat_created "
                + "FROM detalles d "
                + "INNER JOIN productos p ON d.id_producto = p.id_producto "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE d.id_pedido = ?";

        try (PreparedStatement pstmt = con.prepareStatement(queryDetalles)) {
            pstmt.setString(1, idPedido);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    // 1. Reconstruimos el objeto Categoria usando su constructor completo
                    String catId = rs.getString("id_categoria");
                    boolean catEliminado = rs.getBoolean("cat_eliminado");
                    LocalDateTime catCreated = LocalDateTime.parse(rs.getString("cat_created"));
                    String catNombre = rs.getString("cat_nombre");
                    String catDesc = rs.getString("cat_desc");

                    Categoria categoria = new Categoria(catId, catEliminado, catCreated, catNombre, catDesc);

                    // 2. Reconstruimos el objeto Producto usando su constructor completo e inyectando la categoría
                    String prodId = rs.getString("id_producto");
                    boolean prodEliminado = rs.getBoolean("prod_eliminado");
                    LocalDateTime prodCreated = LocalDateTime.parse(rs.getString("prod_created"));
                    String prodNombre = rs.getString("prod_nombre");
                    double prodPrecio = rs.getDouble("prod_precio");
                    String prodDesc = rs.getString("prod_desc");
                    int prodStock = rs.getInt("prod_stock");
                    String prodImg = rs.getString("prod_img");

                    Producto producto = new Producto(prodId, prodEliminado, prodCreated, prodNombre, prodPrecio, prodDesc, prodStock, prodImg, categoria);


                    DetallePedido detalle = new DetallePedido(
                            rs.getString("id_detalle"),
                            rs.getBoolean("det_eliminado"),
                            LocalDateTime.parse(rs.getString("det_created")),
                            rs.getInt("cantidad"),
                            rs.getDouble("subtotal"),
                            producto
                    );

                    listaDetalles.add(detalle);
                }
            }
        }
        return listaDetalles;
    }

}
