import integrador.config.DatabaseConfig;
import integrador.dao.CategoriaDao;
import integrador.dao.UsuarioDao;
import integrador.entities.Categoria;
import integrador.entities.Pedido;
import integrador.entities.Producto;
import integrador.entities.Usuario;
import integrador.enums.Rol;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;


public class Main{

    //Instanciamos el scanner
    private static Scanner scanner = new Scanner(System.in);


    //Simulamos tener ya conexion con base de datos para realizar pruebas sobre el menu
    //A esta simulacion lo hacemos con las siguientes arrayList
    private static ArrayList<Categoria> categoriasDB = new ArrayList<>();
    private static ArrayList<Producto> productosDB = new ArrayList<>();
    private static ArrayList<Usuario> usuariosDB = new ArrayList<>();
    private static ArrayList<Pedido> pedidosDB = new ArrayList<>();
    
    public static void main(String[] args) {
//        for(Usuario usuario : UsuarioDao.buscarPorNombre("it")){
//            System.out.println(usuario.getId()+" "+usuario.getNombre());
//        }
        //Realizamos el menu de opciones
        int opcion = -1;
        while (opcion != 0){
            //Mostramos el menu
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Ingrese una opcion: ");

            //Realizamos la opcion ingresada
            switch (opcion){
                case 1:
                    submenuCategoria();
                    break;
                case 2:
                    submenuProducto();
                    break;
                case 3:
                    submenuUsuario();
                case 4:
                    submenuPedido();
                    break;
                default:
                    System.out.println("Opcion ingresada invalida...");
            }
            scanner.close();
        }
    }
    //Armamos un metodo aparte por cada submenu
    public static void submenuCategoria(){
        int opcion = -1;
        while (opcion != 0){
            System.out.println("-- GESTION DE CATEGORIAS --");
            System.out.println("1. Listar categorías");
            System.out.println("2. Crear categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Seleccione una opcion: ");

            switch (opcion){
                case 1:
                    System.out.println("- Lista de categorias -");
                    break;
            }
        }
    }

    public static void submenuProducto(){
    }

    public static void submenuUsuario(){

    }

    public static void submenuPedido(){

    }

    //Metodos auxiliares (nos ayudan con las validaciones de entrada)
    private static int ingresarEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Ingrese un número entero válido.");
            }
        }
    }

    private static double ingresarDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Ingrese un número decimal válido.");
            }
        }
    }

    private static String ingresarOpcionNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("[ERROR] No puede estar vacio.");
        }
    }
}
