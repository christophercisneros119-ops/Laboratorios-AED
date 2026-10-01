package UI.Elements;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JButton;

/**
 * Boton de la interfaz con hover y click de color.
 *
 * <p>El boton es una pieza con su propia paleta: {@code idle} en reposo,
 * {@code hover} cuando el mouse pasa encima y {@code press} mientras se
 * mantiene el clic. Al hacer hover crece, pero nunca mas alla de sus propios
 * limites: en reposo se pinta con un hueco de {@link UIConstants#RESERVA_BOTON}
 * px y al pasar el mouse ocupa el componente completo. Antes el crecimiento era
 * de 1.10 y se salia del boton; Swing recorta a los bounds y las esquinas
 * redondeadas quedaban cortadas, rectas. Puede dibujar un icono de pixel art
 * (PLAY, FLECHA) o un texto.
 *
 * <p>El color del dibujo sale del fondo con contraste automatico: claro sobre
 * fondos oscuros y oscuro sobre fondos claros, asi mismo estilo sirve sobre el
 * marco beige de Hanói, sobre el celeste de la rana o sobre el verde de
 * QuickSort. El radio de esquina es el mismo en todos los estados y en todos
 * los botones de la aplicacion.
 */
public class BotonEstilizado extends JButton {

    public enum Glifo { NINGUNO, PLAY, FLECHA }

    /** Hueco lateral que deja el texto para que el boton respire. */
    private static final int AIRE_TEXTO = 40;
    /** Ancho minimo de un boton de texto, para que ninguno quede apretado. */
    private static final int ANCHO_MINIMO_TEXTO = 140;

    private final Glifo glifo;
    private final Color colorIdle;
    private final Color colorHover;
    private final Color colorPress;

    private BotonEstilizado(Glifo glifo, String texto, Dimension tamano,
                            Color colorIdle, Color colorHover, Color colorPress) {
        this.glifo = glifo;
        this.colorIdle = colorIdle;
        this.colorHover = colorHover;
        this.colorPress = colorPress;
        if (texto != null) {
            setText(texto);
            setFont(UIConstants.FONT_TEXTO);
        }
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(tamano);
        setMinimumSize(tamano);
        setMaximumSize(tamano);
    }

    /** Cuadrado con icono de pixel art, paleta {reposo, hover, press}. */
    public static BotonEstilizado icono(Glifo glifo, int lado,
            Color colorIdle, Color colorHover, Color colorPress) {
        Dimension tamano = new Dimension(lado, lado);
        return new BotonEstilizado(glifo, null, tamano, colorIdle, colorHover, colorPress);
    }

    /** Boton de texto con la misma paleta y el mismo crecimiento al hover. */
    public static BotonEstilizado texto(String etiqueta, int ancho,
            Color colorIdle, Color colorHover, Color colorPress) {
        Dimension tamano = new Dimension(ancho, 44);
        return new BotonEstilizado(Glifo.NINGUNO, etiqueta, tamano,
                colorIdle, colorHover, colorPress);
    }

    /**
     * Boton de texto con el ancho justo para su etiqueta: el texto queda
     * centrado con {@link #AIRE_TEXTO} px de aire a cada lado, ni mas largo ni
     * apretado. Es el estilo de "Comenzar" y de "Regresar".
     */
    public static BotonEstilizado textoAjustado(String etiqueta,
            Color colorIdle, Color colorHover, Color colorPress) {
        BotonEstilizado boton = texto(etiqueta, ANCHO_MINIMO_TEXTO,
                colorIdle, colorHover, colorPress);
        int medida = boton.getFontMetrics(boton.getFont()).stringWidth(etiqueta);
        int ancho = Math.max(ANCHO_MINIMO_TEXTO, medida + 2 * AIRE_TEXTO);
        Dimension tamano = new Dimension(ancho, 44);
        boton.setPreferredSize(tamano);
        boton.setMinimumSize(tamano);
        boton.setMaximumSize(tamano);
        return boton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        boolean presionado = getModel().isPressed();
        boolean sobre = getModel().isRollover();

        Color fondo = presionado ? colorPress : (sobre ? colorHover : colorIdle);

        int bw = getWidth();
        int bh = getHeight();
        // Reposo con hueco, hover y clic a hueso: el boton "crece" al pasar el
        // mouse pero nunca pinta fuera de sus limites, asi las esquinas
        // redondeadas no se recortan y el boton no se vuelve rectangular.
        int reserva = (sobre || presionado) ? 0 : UIConstants.RESERVA_BOTON;
        int x = reserva;
        int y = reserva;
        int ancho = bw - 2 * reserva;
        int alto = bh - 2 * reserva;
        int radio = UIConstants.RADIO_BOTON;

        g2.setColor(fondo);
        g2.fillRoundRect(x, y, ancho, alto, radio, radio);
        g2.setColor(oscurecer(fondo));
        g2.drawRoundRect(x, y, ancho - 1, alto - 1, radio, radio);

        Color tinta = contraste(fondo);
        if (glifo == Glifo.NINGUNO) {
            g2.setFont(getFont());
            FontMetrics medidas = g2.getFontMetrics();
            g2.setColor(tinta);
            int textoX = x + (ancho - medidas.stringWidth(getText())) / 2;
            int textoY = y + (alto - medidas.getHeight()) / 2 + medidas.getAscent();
            g2.drawString(getText(), textoX, textoY);
        } else {
            int lado = Math.min(ancho, alto);
            dibujarGlifo(g2, glifo, x + ancho / 2, y + alto / 2, lado, tinta);
        }
        g2.dispose();
    }

    /** Dibuja el icono de pixel art centrado en el cuadrado del boton. */
    private static void dibujarGlifo(Graphics2D g2, Glifo glifo, int centroX,
                                     int centroY, int lado, Color color) {
        int mitad = lado / 2;
        int punta = (int) Math.round(lado * 0.30);
        g2.setColor(color);
        Polygon poligono;
        if (glifo == Glifo.PLAY) {
            int baseX = centroX - (int) Math.round(lado * 0.16);
            poligono = new Polygon(
                    new int[]{baseX, baseX, centroX + punta},
                    new int[]{centroY - mitad, centroY + mitad, centroY}, 3);
        } else {
            int baseX = centroX + (int) Math.round(lado * 0.16);
            poligono = new Polygon(
                    new int[]{baseX, baseX, centroX - punta},
                    new int[]{centroY - mitad, centroY + mitad, centroY}, 3);
        }
        g2.fillPolygon(poligono);
    }

    /** Tono mas oscuro del fondo, para el borde del boton. */
    private static Color oscurecer(Color color) {
        int factor = 145;
        return new Color(color.getRed() * factor / 255,
                color.getGreen() * factor / 255,
                color.getBlue() * factor / 255);
    }

    /** Tinta legible: oscura sobre fondos claros, clara sobre oscuros. */
    private static Color contraste(Color fondo) {
        double brillo = 0.299 * fondo.getRed()
                + 0.587 * fondo.getGreen()
                + 0.114 * fondo.getBlue();
        return brillo > 140 ? new Color(0x1C2126) : new Color(0xF2F6F8);
    }
}