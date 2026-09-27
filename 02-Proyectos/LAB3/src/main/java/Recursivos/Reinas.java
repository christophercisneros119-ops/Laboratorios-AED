package Recursivos;

import java.util.ArrayList;
import java.util.List;

public class Reinas {

    private static final int TOTAL_REINAS = Constantes.LADO_TABLERO;
    private static final int DIAGONALES_TOTAL = 2 * Constantes.LADO_TABLERO - 1;

    private Reinas() {}

    public static List<PasoReina> resolver() {
        boolean[] columnas = new boolean[TOTAL_REINAS];
        boolean[] diagonalesPrincipal = new boolean[DIAGONALES_TOTAL];
        boolean[] diagonalesSecundaria = new boolean[DIAGONALES_TOTAL];
        List<PasoReina> solucion = colocar(0, columnas, diagonalesPrincipal,
                diagonalesSecundaria, new ArrayList<>());
        return solucion == null ? new ArrayList<>() : solucion;
    }

    private static List<PasoReina> colocar(int fila, boolean[] columnas,
                                           boolean[] diagonalesPrincipal,
                                           boolean[] diagonalesSecundaria,
                                           List<PasoReina> pasos) {
        if (fila == TOTAL_REINAS) {
            return pasos;
        }
        for (int columna = 0; columna < TOTAL_REINAS; columna++) {
            if (!puedeColocar(fila, columna, columnas, diagonalesPrincipal, diagonalesSecundaria)) {
                continue;
            }
            marcar(fila, columna, columnas, diagonalesPrincipal, diagonalesSecundaria);
            pasos.add(new PasoReina(fila, columna));
            List<PasoReina> solucion = colocar(fila + 1, columnas, diagonalesPrincipal,
                    diagonalesSecundaria, pasos);
            if (solucion != null) {
                return solucion;
            }
            pasos.remove(pasos.size() - 1);
            desmarcar(fila, columna, columnas, diagonalesPrincipal, diagonalesSecundaria);
        }
        return null;
    }

    private static boolean puedeColocar(int fila, int columna, boolean[] columnas,
                                        boolean[] diagonalesPrincipal,
                                        boolean[] diagonalesSecundaria) {
        return !columnas[columna]
                && !diagonalesPrincipal[indiceDiagonalPrincipal(fila, columna)]
                && !diagonalesSecundaria[indiceDiagonalSecundaria(fila, columna)];
    }

    private static void marcar(int fila, int columna, boolean[] columnas,
                               boolean[] diagonalesPrincipal, boolean[] diagonalesSecundaria) {
        columnas[columna] = true;
        diagonalesPrincipal[indiceDiagonalPrincipal(fila, columna)] = true;
        diagonalesSecundaria[indiceDiagonalSecundaria(fila, columna)] = true;
    }

    private static void desmarcar(int fila, int columna, boolean[] columnas,
                                  boolean[] diagonalesPrincipal, boolean[] diagonalesSecundaria) {
        columnas[columna] = false;
        diagonalesPrincipal[indiceDiagonalPrincipal(fila, columna)] = false;
        diagonalesSecundaria[indiceDiagonalSecundaria(fila, columna)] = false;
    }

    // 'fila - columna' se sale del rango al recorrer el tablero, por eso se suma el lado.
    private static int indiceDiagonalPrincipal(int fila, int columna) {
        return fila - columna + Constantes.LADO_TABLERO - 1;
    }

    private static int indiceDiagonalSecundaria(int fila, int columna) {
        return fila + columna;
    }
}
