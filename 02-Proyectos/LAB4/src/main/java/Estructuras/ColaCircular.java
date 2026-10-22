package Estructuras;

/** Cola circular: el final envuelve a 0 y reutiliza las celdas libres. */
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

    // EncolarCircular del pseudocodigo.
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

    // DesencolarCircular del pseudocodigo.
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

    public int frente() {
        return frente;
    }

    public int fin() {
        return fin;
    }

    public int capacidad() {
        return elementos.length;
    }

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

    // Dice si la celda esta entre frente y final, envolviendo.
    public boolean esActiva(int indice) {
        if (frente == -1) {
            return false;
        }
        if (fin >= frente) {
            return indice >= frente && indice <= fin;
        }
        return indice >= frente || indice <= fin;
    }

    // Dato crudo: puede ser basura de una celda ya desencolada.
    public String obtener(int indice) {
        if (indice < 0 || indice >= elementos.length) {
            return null;
        }
        return elementos[indice];
    }
}
