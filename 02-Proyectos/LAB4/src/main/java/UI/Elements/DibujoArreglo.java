package UI.Elements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

/**
 * Piezas de dibujo comunes a los modulos de arreglos lineales: celdas con su
 * texto, indices de posicion, punteros con flecha y la flecha de envoltura
 * de la cola circular. Los paneles calculan la geometria; aqui solo se pinta.
 */
public final class DibujoArreglo {

    private DibujoArreglo() {}

    /**
     * Celda cuadrada con texto centrado. Si el texto no cabe en 16 px baja a
     * 8 px y si aun no cabe se recorta contra el borde de la propia celda.
     */
    public static void celda(Graphics2D g2, int x, int y, int ancho, int alto,
                             String texto, Color relleno, Color borde, Color tinta) {
        g2.setColor(relleno);
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(borde);
        g2.drawRect(x, y, ancho - 1, alto - 1);
        if (texto == null || texto.isEmpty()) {
            return;
        }
        g2.setFont(UIConstants.FONT_TEXTO);
        FontMetrics medidas = g2.getFontMetrics();
        if (medidas.stringWidth(texto) > ancho - 8) {
            g2.setFont(UIConstants.FONT_CHICA);
            medidas = g2.getFontMetrics();
        }
        java.awt.Shape clipViejo = g2.getClip();
        g2.setClip(x + 2, y + 2, ancho - 4, alto - 4);
        g2.setColor(tinta);
        int textoX = x + Math.max((ancho - medidas.stringWidth(texto)) / 2, 0);
        int textoY = y + (alto - medidas.getHeight()) / 2 + medidas.getAscent();
        g2.drawString(texto, textoX, textoY);
        g2.setClip(clipViejo);
    }

    /** Numero de posicion centrado en una coordenada (baseY es la linea base). */
    public static void indice(Graphics2D g2, int centroX, int baseY, int numero) {
        g2.setFont(UIConstants.FONT_CHICA);
        FontMetrics medidas = g2.getFontMetrics();
        g2.setColor(UIConstants.TEXTO_SUAVE);
        String texto = String.valueOf(numero);
        g2.drawString(texto, centroX - medidas.stringWidth(texto) / 2, baseY);
    }

    /**
     * Puntero sobre una celda horizontal: etiqueta arriba y triangulo que baja
     * hasta el borde de la celda. El nivel separa el Frente del Final cuando
     * sus celdas quedan pegadas: nivel 0 pega la flecha, nivel 1 sube la
     * etiqueta y baja una linea hasta la flecha.
     */
    public static void punteroAbajo(Graphics2D g2, int centroX, int celdaArriba,
                                     int nivel, String etiqueta, Color color) {
        g2.setColor(color);
        if (nivel > 0) {
            int yTexto = celdaArriba - 36;
            g2.drawLine(centroX, yTexto + 4, centroX, celdaArriba - 10);
        }
        g2.fillPolygon(new int[]{centroX - 5, centroX + 5, centroX},
                new int[]{celdaArriba - 9, celdaArriba - 9, celdaArriba - 2}, 3);
        g2.setFont(UIConstants.FONT_CHICA);
        FontMetrics medidas = g2.getFontMetrics();
        int baseY = celdaArriba - 14 - nivel * 22;
        g2.drawString(etiqueta, centroX - medidas.stringWidth(etiqueta) / 2, baseY);
    }

    /** Puntero al costado derecho de una celda vertical, flecha hacia la izquierda. */
    public static void punteroIzquierda(Graphics2D g2, int celdaDerecha, int centroY,
                                         String etiqueta, Color color) {
        g2.setColor(color);
        g2.fillPolygon(new int[]{celdaDerecha + 2, celdaDerecha + 12, celdaDerecha + 12},
                new int[]{centroY, centroY - 5, centroY + 5}, 3);
        g2.setFont(UIConstants.FONT_CHICA);
        FontMetrics medidas = g2.getFontMetrics();
        g2.drawString(etiqueta, celdaDerecha + 18,
                centroY + medidas.getAscent() / 2 - 1);
    }

    /**
     * Flecha curva de la envoltura de la cola circular: nace bajo la ultima
     * celda, se comba hacia abajo y termina bajo la celda 0 con la punta
     * hacia arriba.
     */
    public static void envoltura(Graphics2D g2, int xInicio, int xFin, int y, Color color) {
        g2.setColor(color);
        QuadCurve2D curva = new QuadCurve2D.Float(xInicio, y,
                (xInicio + xFin) / 2, y + 48, xFin, y);
        g2.draw(curva);
        g2.fillPolygon(new int[]{xFin, xFin - 6, xFin + 6},
                new int[]{y - 1, y + 9, y + 9}, 3);
    }
}
