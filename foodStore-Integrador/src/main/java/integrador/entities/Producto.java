package integrador.entities;

public class Producto extends Base{
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;


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

    public String getNombre(){
        return nombre;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public double getPrecio(){ return precio; }

}
