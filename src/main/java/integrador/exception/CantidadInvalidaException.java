package integrador.exception;

public class CantidadInvalidaException extends RuntimeException{

    public CantidadInvalidaException(String mensaje) {
        super("[ERROR] "+mensaje);
    }
}
