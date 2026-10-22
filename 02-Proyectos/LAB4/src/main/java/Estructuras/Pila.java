package Estructuras;

import java.util.ArrayList;
import java.util.List;

/**
 * Pila sobre un arreglo fijo, igual que el pseudocodigo del curso: el tope
 * parte en -1 cuando esta vacia y llega a capacidad-1 cuando esta llena.
 * Las operaciones no escriben mensajes: lanzan la excepcion que corresponde
 * y quien llama decide como mostrarla.
 */
public class Pila {

    private final String[] elementos;
    private int tope;

    public Pila(int capacidad) {
        if (capacidad < 1) {
            throw new IllegalArgumentException("La capacidad debe ser al menos 1");
        }
        elementos = new String[capacidad];
        tope = -1;
    }

    /** PilaLlena del pseudocodigo. */
    public boolean estaLlena() {
        return tope == elementos.length - 1;
    }

    /** PilaVacia del pseudocodigo. */
    public boolean estaVacia() {
        return tope == -1;
    }

    /** Push del pseudocodigo: si hay espacio sube el tope y guarda el dato. */
    public void push(String dato) throws DesbordamientoException {
        if (estaLlena()) {
            throw new DesbordamientoException("Desbordamiento: pila llena");
        }
        tope++;
        elementos[tope] = dato;
    }

    /** Pop del pseudocodigo: devuelve el dato de la cima y baja el tope. */
    public String pop() throws SubdesbordamientoException {
        if (estaVacia()) {
            throw new SubdesbordamientoException("Subdesbordamiento: pila vacia");
        }
        String dato = elementos[tope];
        tope--;
        return dato;
    }

    /** Dato de la cima sin sacarlo. Devuelve null si la pila esta vacia. */
    public String cima() {
        return estaVacia() ? null : elementos[tope];
    }

    /** Posicion del tope: -1 cuando la pila esta vacia. */
    public int tope() {
        return tope;
    }

    public int capacidad() {
        return elementos.length;
    }

    /** Cantidad de elementos guardados. */
    public int tamano() {
        return tope + 1;
    }

    /** Dato de una posicion, de 0 (fondo) a tope (cima). Null fuera de rango. */
    public String obtener(int indice) {
        if (indice < 0 || indice > tope) {
            return null;
        }
        return elementos[indice];
    }

    /**
     * Copia de los elementos del fondo a la cima. Sirve para pintar la pila y
     * para registrar la traza de una conversion polaca.
     */
    public List<String> contenido() {
        List<String> copia = new ArrayList<>();
        for (int i = 0; i <= tope; i++) {
            copia.add(elementos[i]);
        }
        return copia;
    }
}
