package Estructuras;

/** Cola lineal: el frente avanza y las celdas viejas no se reutilizan. */
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

    // Encolar del pseudocodigo; el frente arranca en 0 con el primer dato.
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

    // Desencolar del pseudocodigo: al vaciarse reinicia los punteros.
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
        return frente == -1 ? 0 : fin - frente + 1;
    }

    public boolean estaVacia() {
        return frente == -1;
    }

    // Dato crudo: puede ser basura de una celda ya desencolada.
    public String obtener(int indice) {
        if (indice < 0 || indice >= elementos.length) {
            return null;
        }
        return elementos[indice];
    }
}
