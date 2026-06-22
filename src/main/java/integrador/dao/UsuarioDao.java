package integrador.dao;

import integrador.config.DatabaseConfig;

import integrador.entities.Usuario;
import integrador.enums.Rol;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UsuarioDao {

    public static boolean insertarUsuario(Usuario usuario){
        // query incompleta
        String query = "INSERT INTO usuarios (id_usuario, nombre, apellido,email,celular,contraseña,rol, eliminado, created_at) VALUES (?,?,?,?,?,?,?,?,?)";


        // 1] conexion y statment
        try (Connection con = DatabaseConfig.conectar(); PreparedStatement pstmt = con.prepareStatement(query)) {


            // 2] completamos la query con los datos del objeto usuario
            pstmt.setString(1, usuario.getId());

            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getCelular());
            pstmt.setString(6, usuario.getContrasenia());
            pstmt.setString(7, usuario.getRol().name());

            pstmt.setBoolean(8, usuario.getEliminado());
            pstmt.setString(9, usuario.getCreatedAt().toString()); //Convertimos el objeto fecha a string

            // 3] ejecutar query
            pstmt.executeUpdate();

            System.out.println("[DATABASE]: Subida de usuario "+usuario.getNombre()+" exitosa!");
            return true;

        } catch (SQLException err) {
            System.err.println("[!] Error al insertar " + err.getMessage());
            return false;
        }
    }

    public static ArrayList<Usuario> buscarPorNombre(String buscador) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();


        String query = "SELECT id_usuario, nombre, apellido,email,celular,contraseña,rol, eliminado, created_at FROM usuarios WHERE nombre LIKE ?";

        try (Connection con = DatabaseConfig.conectar();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            // 1] preparamos la query para buscar
            pstmt.setString(1,"%"+buscador+"%");


            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {

                    String id_usuario = rs.getString("id_usuario");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String email = rs.getString("email");
                    String celular = rs.getString("celular");
                    String contrasenia = rs.getString("contraseña");

                    // transformamos el string a enum Rol, usando upperCase por las dudas
                    Rol rol = Rol.valueOf(rs.getString("rol").toUpperCase());

                    boolean eliminado = rs.getBoolean("eliminado");

                    String fechaStr = rs.getString("created_at");
                    LocalDateTime createdAt = LocalDateTime.parse(fechaStr); // Parseamos de String a LocalDateTime


                    Usuario usuario = new Usuario(id_usuario,eliminado,createdAt,nombre,apellido,email,celular,contrasenia,rol  );

                    // 5. Agregamos el objeto a nuestra lista
                    listaUsuarios.add(usuario);
                }
            }

        } catch (SQLException err) {
            System.err.println("[Error al buscar Usuarios]: " + err.getMessage());
        }

        return listaUsuarios;
    }


}
