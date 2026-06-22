package integrador.entities;

import integrador.enums.Estado;
import integrador.enums.FormatoPago;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido extends Base implements Calculable {
    //Atributos
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormatoPago formatoPago;
    private ArrayList<DetallePedido> listaDetalles;
    private Usuario usuario;


    // Constructor

    public Pedido(FormatoPago formatoPago) {
        super();

        this.fecha = LocalDate.now().plusDays(3); // tres dias en el futuro por defecto
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.listaDetalles = new ArrayList<>();
        this.formatoPago = formatoPago;
        this.usuario = null;
    }


    // Constructor completo
    public Pedido(String id_pedido, boolean eliminado, LocalDateTime cratedAt,LocalDate fecha, Estado estado, double total, ArrayList<DetallePedido> listaDetalles,  FormatoPago formatoPago, Usuario usuario) {
        super(id_pedido,eliminado,cratedAt);

        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.listaDetalles = listaDetalles;
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

    public ArrayList<DetallePedido> getListaDetalles() { return listaDetalles; }

    public Usuario getUsuario(){return usuario;}

    //Setter
    public void setUsuario(Usuario usuario) {
        if(usuario != null){
            this.usuario = usuario;
        }
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setFormatoPago(FormatoPago formatoPago) {
        this.formatoPago = formatoPago;
    }

    // Metodos
    @Override
    public double calcularTotal(){
        double t = 0.0;
        for (DetallePedido detalle:listaDetalles){
            t += detalle.getSubtotal();
        }
        this.total = t; // Actualizamos el atributo "total" cada vez que lo calculamos
        return t;
    }

    public void addDetallePedido(int cantidad, Producto producto){
        DetallePedido detallePedido = new DetallePedido(cantidad,producto);
        listaDetalles.add(detallePedido);
        calcularTotal(); // Cada vez que agregamos un detalle, calculamos el total nuevamente
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
        boolean eliminado = false;
        for (DetallePedido detallePedido:listaDetalles){
            if(detallePedido.getProducto().equals(producto)){
                System.out.println("Se pudo eliminar el detalle del pedido");
                detallePedido.delete();
                eliminado = true;
                calcularTotal(); // Despues de eliminar un detallePedido del pedido, recalculamos el total
            }
        }
        if(!eliminado){
            System.out.println("No se pudo eliminar el detalle del pedido.");
        }
    }

    @Override
    public String toString(){
        //Le agregamos un formato para que se vea un poco mas lindo por consola.
        return String.format("Pedido ID: %s | Fecha: %s | Cliente: %-15s | Estado: %-10s | Pago: %-13s | Total: $%.2f",
                this.id,
                this.fecha,
                this.usuario.getNombre(),
                this.estado,
                this.formatoPago,
                total);
    }
}
