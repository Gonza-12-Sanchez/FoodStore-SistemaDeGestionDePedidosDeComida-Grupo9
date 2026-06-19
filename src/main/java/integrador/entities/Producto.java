package integrador.entities;

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
    public boolean isDisponible() {
        return disponible;
    }
    public Categoria getCategoria() {
        return categoria;
    }
}
