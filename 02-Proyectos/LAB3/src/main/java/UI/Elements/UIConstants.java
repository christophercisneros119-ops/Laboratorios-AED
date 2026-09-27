package UI.Elements;

import java.awt.Color;
import java.awt.Font;

/**
 * Valores provisionales de la interfaz. Cuando lleguen las imagenes y el
 * diseno final, lo que cambia de verdad son los colores y las fuentes: los
 * tamanos, margenes y la duracion del paso se conservan porque la geometria
 * de los lienzos depende de ellos.
 */
public final class UIConstants {

    private UIConstants() {}

    // ── Lienzo ──
    public static final Color FONDO = new Color(0x0E2230);
    public static final Color ESCENARIO = new Color(0x16303F);
    public static final Color BORDE = new Color(0x2E5165);
    public static final Color PIEZA = new Color(0x4FA3D9);
    public static final Color PIEZA_ALT = new Color(0x7C6B4F);
    public static final Color RESALTADO = new Color(0xF2C14E);
    public static final Color CASILLA = new Color(0xD8DEE3);
    public static final Color CASILLA_ALT = new Color(0x4B6B7C);

    // ── Piezas ──
    public static final Color RANA_VERDE = new Color(0x5BC46B);
    public static final Color RANA_CAFE = new Color(0x8B5A2B);

    // ── Texto ──
    public static final Color TEXTO = new Color(0xE6EEF2);
    public static final Color TEXTO_SUAVE = new Color(0x9BB0BC);

    // ── Fuentes ──
    // Silkscreen es pixel art: se pide en multiplos de su tamano nativo de 8 px.
    public static final Font FONT_TITULO = Fuentes.obtener(true, 16);
    public static final Font FONT_SUBTITULO = Fuentes.obtener(false, 8);
    public static final Font FONT_TEXTO = Fuentes.obtener(false, 8);
    public static final Font FONT_NUMERO = Fuentes.obtener(true, 8);

    // ── Medidas ──
    public static final int ANCHO_MINIMO = 720;
    public static final int ALTO_MINIMO = 520;
    public static final int DURACION_PASO = 500;
    /** Ritmo del fondo animado de QuickSort. Va aparte del paso: es decorativo. */
    public static final int CUADROS_POR_SEGUNDO = 12;
    public static final int ELEMENTOS_MAXIMOS = 12;
    public static final int MARGEN = 24;
}
