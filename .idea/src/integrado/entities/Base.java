package integrado.entities;

import java.time.LocalDateTime;

public abstract class Base {
    //Atributos
    protected int id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;
    //Constructor
    public Base(int id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }
    //Getters and setters
    //Metodos
}
