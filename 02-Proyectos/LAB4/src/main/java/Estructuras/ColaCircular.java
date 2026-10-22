package Estructuras;

/**
 * Cola circular sobre un arreglo fijo: cuando el final llega a la ultima celda
 * vuelve a 0 y asi reutiliza las posiciones que el frente va liberando.
 * Frente y final parten en -1, igual que la cola lineal.
 */
public class ColaCircular {

    private final String[] elementos;
    private int frente;
    private int fin;

    public ColaCircular(int capacidad) {
        if (capacidad < 1) {
            throw new IllegalArgumentException("La capacidad debe ser al menos 1");
        }
        elementos = new String[capacidad];
        frente = -1;
        fin = -1;
    }

    /**
     * EncolarCircular del pseudocodigo: llena si el final envuelto alcanzaria
     * al frente; si no, envuelve el final a 0 o lo avanza y guarda el dato.
     */
    public void encolar(String dato) throws DesbordamientoException {
        if ((fin == elementos.length - 1 && frente == 0) || (fin + 1 == frente)) {
            throw new DesbordamientoException("Desbordamiento: cola circular llena");
        }
        if (fin == elementos.length - 1) {
            fin = 0;
        } else {
            fin++;
        }
        elementos[fin] = dato;
        if (frente == -1) {
            frente = 0;
        }
    }

    /** DesencolarCircular del pseudocodigo: saca por el frente, envolviendo. */
    public String desencolar() throws SubdesbordamientoException {
        if (frente == -1) {
            throw new SubdesbordamientoException("Subdesbordamiento: cola circular vacia");
        }
        String dato = elementos[frente];
        if (frente == fin) {
            frente = -1;
            fin = -1;
        } else if (frente == elementos.length - 1) {
            frente = 0;
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

    /** Cantidad de elementos, contando los que estan despues del envoltorio. */
    public int tamano() {
        if (frente == -1) {
            return 0;
        }
        if (fin >= frente) {
            return fin - frente + 1;
        }
        return elementos.length - frente + fin + 1;
    }

    public boolean estaVacia() {
        return frente == -1;
    }

    public boolean estaLlena() {
        return (fin == elementos.length - 1 && frente == 0) || (fin + 1 == frente);
    }

    /** Dice si la celda guarda un elemento vigente, de frente a final envolviendo. */
    public boolean esActiva(int indice) {
        if (frente == -1) {
            return false;
        }
        if (fin >= frente) {
            return indice >= frente && indice <= fin;
        }
        return indice >= frente || indice <= fin;
    }

    /**
     * Dato crudo de una celda del arreglo: un dato viejo de una celda ya
     * desencolada, o null si nunca se escribio.
     */
    public String obtener(int indice) {
        if (indice < 0 || indice >= elementos.length) {
            return null;
        }
        return elementos[indice];
    }
}
