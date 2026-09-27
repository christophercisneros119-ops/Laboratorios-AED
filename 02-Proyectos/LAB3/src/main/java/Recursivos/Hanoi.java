package Recursivos;

import java.util.ArrayList;
import java.util.List;

public class Hanoi {

    private Hanoi() {}

    public static List<PasoHanoi> resolver(int numeroDiscos) {
        List<PasoHanoi> pasos = new ArrayList<>();
        mover(numeroDiscos, Constantes.TORRE_ORIGEN,
                Constantes.TORRE_AUXILIAR, Constantes.TORRE_DESTINO, pasos);
        return pasos;
    }

    private static void mover(int discos, int origen, int auxiliar, int destino,
                              List<PasoHanoi> pasos) {
        if (discos <= 0) {
            return;
        }
        mover(discos - 1, origen, destino, auxiliar, pasos);
        pasos.add(new PasoHanoi(discos, origen, destino));
        mover(discos - 1, auxiliar, origen, destino, pasos);
    }
}
