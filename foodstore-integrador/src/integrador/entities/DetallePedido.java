package integrador.entities;

import integrador.exception.CantidadInvalidaException;

public class DetallePedido extends Base implements Calculable{
    private int cantidad;
    private Producto producto;
    private double subtotal;

    public DetallePedido(int cantidad, Producto producto) {

        if(cantidad <= 0){
            throw new CantidadInvalidaException("Cantidad de producto debe ser mayor a 0");
        }

        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularTotal();
    }


    // Getters
    public double getSubtotal(){
        return subtotal;
    }

    // Metodos

    @Override
    public double calcularTotal() {
        return cantidad * producto.getPrecio();
    }
}
