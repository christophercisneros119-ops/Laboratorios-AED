package UI.Panels;

import Recursivos.Constantes;
import Recursivos.PasoReina;
import Recursivos.Reinas;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

public class P_Reinas extends P_EjercicioBase {

    private static final int SIN_REINA = -1;

    private List<PasoReina> pasos = List.of();
    private int[] columnaPorFila = tableroVacio();

    /** Sin reina puesta se usa -1, no 0, que es una columna valida. */
    private static int[] tableroVacio() {
        int[] columnas = new int[Constantes.LADO_TABLERO];
        Arrays.fill(columnas, SIN_REINA);
        return columnas;
    }

    public P_Reinas() {
        super(Constantes.LADO_TABLERO + " Reinas",
                "Una reina por fila y por columna, sin que se ataquen en diagonal.");
        preparar();
        refrescar();
    }

    @Override
    protected boolean preparar() {
        pasos = Reinas.resolver();
        columnaPorFila = tableroVacio();
        return true;
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
        PasoReina paso = pasos.get(indice);
        columnaPorFila[paso.fila()] = paso.columna();
    }

    /** Tamano base de una casilla. El sprite mide 18 para dejar 1px de margen. */
    private static final int TAM_CASILLA = 16;

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        int lado = Math.max(Math.min(ancho, alto) - 2 * UIConstants.MARGEN, 10);
        // La casilla se redondea hacia abajo a un multiplo de TAM_CASILLA para que
        // el sprite siempre escale por un factor entero y no se deforme.
        int casilla = (lado / Constantes.LADO_TABLERO / TAM_CASILLA) * TAM_CASILLA;
        if (casilla < TAM_CASILLA) {
            casilla = TAM_CASILLA;
        }
        int tablero = casilla * Constantes.LADO_TABLERO;
        int origenX = (ancho - tablero) / 2;
        int origenY = (alto - tablero) / 2;

        // Escenario: el tablero.
        for (int fila = 0; fila < Constantes.LADO_TABLERO; fila++) {
            for (int columna = 0; columna < Constantes.LADO_TABLERO; columna++) {
                g2.setColor((fila + columna) % 2 == 0
                        ? UIConstants.CASILLA : UIConstants.CASILLA_ALT);
                g2.fillRect(origenX + columna * casilla, origenY + fila * casilla,
                        casilla, casilla);
            }
        }
        g2.setColor(UIConstants.BORDE);
        g2.drawRect(origenX, origenY, tablero, tablero);

        // Piezas: la reina de cada fila ya colocada.
        int radio = Math.max(casilla / 3, 6);
        for (int fila = 0; fila < Constantes.LADO_TABLERO; fila++) {
            int columna = columnaPorFila[fila];
            if (columna == SIN_REINA) {
                continue;
            }
            int centroX = origenX + columna * casilla + casilla / 2;
            int centroY = origenY + fila * casilla + casilla / 2;
            if (!Imagenes.dibujarCentrado(g2, "queen", centroX, centroY, casilla)) {
                g2.setColor(UIConstants.PIEZA_ALT);
                g2.fillOval(centroX - radio / 2, centroY - radio / 2, radio, radio);
            }
        }
    }
}
