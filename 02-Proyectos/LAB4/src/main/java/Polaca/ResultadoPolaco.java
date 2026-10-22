package Polaca;

import java.util.List;

/** Resultado completo de una conversion, con su traza paso a paso. */
public record ResultadoPolaco(String infija, String resultado, List<PasoPolaco> pasos) {

    public ResultadoPolaco {
        pasos = List.copyOf(pasos);
    }
}
