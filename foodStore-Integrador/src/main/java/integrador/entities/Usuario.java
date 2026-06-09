package integrador.entities;

import integrador.enums.Rol;

import java.util.ArrayList;

public class Usuario extends Base{
    //Atributos
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contraseña;
    private Rol rol;
    private ArrayList<Pedido> pedidos;

    //Constructor
    public Usuario(String nombre, String apellido, String email, String celular,String contraseña, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contraseña = contraseña;
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
}
