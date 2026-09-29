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
    /** Avisos de validacion, se muestran en linea dentro del panel. */
    public static final Color ERROR = new Color(0xF28B82);
    /** Acento de marca: el cian del UNI, para el encabezado y los separadores. */
    public static final Color ACENTO = new Color(0x6FD3E8);

    // ── Fondo por ejercicio ──
    public static final Color FONDO_HANOI = new Color(0x6B4A2E);
    public static final Color FONDO_RANA = new Color(0x4A90B8);
    public static final Color FONDO_REINAS = new Color(0x5C6674);
    public static final Color FONDO_QUICKSORT = new Color(0x8A6C1E);

    // ── Fuentes ──
    // Silkscreen es pixel art: se pide en multiplos de su tamano nativo de 8 px.
    public static final Font FONT_TITULO = Fuentes.obtener(true, 32);
    public static final Font FONT_SUBTITULO = Fuentes.obtener(false, 16);
    public static final Font FONT_TEXTO = Fuentes.obtener(false, 16);
    /** Nombre del UNI en la portada: el texto mas grande, en negrita. */
    public static final Font FONT_UNI = Fuentes.obtener(true, 40);
    /** Numeros de la escena: el tamano lo decide el lienzo, no se fija aqui. */
    public static final int NUMERO_MINIMO = 8;
    public static final int NUMERO_MAXIMO = 32;

    // ── Medidas ──
    public static final int ANCHO_MINIMO = 720;
    public static final int ALTO_MINIMO = 520;
    public static final int DURACION_PASO = 500;
    /** Salto de la rana: cada movimiento dura 800 ms en 4 fases de 200 ms. */
    public static final int DURACION_PASO_RANA = 800;
    /** Ritmo del fondo animado de QuickSort. Va aparte del paso: es decorativo. */
    public static final int CUADROS_POR_SEGUNDO = 12;
    public static final int MARGEN = 32;
}
