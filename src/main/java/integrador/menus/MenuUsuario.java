package integrador.menus;

import integrador.entities.Usuario;
import integrador.enums.Rol;
import integrador.exception.ValidacionesDeEntrada;

import java.util.ArrayList;

public class MenuUsuario {
    //Dicha clase contiene toda la logica del submenu de producto

    //Atributos
    //private static ArrayList<Usuario> usuariosDB = UsuarioDao.obtenerTodos();
    private static ArrayList<Usuario> usuariosDB = new ArrayList<>();

    //----ARMAMOS UN METODO POR CADA OPCION DEL MENU----
    //Metodo listar
    public static void listarUsuarios() {
        System.out.println("- Lista de usuarios -");
        //Obtenemos una lista de usuarios no eliminados
        ArrayList<Usuario> noEliminados = obtenerUsuariosNoEliminados();

        //Si la lista esta vacia mostramos un mensaje, de no ser asi los mostramos por pantalla
        if (noEliminados.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            for (int i = 0; i < noEliminados.size(); i++) {
                System.out.println((i + 1) + ". " + noEliminados.get(i));
            }
        }
        System.out.println("-----------------");
    }

    //Metodo crear
    public static void crearUsuario() {
        System.out.println("- Creacion nuevo Usuario -");
        //Solicitamos los datos necesarios para crear un usuario
        String nombre = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre: ");
        String apellido = ValidacionesDeEntrada.ingresarOpcionNoVacia("Apellido: ");

        //Como el email debe ser unico, utilizamos un metodo para asegurarnos de eso
        String email = ValidacionesDeEntrada.ingresarOpcionNoVacia("Email: ");
        while (existeEmail(email)) {
            System.out.println("[ERROR] Ya existe un usuario con ese email.");
            email = ValidacionesDeEntrada.ingresarOpcionNoVacia("Email: ");
        }

        String celular = ValidacionesDeEntrada.ingresarOpcionNoVacia("Celular: ");
        String contrasenia = ValidacionesDeEntrada.ingresarOpcionNoVacia("Contraseña: ");

        //Como tenemos distintos roles, solicitamos que se seleccione uno con la ayuda de un metodo
        Rol rol = seleccionarRolPorNumero();

        //Realizamos la creacion del usuario y lo insertamos a la BD
        Usuario usuarioNuevo = new Usuario(nombre, apellido, email, celular, contrasenia, rol);
        //UsuarioDao.insertarUsuario(usuarioNuevo);
        System.out.println("Usuario creado con ID: " + usuarioNuevo.getId());

        //Actualizamos la lista usuariosDB
        //usuariosDB = UsuarioDao.obtenerTodos();
    }

    //Metodo editar
    public static void editarUsuario() {
        //Solicitamos que se seleccione un usuario
        Usuario usuario = seleccionarUsuarioPorNumero();

        //Si no hay usuarios cargados devolvemos null
        if (usuario == null) return;

        System.out.println("- Edicion usuario existente -");
        System.out.println("(Si no queres modificar un valor, directamente toca enter :D)\n " +
                "(Los valores actuales se van a mostrar entre parentesis)");

        //Solicitamos que se ingresen los nuevos valores
        //Si los valores ingresados no estan vacios, los seteamos
        String nuevoNombre = ValidacionesDeEntrada.ingresarOpcional("Nombre (" + usuario.getNombre() + "): ");
        if (!nuevoNombre.isEmpty()) usuario.setNombre(nuevoNombre);

        String nuevoApellido = ValidacionesDeEntrada.ingresarOpcional("Apellido (" + usuario.getApellido() + "): ");
        if (!nuevoApellido.isEmpty()) usuario.setApellido(nuevoApellido);

        String nuevoEmail = ValidacionesDeEntrada.ingresarOpcional("Email (" + usuario.getEmail() + "): ");
        //Si decide cambiar el email, verificamos si ya existe
        //De ser asi, se mantiene el email anterior
        if (!nuevoEmail.isEmpty()) {
            if (existeEmail(nuevoEmail)) {
                System.out.println("[ERROR] Email ingresado ya en uso, se mantiene el anterior.");
            } else {
                usuario.setEmail(nuevoEmail);
            }
        }

        String nuevoCelular = ValidacionesDeEntrada.ingresarOpcional("Celular (" + usuario.getCelular() + "): ");
        if (!nuevoCelular.isEmpty()) usuario.setCelular(nuevoCelular);

        String nuevaContrasenia = ValidacionesDeEntrada.ingresarOpcional("Contraseña: ");
        if (!nuevaContrasenia.isEmpty()) usuario.setContrasenia(nuevaContrasenia);

        String cambiarRol = ValidacionesDeEntrada.ingresarOpcionNoVacia("¿Cambiar rol? (S/N): ");
        //Si decide cambiar el rol, nos ayudamos con un metodo ya creado
        if (cambiarRol.equalsIgnoreCase("S")) {
            usuario.setRol(seleccionarRolPorNumero());
        }

        //Llamamos a un metodo para actualizar el usuario
        //UsuarioDao.editarUsuario(usuario);
        System.out.println("Usuario actualizado correctamente.");

        //Actualizamos la lista usuariosDB
        //usuariosDB = UsuarioDao.obtenerTodos();
    }

    //Metodo eliminar
    public static void eliminarUsuario() {
        System.out.println("- Eliminar producto existente -");
        //Obtenemos el usuario a eliminar con la ayuda de un metodo ya creado
        Usuario usuario = seleccionarUsuarioPorNumero();

        //Verificamos si no habian usuarios cargados
        if (usuario == null) return;

        //Preguntamos si confirma la eliminacion del usuario
        String confirmacion = ValidacionesDeEntrada.ingresarOpcionNoVacia(
                "¿Confirma la eliminación de '" + usuario.getNombre() + " " + usuario.getApellido() + "'? (S/N): "
        );

        //De ser asi, lo eliminamos. Sino no
        if (confirmacion.equalsIgnoreCase("S")) {
            //Llamamos a un metodo para eliminar al usuario pasandole el ID
            //UsuarioDao.eliminarUsuario(usuario.getId());
            System.out.println("Usuario eliminado correctamente.");

            //Actualizamos la lista usuariosDB
            //usuariosDB = UsuarioDao.obtenerTodos();
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    //----METODOS AUXILIARES (VALIDACIONES INTERNAS)----
    private static ArrayList<Usuario> obtenerUsuariosNoEliminados() {
        //Armamos una lista de usuarios no eliminados
        ArrayList<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuariosDB) {
            if (!u.getEliminado()) resultado.add(u);
        }
        return resultado;
    }

    private static Usuario seleccionarUsuarioPorNumero() {
        //Obtenemos una lista de usuarios no eliminados con la ayuda de un metodo
        ArrayList<Usuario> noEliminados = obtenerUsuariosNoEliminados();

        //Si la lista esta vacia, lo notificamos y retornamos null
        if (noEliminados.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return null;
        }

        //Listamos los usuarios
        listarUsuarios();

        //Solicitamos que se seleccione un usuario
        int opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un usuario: ");
        //Validamos si dicha opcion seleccionada sea uno de los usuarios mostrados
        while (opcion < 1 || opcion > noEliminados.size()) {
            System.out.println("[ERROR] Ingrese una opción válida.");
            opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un usuario: ");
        }

        //Retornamos el usuario en la posicion ingresada
        return noEliminados.get(opcion - 1);
    }

    private static boolean existeEmail(String email) {
        //Verificamos existe un usuario con el email recibido
        for (Usuario u : usuariosDB) {
            //De ser asi, retornamos true
            if (u.getEmail().equalsIgnoreCase(email) && !u.getEliminado()) {
                return true;
            }
        }

        //Si reviso todo y no ecnontro nada devolvemos false
        return false;
    }

    private static Rol seleccionarRolPorNumero() {
        //Armamos una lista de roles con la ayuda de la funcion ".values()"
        Rol[] roles = Rol.values();

        //Mostramso la lista de roles
        for (int i = 0; i < roles.length; i++) {
            System.out.println((i + 1) + ". " + roles[i]);
        }

        //Solicitamos que se seleccione un rol
        int opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un rol: ");
        //Verificamos si el rol seleciconado fue uno de los mostrados
        while (opcion < 1 || opcion > roles.length) {
            System.out.println("[ERROR] Ingrese una opción válida.");
            opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un rol: ");
        }

        //Retornamos el rol en la posicion ingresada
        return roles[opcion - 1];
    }
}

