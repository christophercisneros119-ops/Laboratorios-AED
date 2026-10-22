package Estructuras;

/** Lanzada al intentar eliminar de una estructura que esta vacia. */
public class SubdesbordamientoException extends Exception {

    public SubdesbordamientoException(String mensaje) {
        super(mensaje);
    }
}
