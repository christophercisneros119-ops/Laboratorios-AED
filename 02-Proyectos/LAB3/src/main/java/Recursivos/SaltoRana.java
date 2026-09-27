package Recursivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaltoRana {

    public static final char RANA_VERDE = 'V';
    public static final char RANA_CAFE = 'C';
    public static final char CASILLA_VACIA = '_';

    private static final int AVANCE = 1;
    private static final int SALTO = 2;

    private SaltoRana() {}

    public static char[] estadoInicial() {
        char[] estado = new char[Constantes.CASILLAS_TOTAL];
        Arrays.fill(estado, CASILLA_VACIA);
        Arrays.fill(estado, 0, Constantes.RANAS_VERDES, RANA_VERDE);
        int inicioCafe = Constantes.RANAS_VERDES + 1;
        Arrays.fill(estado, inicioCafe, Constantes.CASILLAS_TOTAL, RANA_CAFE);
        return estado;
    }

    public static List<PasoRana> resolver() {
        char[] estado = estadoInicial();
        List<PasoRana> pasos = new ArrayList<>();
        boolean solucion = buscar(estado, pasos);
        return solucion ? pasos : new ArrayList<>();
    }

    private static boolean buscar(char[] estado, List<PasoRana> pasos) {
        if (pasos.size() == Constantes.MOVIMIENTOS_RANA) {
            return true;
        }
        for (int posicion = 0; posicion < Constantes.CASILLAS_TOTAL; posicion++) {
            if (estado[posicion] == RANA_VERDE) {
                if (puedeAvanzar(estado, posicion + AVANCE)
                        && intentar(estado, pasos, posicion, posicion + AVANCE)) {
                    return true;
                }
                if (puedeSaltar(estado, posicion + AVANCE, posicion + SALTO, RANA_CAFE)
                        && intentar(estado, pasos, posicion, posicion + SALTO)) {
                    return true;
                }
            } else if (estado[posicion] == RANA_CAFE) {
                if (puedeAvanzar(estado, posicion - AVANCE)
                        && intentar(estado, pasos, posicion, posicion - AVANCE)) {
                    return true;
                }
                if (puedeSaltar(estado, posicion - AVANCE, posicion - SALTO, RANA_VERDE)
                        && intentar(estado, pasos, posicion, posicion - SALTO)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean intentar(char[] estado, List<PasoRana> pasos, int origen, int destino) {
        intercambiar(estado, origen, destino);
        pasos.add(new PasoRana(origen, destino));
        if (buscar(estado, pasos)) {
            return true;
        }
        pasos.remove(pasos.size() - 1);
        intercambiar(estado, origen, destino);
        return false;
    }

    private static boolean puedeAvanzar(char[] estado, int destino) {
        return estaEnRango(destino) && estado[destino] == CASILLA_VACIA;
    }

    private static boolean puedeSaltar(char[] estado, int medio, int destino, char rana) {
        return estaEnRango(medio) && estaEnRango(destino)
                && estado[medio] == rana
                && estado[destino] == CASILLA_VACIA;
    }

    private static boolean estaEnRango(int casilla) {
        return casilla >= 0 && casilla < Constantes.CASILLAS_TOTAL;
    }

    private static void intercambiar(char[] estado, int origen, int destino) {
        char auxiliar = estado[origen];
        estado[origen] = estado[destino];
        estado[destino] = auxiliar;
    }
}
