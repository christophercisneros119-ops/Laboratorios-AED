package UI.Elements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.QuadCurve2D;

/** Dibujo de celdas, indices, punteros y flecha de envoltura para los arreglos. */
public final class DibujoArreglo {

    private DibujoArreglo() {}

    public static void celda(Graphics2D g2, int x, int y, int ancho, int alto,
                             String texto, Color relleno, Color borde, Color tinta) {
        g2.setColor(relleno);
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(borde);
        g2.drawRect(x, y, ancho - 1, alto - 1);
        if (texto == null || texto.isEmpty()) {
            return;
        }
        // Si no cabe en 16 px baja a 8 y de ahi se recorta al borde de la celda.
        g2.setFont(UIConstants.FONT_TEXTO);
        FontMetrics medidas = g2.getFontMetrics();
        if (medidas.stringWidth(texto) > ancho - 8) {
            g2.setFont(UIConstants.FONT_CHICA);
            medidas = g2.getFontMetrics();
        }
        Shape clipViejo = g2.getClip();
        g2.setClip(x + 2, y + 2, ancho - 4, alto - 4);
        g2.setColor(tinta);
        int textoX = x + Math.max((ancho - medidas.stringWidth(texto)) / 2, 0);
        int textoY = y + (alto - medidas.getHeight()) / 2 + medidas.getAscent();
        g2.drawString(texto, textoX, textoY);
        g2.setClip(clipViejo);
    }

    public static void indice(Graphics2D g2, int centroX, int baseY, int numero) {
        g2.setFont(UIConstants.FONT_CHICA);
        FontMetrics medidas = g2.getFontMetrics();
        g2.setColor(UIConstants.TEXTO_SUAVE);
        String texto = String.valueOf(numero);
        g2.drawString(texto, centroX - medidas.stringWidth(texto) / 2, baseY);
    }

    // nivel separa Frente y Final cuando sus celdas quedan pegadas.
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

    // Curva de la ultima celda a la celda 0 con la punta hacia arriba.
    public static void envoltura(Graphics2D g2, int xInicio, int xFin, int y, Color color) {
        g2.setColor(color);
        QuadCurve2D curva = new QuadCurve2D.Float(xInicio, y,
                (xInicio + xFin) / 2, y + 48, xFin, y);
        g2.draw(curva);
        g2.fillPolygon(new int[]{xFin, xFin - 6, xFin + 6},
                new int[]{y - 1, y + 9, y + 9}, 3);
    }
}
