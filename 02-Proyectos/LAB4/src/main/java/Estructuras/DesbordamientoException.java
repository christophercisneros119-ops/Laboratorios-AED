package Estructuras;

/** Lanzada al intentar insertar en una estructura que ya esta llena. */
public class DesbordamientoException extends Exception {

    public DesbordamientoException(String mensaje) {
        super(mensaje);
    }
}
