package Polaca;

/** La expresion infija no cumple el formato que esperan los algoritmos. */
public class ExpresionInvalidaException extends Exception {

    public ExpresionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
