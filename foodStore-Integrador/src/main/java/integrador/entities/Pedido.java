package integrador.entities;

import integrador.enums.Estado;
import integrador.enums.FormatoPago;
import integrador.exception.ListaDetallePedidoException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido extends Base implements Calculable{
    //Atributos
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormatoPago formatoPago;
    private ArrayList<DetallePedido> listaDetalles;
    private Usuario usuario;

    // Constructor
    public Pedido(LocalDate fecha, Estado estado, FormatoPago formatoPago, Usuario usuario) {
        super();
        // validar (filtro) que listaDetalles no esté vacio.
        if(listaDetalles.isEmpty()){
            throw new ListaDetallePedidoException("Debe agregar al menos un detalle al pedido.");
        }

        this.fecha = fecha;
        this.estado = estado;
        this.total = calcularTotal(); // usa metodo calcularTotal()
        this.listaDetalles = new ArrayList<>();
        this.formatoPago = formatoPago;
        this.usuario = usuario;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }
    public Estado getEstado() {
        return estado;
    }
    public double getTotal() {
        return total;
    }
    public FormatoPago getFormatoPago() {
        return formatoPago;
    }

    //Setter
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Metodos
    @Override
    public double calcularTotal(){
        double t = 0.0;
        for (DetallePedido detalle:listaDetalles){
            t += detalle.getSubtotal();
        }
        return t;
    }

    public void addDetallePedido(int cantidad, Producto producto ){
        DetallePedido detallePedido = new DetallePedido(cantidad,producto);
        listaDetalles.add(detallePedido);
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto){
        for (DetallePedido detallePedido:listaDetalles){
            if(detallePedido.getProducto().equals(producto)){
                System.out.println("Se pudo eliminar el detalle del pedido correctamente");
                return detallePedido;
            }
        }
        System.out.println("No se pudo encontrar el detalle del pedido.");
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto){
        for (DetallePedido detallePedido:listaDetalles){
            if(detallePedido.getProducto().equals(producto)){
                System.out.println("Se pudo eliminar el detalle del pedido");
                detallePedido.delete();
            }
        }
        System.out.println("No se pudo eliminar el detalle del pedido.");
    }
}
