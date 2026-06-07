package integrador.entities;

public class Producto extends Base{
    protected String nombre;
    protected double precio;
    protected String descripcion;
    protected int stock;
    protected String imagen;
    protected boolean disponible;
    protected  Categoria categoria;


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


    public String getNombre(){
        return nombre;
    }

    public Categoria getCategoria(){
        return categoria;
    }
}
