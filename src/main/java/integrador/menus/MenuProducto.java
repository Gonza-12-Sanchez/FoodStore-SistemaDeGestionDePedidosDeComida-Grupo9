package integrador.menus;


import integrador.dao.CategoriaDao;
import integrador.dao.ProductoDao;
import integrador.entities.Categoria;
import integrador.entities.Producto;
import integrador.exception.ValidacionesDeEntrada;

import java.util.ArrayList;

public class MenuProducto {
    //Dicha clase contiene toda la logica del submenu de producto

    //Atributos
    private static ArrayList<Producto> productosDB = ProductoDao.obtenerTodos();

    //----ARMAMOS UN METODO POR CADA OPCION DEL MENU----
    //Metodo listar
    public static void listarProductos(){
        System.out.println("- Lista de productos -");
        //Obtenemos una lista de productos disponibles gracias a un metodo
        ArrayList<Producto> disponibles = obtenerProductosNoEliminados();

        //Verificamos si la lista de disponibles esta vacia, de no ser asi la mostramos por pantalla
        if (disponibles.isEmpty()) {
            System.out.println("No hay productos cargados.");
        } else {
            for (int i = 0; i < disponibles.size(); i++) {
                System.out.println((i + 1) + ". " + disponibles.get(i));
            }
        }
        System.out.println("-----------------");
    }

    //Metodo crear
    public static void crearProducto() {
        //Primero que nada verificamos si hay categorias disponibles
        //Si no hay categorias disponibles, no podemos crear un producto
        ArrayList<Categoria> categorias = obtenerCategoriasDisponibles();
        if (categorias.isEmpty()) {
            System.out.println("[ERROR] No hay categorías disponibles. Cree una antes de crear un producto.");
            return;
        }

        System.out.println("- Creacion nuevo producto -");
        //Si hay categorias disponibles, solicitamos los distintos datos necesarios
        String nombre = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre: ");
        String descripcion = ValidacionesDeEntrada.ingresarOpcionNoVacia("Descripción: ");
        double precio = ValidacionesDeEntrada.ingresarDoublePositivo("Precio: ");
        int stock = ValidacionesDeEntrada.ingresarEnteroPositivo("Stock: ");
        String imagen = ValidacionesDeEntrada.ingresarOpcionNoVacia("Imagen: ");

        //Solicitamos que se elija una categoria disponible
        Categoria categoria = seleccionarCategoriaPorNumero(categorias);

        //Con todos los datos necesarios, procedemos a crear el producto y la insertamos en la BD
        Producto nuevo = new Producto(nombre, precio, descripcion, stock, imagen, categoria);
        ProductoDao.insertarProducto(nuevo);
        System.out.println("Producto creado con ID: " + nuevo.getId());

        //Actualizamos la lista productosBD
        productosDB = ProductoDao.obtenerTodos();
    }

    //Metodo editar
    public static void editarProducto() {
        //Solicitamos que se seleccione un producto
        Producto producto = seleccionarProductoPorNumero();

        //Si no hay productos cargados devolvemos null
        if (producto == null){
            return;
        }

        System.out.println("- Edicion producto existente -");
        System.out.println("(Si no queres modificar un valor, directamente toca enter :D)\n " +
                "(Los valores actuales se van a mostrar entre parentesis)");

        //Solicitamos que se ingresen los nuevos valores
        //Si los valores ingresados no estan vacios, los seteamos
        String nuevoNombre = ValidacionesDeEntrada.ingresarOpcional("Nombre (" + producto.getNombre() + "): ");
        if (!nuevoNombre.isEmpty()) producto.setNombre(nuevoNombre);

        String nuevaDesc = ValidacionesDeEntrada.ingresarOpcional("Descripción (" + producto.getDescripcion() + "): ");
        if (!nuevaDesc.isEmpty()) producto.setDescripcion(nuevaDesc);

        //Primero leemos el precio en string, asi le damos la posibilidad de no querer modificar el valor
        String precioString = ValidacionesDeEntrada.ingresarOpcional("Precio (" + producto.getPrecio() + "): ");
        //Si se ingresa un valor relizamos 2 validaciones:
        //1. Que el valor ingresado sea double
        //2. Que el valor sea mayor o igual a 0
        //(No utilizamos las validaciones de la clase "ValidacionesDeEntrada" ya que,
        // si se ingresa un valor mal, directamente queda el valor actual del producto)
        if (!precioString.isEmpty()) {
            try {
                double precio = Double.parseDouble(precioString);
                if (precio >= 0) producto.setPrecio(precio);
                else System.out.println("[ERROR] Precio inválido, se mantiene el anterior.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Valor ingresado inválido, se mantiene el anterior.");
            }
        }

        //Primero leemos el stock en string, asi le damos la posibilidad de no querer modificar el valor
        String stockString = ValidacionesDeEntrada.ingresarOpcional("Stock (" + producto.getStock() + "): ");
        //Si ingresa un valor relizamos 2 validaciones:
        //1. Que el valor ingresado sea int
        //2. Que el valor sea mayor o igual a 0
        //(No utilizamos las validaciones de la clase "ValidacionesDeEntrada" ya que,
        // si se ingresa un valor mal, directamente queda el valor actual del producto)
        if (!stockString.isEmpty()) {
            try {
                int stock = Integer.parseInt(stockString);
                if (stock >= 0) producto.setStock(stock);
                else System.out.println("[ERROR] Stock inválido, se mantiene el anterior.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Valor ingresado inválido, se mantiene el anterior.");
            }
        }

        String nuevaImagen = ValidacionesDeEntrada.ingresarOpcional("Imagen (" + producto.getImagen() + "): ");
        if (!nuevaImagen.isEmpty()) producto.setImagen(nuevaImagen);

        //En el caso de las categorias preguntamos si la desea cambiar o no.
        String cambiarCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("¿Cambiar categoría? (S/N): ").toUpperCase();
        //En caso de querer cambiarla obtenemos las categorias disponibles
        //Si hay categorias disponibles, seteamos una categoria a eleccion
        //Sino mostramos un mensaje
        if (cambiarCategoria.equalsIgnoreCase("S")) {
            ArrayList<Categoria> categorias = obtenerCategoriasDisponibles();
            if (categorias.isEmpty()) {
                System.out.println("[ERROR] No hay categorías disponibles.");
            } else {
                producto.setCategoria(seleccionarCategoriaPorNumero(categorias));
            }
        }

        //Llamamos a un metodo para actualizar el producto
        //ProductoDao.editarProducto(producto);
        System.out.println("Producto actualizado correctamente.");

        //Actualizamos la lista productosDB
        productosDB = ProductoDao.obtenerTodos();
    }

    //Metodo eliminar
    public static void eliminarProducto(){
        System.out.println("- Eliminar producto existente -");
        //Obtenemos el producto a eliminar con la ayuda de un metodo
        Producto producto = seleccionarProductoPorNumero();

        //Verificamos si no habian productos cargados
        if(producto == null){
            return;
        }

        //Preguntamos si se confirma la eliminacion del producto
        String confirmacion = ValidacionesDeEntrada.ingresarOpcionNoVacia(
                "¿Confirma la eliminación de '" + producto.getNombre() + "'? (S/N): ");

        //De ser asi, lo eliminamos. Sino no
        if (confirmacion.equalsIgnoreCase("S")) {
            //Llamamos a un metodo para eliminar al producto, pasandole el ID
            ProductoDao.eliminar(producto.getId());

            //Actualizamos la lista productosBD
            productosDB = ProductoDao.obtenerTodos();

            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Se cancelo la eliminacion del producto.");
        }
    }

    //----METODOS AUXILIARES (VALIDACIONES INTERNAS)----
    private static ArrayList<Producto> obtenerProductosNoEliminados() {
        //Armamos una lista de productos no eliminados
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : productosDB) {
            if (!p.getEliminado()) resultado.add(p);
        }
        return resultado;
    }

    private static Producto seleccionarProductoPorNumero() {
        //Obtenemos una lista de productos no eliminados con la ayuda de un metodo
        ArrayList<Producto> noEliminados = obtenerProductosNoEliminados();

        //Si no hay productos cargados, avisamos y retornamos null
        if (noEliminados.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return null;
        }

        //Listamos los productos disponibles con ayuda de un metodo
        listarProductos();

        //Solicitamos que seleccione un producto
        int opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un producto: ");
        //Validamos que la opcion ingresada sea uno de los productos mostrados
        while (opcion < 1 || opcion > noEliminados.size()) {
            System.out.println("[ERROR] Ingrese una opción válida.");
            opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione un producto: ");
        }

        //Retornamos el producto en la posicion ingresada
        return noEliminados.get(opcion - 1);
    }

    private static ArrayList<Categoria> obtenerCategoriasDisponibles() {
        //Obtenemos las categorias disponibles
        ArrayList<Categoria> disponibles = new ArrayList<>();
        for (Categoria c : CategoriaDao.obtenerTodas()) {
            if (!c.getEliminado()) disponibles.add(c);
        }
        return disponibles;
    }

    private static Categoria seleccionarCategoriaPorNumero(ArrayList<Categoria> categorias) {
        //Mostramos las categorias disponibles
        System.out.println("- Categorías disponibles -");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i));
        }

        //Solicitamos que seleccione una categoria disponible
        int opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione una categoría: ");

        //Validamos que la opcion ingresada sea una de las categorias
        while (opcion < 1 || opcion > categorias.size()) {
            System.out.println("[ERROR] Ingrese una opción válida.");
            opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione una categoría: ");
        }

        //Retornamos la categoria seleccionada
        return categorias.get(opcion - 1);
    }

}
