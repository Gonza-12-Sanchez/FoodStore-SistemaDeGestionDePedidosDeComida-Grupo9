package integrador.menus;

import integrador.dao.CategoriaDao;
import integrador.entities.Categoria;
import integrador.exception.ValidacionesDeEntrada;

import java.util.ArrayList;

public class MenuCategoria {
    //Dicha clase contiene toda la logica del submenu de categoria

    //Atributos
    private static ArrayList<Categoria> categoriasDB = CategoriaDao.obtenerTodas();

    //----ARMAMOS UN METODO POR CADA OPCION DEL MENU----
    //Metodo listar
    public static void listarCategorias(){
        System.out.println("- Lista de categorias -");
        //Obtenemos una lista de categorias no eliminadas con la ayuda de un metodo
        ArrayList<Categoria> categoriasNoEliminadas = obtenerCategoriasNoEliminadas();

        //Verificamos si esta vacia, de no ser asi la mostramos por pantalla
        if(categoriasNoEliminadas.isEmpty()){
            System.out.println("No hay categorias cargadas");
        }else {
            for (int i = 0; i < categoriasNoEliminadas.size(); i++) {
                System.out.println((i + 1) + ". " + categoriasNoEliminadas.get(i));
            }
        }
        System.out.println("-----------------");
    }

    //Metodo crear
    public static void crearCategoria(){
        System.out.println("- Creacion nueva categoria -");
        //Solicitamos al usuario que ingrese el nombre de la categoria
        String nombreCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre de la categoria: ");

        //Verificamos si ya existe una categoria con el mismo nombre
        while (existeCategoria(nombreCategoria)) {
            System.out.println("[ERROR] Ya existe una categoría con ese nombre. Ingrese otro nombre");
            nombreCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre de la categoria: ");
        }

        //Solicitamos al usuario que ingrese la descripcion de la categoria
        String descripcionCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Descripcion de la categoria: ");

        //Realizamos la creacion de la categoria y la insertamos en la base de datos
        Categoria nuevaCategoria = new Categoria(nombreCategoria, descripcionCategoria);
        CategoriaDao.insertarCategoria(nuevaCategoria);
        System.out.println("Categoría creada con ID: " + nuevaCategoria.getId());

        //Actualizamos la lista categoriasDB
        actualizarListaDB();
    }

    //Metodo editar
    public static void editarCategoria(){
        //Obtenemos la categoria seleccionada por el usuario con la ayuda de un metodo
        Categoria categoria = seleccionarCategoriaPorNumero();

        //Verificamos si no habian categorias cargadas
        if(categoria == null){
            return;
        }

        System.out.println("- Edicion categoria existente -");
        //Solicitamos al usuario que ingrese el nuevo nombre de la categoria
        String nombreCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre de la categoria: ");

        //Verificamos si ya existe una categoria con el mismo nombre
        while (existeCategoria(nombreCategoria)) {
            System.out.println("[ERROR] Ya existe una categoría con ese nombre.");
            nombreCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Nombre de la categoria: ");
        }

        //Solicitamos al usuario que ingrese la descripcion de la categoria
        String descripcionCategoria = ValidacionesDeEntrada.ingresarOpcionNoVacia("Descripcion de la categoria: ");

        //Llamamos a un metodo para actualizar la categoria
        CategoriaDao.editarCategoria(categoria.getId(),nombreCategoria,descripcionCategoria);
        System.out.println("Categoria actualizada correctamente.");

        //Actualizamos la lista categoriasDB
        actualizarListaDB();
    }

    //Metodo eliminar
    public static void eliminarCategoria(){
        System.out.println("- Eliminar categoria existente -");
        //Obtenemos la categoria a eliminar con la ayuda de un metodo
        Categoria  categoria = seleccionarCategoriaPorNumero();

        //Verificamos si no habian categorias cargadas
        if(categoria == null){
            return;
        }

        //Preguntamos si se confirma la eliminacion de la categoria
        String confirmacion = ValidacionesDeEntrada.ingresarOpcionNoVacia(
                "¿Confirma la eliminación de '" + categoria.getNombre() + "'? (S/N): ");

        //De ser asi, la eliminamos. Sino no
        if (confirmacion.equalsIgnoreCase("S")) {
            //Llamamos a un metodo para eliminar la categoria, pasandole el ID
            CategoriaDao.eliminarCategoria(categoria.getId());

            //Actualizamos la lista categoriasBD
            actualizarListaDB();

            System.out.println("Categoria eliminada correctamente.");
        } else {
            System.out.println("Se cancelo la eliminacion de la categoria.");
        }
    }


    //----METODOS AUXILIARES (VALIDACIONES INTERNAS)----
    private static boolean existeCategoria(String nombre){
        //Verificamos si ya existe una categoria con el nombre ingresado
        for (Categoria categoria : categoriasDB) {
            //Si encontramos una categoria que tenga el mismo nombre devolvemos true
            if (categoria.getNombre().equalsIgnoreCase(nombre) && !categoria.getEliminado()) {
                return true;
            }
        }

        //Si reviso todo y no encontro nada devolvemos false
        return false;
    }

    private static ArrayList<Categoria> obtenerCategoriasNoEliminadas() {
        //Armamos una lista de categorias no eliminadas
        ArrayList<Categoria> resultado = new ArrayList<>();
        for (Categoria categoria : categoriasDB) {
            if (!categoria.getEliminado()) {
                resultado.add(categoria);
            }
        }
        return resultado;
    }

    private static Categoria seleccionarCategoriaPorNumero(){
        //Obtenemos una lista de categorias no eliminadas con la ayuda de un metodo
        ArrayList<Categoria> noEliminadas = obtenerCategoriasNoEliminadas();

        //Si no hay categorias cargadas, avisamos y retornamos null
        if(noEliminadas.isEmpty()){
            System.out.println("No hay categorias cargadas.");
            return null;
        }

        //Mostramos el nombre de las categorias
        for(int i = 0; i < noEliminadas.size(); i++){
            System.out.println((i+1) + ". " + noEliminadas.get(i).getNombre());
        }

        //Solicitamos al usuario que seleccione una de las categorias mostradas
        int opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione una categoria: ");
        //Validamos que la opcion ingresada sea una de las categorias
        while(opcion < 1 || opcion > noEliminadas.size()){
            System.out.println("[ERROR] Ingrese una opcion valida.");
            opcion = ValidacionesDeEntrada.ingresarEntero("Seleccione una categoria: ");
        }

        //Retornamos la categoria en la posicion ingresada
        return noEliminadas.get(opcion - 1);
    }

    private static void actualizarListaDB(){
        categoriasDB = CategoriaDao.obtenerTodas();
    }
}
