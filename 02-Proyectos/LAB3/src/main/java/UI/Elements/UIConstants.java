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
    /** Texto de la portada: rojo coral, fuera de la paleta prohibida. */
    public static final Color CORAL = new Color(0xFF6B5E);
    /**
     * Subtitulo de la portada: negro casi puro. Va sobre las nubes claras del
     * fondo animado, donde cualquier tono claro se camufla; con este contraste
     * se lee en todos los cuadros del video.
     */
    public static final Color NEGRO_PURO = new Color(0x101418);
    /** Texto sobre el marco beige de Hanói: cafe oscuro para que tenga contraste. */
    public static final Color CAFE_OSCURO = new Color(0x4A3220);

    // ── Botones estilizados ──
    /** Comenzar de la portada: blanco hueso de reposo. */
    public static final Color BLANCO_HUESO = new Color(0xF4E9DA);
    /** Hover del Comenzar: lila suave. */
    public static final Color LILA_SUAVE = new Color(0xCBB6E4);
    /** Click del Comenzar: amarillo pastel. */
    public static final Color AMARILLO_PASTEL = new Color(0xFFF2B0);
    /** Atras: celeste de reposo, azulito al hover y azul apagado al clic. */
    public static final Color CELESTE = new Color(0xA9DDF2);
    public static final Color AZULITO = new Color(0x6699CC);
    public static final Color AZUL_PRESION = new Color(0x3F6BA6);
    /** Iniciar/Reiniciar/Generar: gris suave al hover, gris oscuro al clic. */
    public static final Color GRIS_SUAVE = new Color(0xC9CFD5);
    public static final Color GRIS_OSCURO = new Color(0x59626B);
    /** Clic de los botones de control: el gris oscuro aun mas apagado. */
    public static final Color GRIS_PRESION = new Color(0x3D444B);

    // ── Boton ──
    /**
     * Radio de esquina de todos los botones, en todos sus estados. En Swing el
     * argumento es la caja del arco, no el radio: con 20 la curva mide 10 px y
     * la esquina se nota redondeada en un boton de 44 de alto.
     */
    public static final int RADIO_BOTON = 20;
    /** Hueco que el reposo deja al borde del componente para crecer sin recortarse. */
    public static final int RESERVA_BOTON = 3;

    // ── Fondo por ejercicio ──
    public static final Color FONDO_HANOI = new Color(0xE9DFC9);
    public static final Color FONDO_RANA = new Color(0x4A90B8);
    public static final Color FONDO_REINAS = new Color(0x5C6674);
    /** Verde de QuickSort: casi negro, para que la foto sea la que ilumina. */
    public static final Color FONDO_QUICKSORT = new Color(0x0F160D);
    /** Borde del marco de la foto de cada escenario. */
    public static final Color MARCO_FOTO = new Color(0x262A2E);

    // ── Marco de foto congruente ──
    /** El marco de la foto mide la menor dimension del lienzo / 18. */
    public static final int MARCO_FOTO_DIVISION = 18;
    public static final int MARCO_FOTO_MIN = 16;
    public static final int MARCO_FOTO_MAX = 72;

    // ── Menu ──
    /** Banda del borde inferior derecho que se recorta para ocultar el @ del autor. */
    public static final double RECORTE_MENU = 0.07;

    // ── QuickSort ──
    /** Las espadas se oscurecen para estar a tono con la luz del fondo. */
    public static final double OSCURIDAD_ESPADA = 0.72;

    // ── Velocidades ──
    public static final String[] VELOCIDADES = {"1 · Lenta", "2 · Media", "3 · Rápida"};
    public static final int VELOCIDAD_MEDIA = 1;

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
