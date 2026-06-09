package integrador.entities;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;

    public Categoria(String nombre,String descripcion){
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {return nombre;}

    public String getDescripcion() {return descripcion;}
}
