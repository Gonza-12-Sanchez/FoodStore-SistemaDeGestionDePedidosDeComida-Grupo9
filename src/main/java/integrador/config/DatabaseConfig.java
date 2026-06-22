package integrador.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:productos_database.db");
            System.out.println("- conexion exitosa -");
        } catch (SQLException e) {
            System.err.println("[ERROR]: error al conectar a la base de datos: " + e.getMessage());
        }
        return con;
    }



    public static void crearTablaCategorias() { // --------------------------------------- Categorias
        String sql = "CREATE TABLE IF NOT EXISTS categorias ("
                + "id_categoria VARCHAR PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "descripcion TEXT,"
                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL"
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement()) {

            stmt.execute(sql);
            System.out.println("[DATABASE]: Tabla Categorias creada.");

        } catch (SQLException e) {
            System.err.println("[ERROR]: Error al crear la tabla: " + e.getMessage());
        }
    }


    public static void crearTablaUsuarios() { // --------------------------------------- Usuarios
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id_usuario VARCHAR PRIMARY KEY,"
                + "nombre VARCHAR NOT NULL,"
                + "apellido VARCHAR NOT NULL,"
                + "email VARCHAR NOT NULL,"
                + "celular VARCHAR,"
                + "contrasenia VARCHAR NOT NULL,"
                + "rol VARCHAR NOT NULL,"
                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL"
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement() ) {

            stmt.execute(sql);
            System.out.println("[DATABASE]: Tabla Usuarios creada.");

        } catch (SQLException e) {
            System.err.println("[ERROR]: Error al crear la tabla: " + e.getMessage());
        }
    }




    public static void crearTablaProductos() {// --------------------------------------- Productos
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "id_producto VARCHAR PRIMARY KEY,"
                + "id_categoria VARCHAR NOT NULL,"
                + "nombre VARCHAR NOT NULL,"
                + "precio REAL NOT NULL,"
                + "descripcion VARCHAR ,"
                + "stock INT NOT NULL ,"
                + "imagen VARCHAR ,"
                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL,"
                + "FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)"
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement() ) {
            stmt.execute(sql);
            System.out.println("[DATABASE]: Tabla Productos creada.");
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    public static void crearTablaDetalles() {// --------------------------------------- Detalle
        String sql = "CREATE TABLE IF NOT EXISTS detalles ("
                + "id_detalle VARCHAR PRIMARY KEY,"
                + "id_pedido VARCHAR NOT NULL,"
                + "id_producto VARCHAR NOT NULL,"
                + "cantidad INT NOT NULL,"
                + "subtotal REAL NOT NULL,"
                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL,"
                + "FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido), "
                + "FOREIGN KEY (id_producto) REFERENCES productos(id_producto)"
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement() ) {
            stmt.execute(sql);
            System.out.println("[DATABASE]: Tabla Detalles creada.");
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    public static void crearTablaPedido() {// --------------------------------------- Detalle
        String sql = "CREATE TABLE IF NOT EXISTS pedidos ("
                + "id_pedido VARCHAR PRIMARY KEY,"
                + "fecha TEXT NOT NULL,"
                + "estado VARCHAR NOT NULL,"
                + "total REAL NOT NULL,"
                + "formato_pago VARCHAR NOT NULL,"

                + "id_usuario VARCHAR NOT NULL,"

                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL,"

                + "FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) "
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement() ) {
            stmt.execute(sql);
            System.out.println("[DATABASE]: Tabla Pedidos creada.");
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }





}