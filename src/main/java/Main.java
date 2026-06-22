import integrador.menus.MenuCategoria;
import integrador.menus.MenuPedido;
import integrador.menus.MenuProducto;
import integrador.menus.MenuUsuario;


import static integrador.exception.ValidacionesDeEntrada.ingresarEntero;


public class Main{

    public static void main(String[] args) {
        //Realizamos el menu de opciones
        int opcion;
        do{
            //Mostramos el menu
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Ingrese una opcion: ");

            //Validamos que la opcion ingresada sea correcta
            while(opcion < 0 || opcion > 4){
                System.out.println("[ERROR] Opcion invalida. Ingrese un numero del 0 al 4");
                opcion = ingresarEntero("Seleccione una opcion: ");
            }

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
                    break;
                case 4:
                    submenuPedido();
                    break;
            }
        }while (opcion != 0);
    }

    //Armamos un metodo aparte por cada submenu
    public static void submenuCategoria(){
        int opcion;
        do{
            System.out.println("-- GESTION DE CATEGORIAS --");
            System.out.println("1. Listar categorias");
            System.out.println("2. Crear categoria");
            System.out.println("3. Editar categoria");
            System.out.println("4. Eliminar categoria");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Seleccione una opcion: ");

            //Validamos que la opcion ingresada sea correcta
            while(opcion < 0 || opcion > 4){
                System.out.println("[ERROR] Opcion invalida. Ingrese un numero del 0 al 4");
                opcion = ingresarEntero("Seleccione una opcion: ");
            }

            switch (opcion){
                case 1:
                    MenuCategoria.listarCategorias();
                    break;
                case 2:
                    MenuCategoria.crearCategoria();
                    break;
                case 3:
                    MenuCategoria.editarCategoria();
                    break;
                case 4:
                    MenuCategoria.eliminarCategoria();
                    break;
            }
        }while (opcion != 0);
    }

    public static void submenuProducto(){
        int opcion;
        do{
            System.out.println("-- GESTION DE PRODUCTOS --");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Seleccione una opcion: ");

            //Validamos que la opcion ingresada sea correcta
            while(opcion < 0 || opcion > 4){
                System.out.println("[ERROR] Opcion invalida. Ingrese un numero del 0 al 4");
                opcion = ingresarEntero("Seleccione una opcion: ");
            }

            switch (opcion){
                case 1:
                    MenuProducto.listarProductos();
                    break;
                case 2:
                    MenuProducto.crearProducto();
                    break;
                case 3:
                    MenuProducto.editarProducto();
                    break;
                case 4:
                    MenuProducto.eliminarProducto();
                    break;
            }
        }while (opcion != 0);
    }

    public static void submenuUsuario(){
        int opcion;
        do{
            System.out.println("-- GESTION DE USUARIOS --");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Seleccione una opcion: ");

            //Validamos que la opcion ingresada sea correcta
            while(opcion < 0 || opcion > 4){
                System.out.println("[ERROR] Opcion invalida. Ingrese un numero del 0 al 4");
                opcion = ingresarEntero("Seleccione una opcion: ");
            }

            switch (opcion){
                case 1:
                    MenuUsuario.listarUsuarios();
                    break;
                case 2:
                    MenuUsuario.crearUsuario();
                    break;
                case 3:
                    MenuUsuario.editarUsuario();
                    break;
                case 4:
                    MenuUsuario.eliminarUsuario();
                    break;
            }
        }while (opcion != 0);
    }

    public static void submenuPedido(){
        int opcion;
        do{
            System.out.println("-- GESTION DE PEDIDOS Y DETALLES --");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido con detalles");
            System.out.println("3. Actualizar estado/forma de pago del pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Salir");
            opcion = ingresarEntero("Seleccione una opcion: ");

            //Validamos que la opcion ingresada sea correcta
            while(opcion < 0 || opcion > 4){
                System.out.println("[ERROR] Opcion invalida. Ingrese un numero del 0 al 4");
                opcion = ingresarEntero("Seleccione una opcion: ");
            }

            switch (opcion){
                case 1:
                    MenuPedido.listarPedidos();
                    break;
                case 2:
                    MenuPedido.crearPedido();
                    break;
                case 3:
                    MenuPedido.editarPedido();
                    break;
                case 4:
                    MenuPedido.eliminarPedido();
                    break;
            }
        }while (opcion != 0);
    }
}
