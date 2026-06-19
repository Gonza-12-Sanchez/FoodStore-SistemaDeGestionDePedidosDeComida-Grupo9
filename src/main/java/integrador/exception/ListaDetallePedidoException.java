package integrador.exception;

public class ListaDetallePedidoException extends RuntimeException {

    public ListaDetallePedidoException(String message) {
        super("[ERROR] "+message);
    }
}
