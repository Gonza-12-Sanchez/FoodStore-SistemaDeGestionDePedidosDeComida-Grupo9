package integrador.entities;

import integrador.exception.CantidadInvalidaException;

public class DetallePedido extends Base implements Calculable{
    //Atributos
    private int cantidad;
    private double subtotal;
    private Producto producto;

    //Constructor
    public DetallePedido(int cantidad, Producto producto) {
        super();

        if(cantidad <= 0){
            throw new CantidadInvalidaException("La cantidad ingresada es invalida. Esta tiene que ser mayor a 0");

        }

        this.cantidad = cantidad;
        this.subtotal = calcularTotal();
        this.producto = producto;

    }

    // Getters
    public int getCantidad() {
        return cantidad;
    }
    public double getSubtotal(){
        return subtotal;
    }
    public Producto getProducto() {
        return producto;
    }

    // Metodos
    @Override
    public double calcularTotal() {
        return cantidad * producto.getPrecio();
    }
}
