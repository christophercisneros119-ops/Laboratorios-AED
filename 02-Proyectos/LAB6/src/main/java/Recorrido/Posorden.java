package Recorrido;

import Arboles.Nodo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Posorden {

    private Posorden() {}

    public static List<Object> ejecutar(Nodo raiz) {
        List<Object> resultado = new ArrayList<>();
        recorrer(raiz, resultado);
        return Collections.unmodifiableList(resultado);
    }

    private static void recorrer(Nodo nodo, List<Object> resultado) {
        if (nodo == null) return;
        recorrer(nodo.getIzq(), resultado);
        recorrer(nodo.getDer(), resultado);
        resultado.add(nodo.getDato());
    }
}
