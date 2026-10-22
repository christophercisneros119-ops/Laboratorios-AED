package Estructuras;

/**
 * Cola lineal sobre un arreglo fijo, igual que el pseudocodigo del curso:
 * frente y final parten en -1. El final solo avanza hacia la derecha, asi que
 * las celdas que el frente deja atras nunca se reutilizan; ese espacio
 * desperdiciado es justamente lo que la cola circular viene a resolver.
 */
public class Cola {

    private final String[] elementos;
    private int frente;
    private int fin;

    public Cola(int capacidad) {
        if (capacidad < 1) {
            throw new IllegalArgumentException("La capacidad debe ser al menos 1");
        }
        elementos = new String[capacidad];
        frente = -1;
        fin = -1;
    }

    /**
     * Encolar del pseudocodigo: inserta al final si queda espacio. Cuando el
     * dato cae en la primera posicion el frente arranca en 0.
     */
    public void encolar(String dato) throws DesbordamientoException {
        if (fin == elementos.length - 1) {
            throw new DesbordamientoException("Desbordamiento: cola llena");
        }
        fin++;
        elementos[fin] = dato;
        if (fin == 0 || fin == 1) {
            frente = 0;
        }
    }

    /** Desencolar del pseudocodigo: saca por el frente; al vaciarse reinicia. */
    public String desencolar() throws SubdesbordamientoException {
        if (frente == -1) {
            throw new SubdesbordamientoException("Subdesbordamiento: cola vacia");
        }
        String dato = elementos[frente];
        if (frente == fin) {
            frente = -1;
            fin = -1;
        } else {
            frente++;
        }
        return dato;
    }

    /** Posicion del frente: -1 cuando la cola esta vacia. */
    public int frente() {
        return frente;
    }

    /** Posicion del final: -1 cuando la cola esta vacia. */
    public int fin() {
        return fin;
    }

    public int capacidad() {
        return elementos.length;
    }

    /** Cantidad de elementos entre frente y final. */
    public int tamano() {
        return frente == -1 ? 0 : fin - frente + 1;
    }

    public boolean estaVacia() {
        return frente == -1;
    }

    public boolean estaLlena() {
        return fin == elementos.length - 1;
    }

    /**
     * Dato crudo de una celda del arreglo. Puede devolver un dato viejo de una
     * celda ya desencolada, o null si nunca se escribio: es el contenido real
     * de la memoria y sirve para pintar la basura que deja la cola lineal.
     */
    public String obtener(int indice) {
        if (indice < 0 || indice >= elementos.length) {
            return null;
        }
        return elementos[indice];
    }
}
