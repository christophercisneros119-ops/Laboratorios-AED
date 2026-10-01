package UI.Panels;

import Recursivos.Constantes;
import Recursivos.PasoReina;
import Recursivos.Reinas;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

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
                "Una reina por fila y por columna, sin que se ataquen en diagonal.",
                UIConstants.FONDO_REINAS);
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Velocidad:"));
        fila.add(velocidad());
        addConfiguracion(fila);
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

    /**
     * Geometria de {@code tablero.png} (130x130), medida sobre el archivo.
     *
     * <p>El area jugable no es el interior completo: son 8x8 celdas de 13px con 1px
     * de separacion, y arranca en (9,10). El margen de 9px de cada lado es marco
     * decorativo, no celdas.
     */
    private static final int TABLERO_ORIGEN_X = 9;
    private static final int TABLERO_ORIGEN_Y = 10;
    /** 13 de celda + 1 de separacion. */
    private static final int TABLERO_PASO = 14;
    private static final int TABLERO_LADO = 130;
    /** Mitad de la celda de 13px: su centro cae en medio pixel. */
    private static final double MITAD_CELDA = 6.5;

    /**
     * Factor con que se escala el sprite de la pieza dentro de su celda.
     *
     * <p>El sprite mide 16px de caja pero solo 10px son arte visible: el resto es
     * transparente. Por eso la proporcion se mide contra el ancho real dibujado y
     * no contra los 16px de la caja, o la reina sale artificialmente pequena.
     */
    private static final double PROPORCION_PIEZA = 0.90;

@Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        int[] ventana = Imagenes.ventanaFoto(ancho, alto);
        int vx = ventana[0];
        int vy = ventana[1];
        int aw = ventana[2];
        int ah = ventana[3];
        Imagenes.dibujarFondoVentana(g2, ventana, "fondo_reinas");

        int disponible = Math.max(Math.min(aw, ah) - 2 * UIConstants.MARGEN, 10);
        // La escala sale del lado del PNG completo, no del area jugable: el factor
        // tiene que ser entero para que el pixel art no se difumine.
        int escala = Math.max(disponible / TABLERO_LADO, 1);
        int lado = TABLERO_LADO * escala;
        int origenX = vx + (aw - lado) / 2;
        int origenY = vy + (ah - lado) / 2;

        // Escenario: el tablero, entero y una sola vez.
        if (!Imagenes.dibujarEscalado(g2, "tablero", origenX, origenY, escala)) {
            int celda = Math.max((escala * 112) / Constantes.LADO_TABLERO, 1);
            for (int fila = 0; fila < Constantes.LADO_TABLERO; fila++) {
                for (int columna = 0; columna < Constantes.LADO_TABLERO; columna++) {
                    g2.setColor((fila + columna) % 2 == 0
                            ? UIConstants.CASILLA : UIConstants.CASILLA_ALT);
                    g2.fillRect(origenX + columna * celda, origenY + fila * celda,
                            celda, celda);
                }
            }
        }

        // Piezas: la reina de cada fila ya colocada. El centro se deja en float
        // porque el de una celda impar cae en medio pixel; el redondeo al entero
        // lo hace Imagenes al pintar el sprite.
        int factorPieza = factorPieza(escala);
        int radio = Math.max(8 * factorPieza / 2, 6);
        for (int fila = 0; fila < Constantes.LADO_TABLERO; fila++) {
            int columna = columnaPorFila[fila];
            if (columna == SIN_REINA) {
                continue;
            }
            double centroX = origenX
                    + (TABLERO_ORIGEN_X + TABLERO_PASO * columna + MITAD_CELDA) * escala;
            double centroY = origenY
                    + (TABLERO_ORIGEN_Y + TABLERO_PASO * fila + MITAD_CELDA) * escala;
            if (!Imagenes.dibujarPieza(g2, "queen", centroX, centroY, factorPieza)) {
                g2.setColor(UIConstants.PIEZA_ALT);
                g2.fillOval((int) Math.round(centroX) - radio / 2,
                        (int) Math.round(centroY) - radio / 2, radio, radio);
            }
        }
    }

    /** Escala entera de la pieza dentro de la celda, nunca menor que 1. */
    private static int factorPieza(int escala) {
        return Math.max(1, (int) Math.round(PROPORCION_PIEZA * escala));
    }
}
