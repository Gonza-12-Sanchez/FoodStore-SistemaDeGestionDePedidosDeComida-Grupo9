package integrador.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Base {

    //Atributos
    protected String id;
    protected boolean eliminado;
    protected String createdAt;

    //Constructor
    public Base() {
        this.id = UUID.randomUUID().toString();
        this.eliminado = false;
        this.createdAt =  LocalDateTime.now().toString();
    }

    public Base(String id, boolean eliminado, String createdAt) {
        this.id = id;
        this.eliminado = eliminado;
        this.createdAt =  createdAt;
    }

    //Getters
    public String getId() {return id;}
    public boolean isEliminado() {return eliminado;}
    public String getCreatedAt() {return createdAt;}

    //Metodos
    public void delete() {
        this.eliminado = true;
    }
}
