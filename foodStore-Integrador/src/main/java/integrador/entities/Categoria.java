package integrador.entities;

public class Categoria extends Base{
    //Atributos
    private String nombre;
    private String descripcion;

    //Constructor
    public Categoria(String nombre,String descripcion){
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getters
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
}
