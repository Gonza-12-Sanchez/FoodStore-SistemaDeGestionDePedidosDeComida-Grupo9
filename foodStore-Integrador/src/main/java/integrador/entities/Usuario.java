package integrador.entities;

import integrador.enums.Rol;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private Rol rol;

    public Usuario(String nombre, String apellido, String email, String celular, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
    }



}
