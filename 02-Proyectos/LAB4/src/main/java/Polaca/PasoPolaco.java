package Polaca;

import java.util.List;

/**
 * Un paso de la conversion: el simbolo leido, como quedo la pila al terminar
 * el paso (del fondo a la cima), la salida acumulada y una nota corta.
 */
public record PasoPolaco(int numero, String simbolo, List<String> pila, String salida,
                         String nota) {

    public PasoPolaco {
        pila = List.copyOf(pila);
    }
}
