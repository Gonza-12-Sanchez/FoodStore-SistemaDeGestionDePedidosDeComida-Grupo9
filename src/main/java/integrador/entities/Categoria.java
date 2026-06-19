package integrador.entities;

import java.time.LocalDateTime;

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

    public Categoria(String id_categoria, boolean eliminado, LocalDateTime createdAt, String nombre, String descripcion){
        super(id_categoria,eliminado,createdAt);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getters
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}

    //Metodos

    @Override
    public String toString() {
        return "[ID: "+this.id+ "] "+this.nombre+" - "+this.descripcion;
    }
}
