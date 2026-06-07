package integrador.entities;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Base {

    static protected AtomicInteger contador = new AtomicInteger(0);
    //Atributos
    protected int id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;


    //Constructor
    public Base() {
        this.id = contador.getAndIncrement();
        this.eliminado = false;
        this.createdAt =  LocalDateTime.now();
    }



    //Getters and setters

    public int getId() {return id;}

    public boolean isEliminado() {return eliminado;}

    public LocalDateTime getCreatedAt() {return createdAt;}


    //Metodos
}
