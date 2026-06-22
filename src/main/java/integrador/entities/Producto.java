package integrador.entities;

import java.time.LocalDateTime;

public class Producto extends Base{
    //Atributos
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    //Constructor
    public Producto(String nombre, double precio, String descripcion, int stock, String imagen,Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;

        if (stock >0){
            this.disponible = true;
        }else{
            this.disponible = false;
        }
    }

    // Constructor completo
    public Producto(String id_producto, boolean eliminado , LocalDateTime createdAt,String nombre, double precio, String descripcion, int stock, String imagen,Categoria categoria){
        super(id_producto,eliminado,createdAt);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;

        if (stock >0){
            this.disponible = true;
        }else{
            this.disponible = false;
        }
    }

    // Getters
    public String getNombre() {
        return nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getStock() {
        return stock;
    }
    public String getImagen() {
        return imagen;
    }
    public boolean isDisponible() {
        return disponible;
    }
    public Categoria getCategoria() {
        return categoria;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(int stock) {
        this.stock = stock;

        //Disponible se actualiza solo
        this.disponible = stock > 0;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    //Metodos
    @Override
    public String toString() {
        return "[ID: "+this.id+ "] "+this.nombre+" ("+this.categoria.getNombre()+") "+" - $"+this.precio+" / Stock: "+this.stock;
    }
}
