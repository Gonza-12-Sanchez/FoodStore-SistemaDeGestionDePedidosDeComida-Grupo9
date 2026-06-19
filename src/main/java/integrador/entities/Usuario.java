package integrador.entities;

import integrador.enums.Rol;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Usuario extends Base{
    //Atributos
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private ArrayList<Pedido> pedidos;

    //Constructor
    public Usuario(String nombre, String apellido, String email, String celular,String contrasenia, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    // constructor para usar cuando se obtenga desde la base de datos
    public Usuario(String id_usuario, boolean eliminado, LocalDateTime createdAt, String nombre, String apellido, String email, String celular, String contrasenia, Rol rol) {
        super(id_usuario,eliminado,createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public String getCelular() {
        return celular;
    }
    public String getContrasenia(){return contrasenia;}
    public Rol getRol() {
        return rol;
    }

    //Metodos
    public void agregarPedido(Pedido pedido) {
        if(!pedidos.contains(pedido)){
            pedidos.add(pedido);
            pedido.setUsuario(this);
        }
    }

    @Override
    public String toString() {
        return "[ID: "+this.id+ "] "+this.nombre+" "+this.apellido+" ("+this.rol+") "+" - email: "+this.email;
    }
}
