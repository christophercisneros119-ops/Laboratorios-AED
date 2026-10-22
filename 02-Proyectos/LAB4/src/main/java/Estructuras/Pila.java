package Estructuras;

import java.util.ArrayList;
import java.util.List;

/** Pila con arreglo fijo, igual que el pseudocodigo del curso (tope en -1). */
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

    // PilaLlena del pseudocodigo.
    public boolean estaLlena() {
        return tope == elementos.length - 1;
    }

    // PilaVacia del pseudocodigo.
    public boolean estaVacia() {
        return tope == -1;
    }

    // Push del pseudocodigo.
    public void push(String dato) throws DesbordamientoException {
        if (estaLlena()) {
            throw new DesbordamientoException("Desbordamiento: pila llena");
        }
        tope++;
        elementos[tope] = dato;
    }

    // Pop del pseudocodigo.
    public String pop() throws SubdesbordamientoException {
        if (estaVacia()) {
            throw new SubdesbordamientoException("Subdesbordamiento: pila vacia");
        }
        String dato = elementos[tope];
        tope--;
        return dato;
    }

    // Dato de la cima sin sacarlo.
    public String cima() {
        return estaVacia() ? null : elementos[tope];
    }

    public int tope() {
        return tope;
    }

    public int capacidad() {
        return elementos.length;
    }

    public int tamano() {
        return tope + 1;
    }

    // Dato por posicion, de 0 (fondo) a tope (cima); null fuera de rango.
    public String obtener(int indice) {
        if (indice < 0 || indice > tope) {
            return null;
        }
        return elementos[indice];
    }

    // Copia del fondo a la cima, para pintar o registrar la traza.
    public List<String> contenido() {
        List<String> copia = new ArrayList<>();
        for (int i = 0; i <= tope; i++) {
            copia.add(elementos[i]);
        }
        return copia;
    }
}
