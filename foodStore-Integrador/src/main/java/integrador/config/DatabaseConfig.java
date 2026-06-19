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

    // ESTO ES PRUEBA, NO ES UNA ENTDAD NUESTRA
    public static void crearTablaUsuarios() {
        String sql = "CREATE TABLE IF NOT EXISTS categorias ("
                + "id_categoria VARCHAR PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "descripcion TEXT,"
                + "eliminado INTEGER NOT NULL,"
                + "created_at TEXT NOT NULL"
                + ");";

        try (Connection con = conectar();
             Statement stmt = con.createStatement() ) {

            stmt.execute(sql);
            System.out.println("Tabla verificada/creada.");

        } catch (SQLException e) {
            System.err.println("[ERROR]: Error al crear la tabla: " + e.getMessage());
        }
    }
}