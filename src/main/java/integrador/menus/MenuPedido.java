package integrador.menus;

import integrador.dao.PedidoDao;
import integrador.dao.ProductoDao;
import integrador.dao.UsuarioDao;
import integrador.entities.Pedido;
import integrador.entities.Producto;
import integrador.entities.Usuario;
import integrador.enums.Estado;
import integrador.enums.FormatoPago;
import integrador.exception.ValidacionesDeEntrada;

import java.time.LocalDate;
import java.util.ArrayList;

import static integrador.exception.ValidacionesDeEntrada.ingresarEntero;

public class MenuPedido {
    //Dicha clase contiene toda la logica del submenu de pedido

    //Atributos
    private static ArrayList<Pedido> pedidosDB = PedidoDao.obtenerTodos();

    //----ARMAMOS UN METODO POR CADA OPCION DEL MENU----
    //Metodo listar
    public static void listarPedidos() {
        System.out.println("- Lista de Pedidos -");
        //Obtenemos los pedidos disponibles
        ArrayList<Pedido> noEliminados = obtenerPedidosNoEliminados();

        //Si la lista no esta vacia la mostramos por pantalla
        if (noEliminados.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (int i = 0; i < noEliminados.size(); i++) {
                System.out.println((i + 1) + ". " + noEliminados.get(i));
            }
        }
        System.out.println("-----------------");
    }

    //Metodo crear
    public static void crearPedido() {
        //Solicitamos que seleccion un usuario disponible
        Usuario cliente = seleccionarUsuario();

        //Verificamos si no habian clientes disponibles
        if (cliente == null) return;

        //Solicitamos que se seleccione un formato de pago
        FormatoPago formatoPago = seleccionarFormatoPago();

        //Instanciamos un nuevo pedido (por defecto se crea como PENDIENTE y con la fecha actual)
        Pedido nuevoPedido = new Pedido(formatoPago);
        nuevoPedido.setUsuario(cliente);

        boolean seguirComprando = true;

        //Con la ayuda de un while solicitamos que se agregen productos al pedido (los detalles)
        System.out.println("\n-- AGREGAR PRODUCTOS AL PEDIDO --");
        while (seguirComprando) {
            //Solicitamos que seleccione un producto
            Producto productoSeleccionado = seleccionarProducto();

            //Si el producto es diferente de nulo, solicitamos su cantidad
            //Luego agregamos el detalle pedido al pedido
            if (productoSeleccionado != null) {
                // Validamos que la cantidad no sea negativa
                int cantidad = ingresarEntero("Ingrese la cantidad para '" + productoSeleccionado.getNombre() + "': ");

                //Validamos que la cantidad ingresada sea mayor a 0 y menor al stock del producto disponible
                while (cantidad <= 0 || cantidad > productoSeleccionado.getStock()) {
                    System.out.println("[ERROR] Cantidad inválida o supera el stock disponible (" + productoSeleccionado.getStock() + ").");
                    cantidad = ingresarEntero("Ingrese la cantidad: ");
                }

                //Agregamos el detalle del pedido al pedido
                nuevoPedido.addDetallePedido(cantidad, productoSeleccionado);
                System.out.println("Producto agregado al pedido. Subtotal: $" + nuevoPedido.getListaDetalles().getLast().getSubtotal());
            }

            //Preguntamos si desea seguir agregando productos
            String opcion = ValidacionesDeEntrada.ingresarOpcionNoVacia("¿Desea agregar otro producto? (S/N): ");
            if (opcion.equalsIgnoreCase("N")) {
                seguirComprando = false;
            }
        }

        //Validamos que el pedido no esté vacío
        if (nuevoPedido.getListaDetalles().isEmpty()) {
            System.out.println("[ERROR] El pedido no tiene productos. Operación cancelada.");
            return;
        }

        //Utilizamos la interfaz Calculable para calcular el total del pedido
        double totalFinalPedido = nuevoPedido.calcularTotal();

        //Con la ayuda de un metodo insertamos el pedido a la BD
        PedidoDao.insertarPedido(nuevoPedido);
        System.out.println("\nPedido registrado correctamente.");
        System.out.println("Total a pagar: $" + totalFinalPedido);

        //Actualizamos la lista de pedidosBD
        pedidosDB = PedidoDao.obtenerTodos();
    }

    //Metodo editar
    public static void editarPedido() {
        //Solicitamos que se seleccione un pedido
        Pedido pedido = seleccionarPedidoPorNumero();

        //Verificamos si no habian pedidos disponibles
        if (pedido == null) return;

        //Mostramos el pedido actual
        System.out.println("\n- Actualiza estado / pago -");
        System.out.println("Pedido actual: " + pedido);

        //Preguntamos si decide modificar el estado o el formato de pago
        String cambiarEstado = ValidacionesDeEntrada.ingresarOpcionNoVacia("¿Modificar Estado? (S/N): ");
        //Si decide cambiar el estado nos apoyamos en el metodo ya creado
        if (cambiarEstado.equalsIgnoreCase("S")) {
            pedido.setEstado(seleccionarEstado());
            System.out.println("[AVISO] Estado actualizado temporalmente en memoria.");
        }

        String cambiarPago = ValidacionesDeEntrada.ingresarOpcionNoVacia("¿Modificar Formato de Pago? (S/N): ");
        //Si decide cambiar el formato de pago nos apoyamos en el metodo ya creado
        if (cambiarPago.equalsIgnoreCase("S")) {
            pedido.setFormatoPago(seleccionarFormatoPago());
            System.out.println("[AVISO] Formato de pago actualizado temporalmente en memoria.");
        }

        //Llamamos a un metodo para actualizar el pedido
        PedidoDao.editarPedido(pedido.getId(), pedido.getEstado(), pedido.getFormatoPago());
        System.out.println("Pedido actualizado correctamente en la base de datos.");

        //Actualizamos la lista pedidosDB
        pedidosDB = PedidoDao.obtenerTodos();
    }

    //Metodo eliminar
    public static void eliminarPedido() {
        System.out.println("- Eliminar pedido existente -");
        //Solicitamos que se seleccione un pedido disponible
        Pedido pedido = seleccionarPedidoPorNumero();

        //Verificamos si no habian pedidos disponibles
        if (pedido == null) return;

        //Preguntamos si confirma la eliminacion del pedido
        String confirmacion = ValidacionesDeEntrada.ingresarOpcionNoVacia(
                "¿Confirma la eliminación del pedido ID '" + pedido.getId() + "'? (S/N): "
        );

        //De ser asi, lo eliminamos. Sino no
        if (confirmacion.equalsIgnoreCase("S")) {
            //Llamamos a un metodo para eliminar al pedido pasandole el ID
            PedidoDao.eliminar(pedido.getId());
            System.out.println("Pedido eliminado lógicamente de forma correcta.");

            //Actualizamos la lista pedidosDB
            pedidosDB = PedidoDao.obtenerTodos();
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    //----METODOS AUXILIARES (VALIDACIONES INTERNAS)----
    private static ArrayList<Pedido> obtenerPedidosNoEliminados() {
        //Armamos un array de pedidos no eliminados y lo retornamos
        ArrayList<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidosDB) {
            if (!p.getEliminado()) resultado.add(p);
        }
        return resultado;
    }

    private static Pedido seleccionarPedidoPorNumero() {
        //Obtenemos los pedidos no eliminados con la ayuda de un metodo creado
        ArrayList<Pedido> noEliminados = obtenerPedidosNoEliminados();

        //Si la lista esta vacia, retornamos null
        if (noEliminados.isEmpty()) {
            System.out.println("No hay pedidos disponibles.");
            return null;
        }

        //Listamos los pedidos disponibles
        listarPedidos();

        //Solicitamos que se seleccione un pedido
        int opcion = ingresarEntero("Seleccione un pedido: ");
        //Verificamos que dicho pedido seleccionado este entre los mostrados
        while (opcion < 1 || opcion > noEliminados.size()) {
            System.out.println("[ERROR] Ingrese una opción válida.");
            opcion = ingresarEntero("Seleccione un pedido: ");
        }

        //Retornamos el pedido en la posicion seleccionada
        return noEliminados.get(opcion - 1);
    }

    private static Usuario seleccionarUsuario() {
        //Obtenemos los usuarios disponibles
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (Usuario u : UsuarioDao.obtenerTodos()) {
            if (!u.getEliminado()) usuarios.add(u);
        }

        //Si la lista esta vacia, devolvemos null
        if (usuarios.isEmpty()) {
            System.out.println("[ERROR] No hay usuarios registrados para realizar el pedido.");
            return null;
        }

        //Listamos los usuarios disponibles (solo mostramos su nombre y apellido)
        System.out.println("- Lista Clientes -");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre() + " " + usuarios.get(i).getApellido());
        }

        //Solicitamos que se seleccione uno
        int opcion = ingresarEntero("Opción: ");
        //Verificamos que el usuario seleccionado este entre los mostrados
        while (opcion < 1 || opcion > usuarios.size()) {
            System.out.println("[ERROR] Opción inválida.");
            opcion = ingresarEntero("Opción: ");
        }

        //Retornamos el usuario en la posicion seleccionada
        return usuarios.get(opcion - 1);
    }

    private static Producto seleccionarProducto() {
        //Obtenemos los productos disponibles
        ArrayList<Producto> productos = new ArrayList<>();
        for (Producto p : ProductoDao.obtenerTodos()) {
            //Validamos que el producto no este eliminado, este disponible y que su stock sea mayor a 0
            if (!p.getEliminado() && p.isDisponible() && p.getStock() > 0) productos.add(p);
        }

        //Si la lista esta vacia, devolvemos null
        if (productos.isEmpty()) {
            System.out.println("[ERROR] No hay productos con stock disponible en este momento.");
            return null;
        }


        //Listamos los productos disponibles
        System.out.println("\n- Lista de Productos -");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + " - $" + p.getPrecio() + " (Stock: " + p.getStock() + ")");
        }

        //Solicitamos que seleccione un producto (puede colocar 0 para cancelar el producto)
        int opcion = ingresarEntero("Seleccione un producto (0 para cancelar): ");
        if(opcion == 0) return null;

        //Verificamos que el producto seleccionado este entre los mostrados
        while (opcion < 1 || opcion > productos.size()) {
            System.out.println("[ERROR] Opción inválida.");
            opcion = ingresarEntero("Seleccione un producto (0 para cancelar): ");
            if(opcion == 0) return null;
        }

        //Retornamos el producto en su posicion seleccionada
        return productos.get(opcion - 1);
    }

    private static Estado seleccionarEstado() {
        //Creamos una lista con los distintos estados con la ayuda de la funcion ".values()"
        Estado[] estados = Estado.values();

        System.out.println("\n- Estado del Pedido -");
        //Los mostramos por pantalla
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }

        //Solicitamos que seleccione una opcion
        int opcion = ingresarEntero("Seleccione el nuevo estado: ");
        //Verificamos si la opcion ingresa esta entre las mostradas
        while (opcion < 1 || opcion > estados.length) {
            System.out.println("[ERROR] Opción inválida.");
            opcion = ingresarEntero("Seleccione el nuevo estado: ");
        }

        //Retornamos el formato en la posicion seleccionada
        return estados[opcion - 1];
    }

    private static FormatoPago seleccionarFormatoPago() {
        //Creamos una lista con los distintos formatos de pago con la ayuda de la funcion ".values()"
        FormatoPago[] formatos = FormatoPago.values();

        System.out.println("\n- Lista Formato de Pago -");
        //Los mostramos por pantalla
        for (int i = 0; i < formatos.length; i++) {
            System.out.println((i + 1) + ". " + formatos[i]);
        }

        //Solicitamos que seleccione una opcion
        int opcion = ingresarEntero("Opción: ");
        //Verificamos si la opcion ingresa esta entre las mostradas
        while (opcion < 1 || opcion > formatos.length) {
            System.out.println("[ERROR] Opción inválida.");
            opcion = ingresarEntero("Opción: ");
        }

        //Retornamos el formato en la posicion seleccionada
        return formatos[opcion - 1];
    }
}
