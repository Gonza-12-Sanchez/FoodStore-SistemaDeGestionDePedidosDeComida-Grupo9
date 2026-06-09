package integrador.entities;

import integrador.enums.Estado;
import integrador.enums.FormatoPago;
import integrador.exception.ListaDetallePedidoException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private ArrayList<DetallePedido> listaDetalles;
    private FormatoPago formatoPago;

    // Constructor
    public Pedido(LocalDate fecha, Estado estado, ArrayList<DetallePedido> listaDetalles, FormatoPago formatoPago) {
        super();
        // validar (filtro) que listaDetalles no esté vacio.
        if(listaDetalles.isEmpty()){
            throw new ListaDetallePedidoException("Debe agregar al menos un detalle al pedido.");
        }

        this.fecha = fecha;
        this.estado = estado;
        this.total = calcularTotal(); // usa metodo calcularTotal()
        this.listaDetalles = listaDetalles;
        this.formatoPago = formatoPago;
    }


    // Getters


    // Metodos
    @Override
    public double calcularTotal(){
        double t = 0.0;
        for (DetallePedido detalle:listaDetalles){
            t += detalle.getSubtotal();
        }
        return t;
    }


}
