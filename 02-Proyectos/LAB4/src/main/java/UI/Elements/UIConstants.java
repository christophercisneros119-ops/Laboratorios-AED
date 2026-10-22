package UI.Elements;

import java.awt.Color;
import java.awt.Font;

/**
 * Paleta, fuentes y medidas comunes de la interfaz. Silkscreen es pixel art:
 * se pide en multiplos de su tamano nativo de 8 px (lo cuida la clase Fuentes).
 */
public final class UIConstants {

    private UIConstants() {}

    // Lienzo y marco
    public static final Color FONDO = new Color(0x0E2230);
    public static final Color ESCENARIO = new Color(0x16303F);
    public static final Color BORDE = new Color(0x2E5165);
    /** Amarillo de resaltados sobre el escenario. */
    public static final Color RESALTADO = new Color(0xF2C14E);

    // Texto
    public static final Color TEXTO = new Color(0xE6EEF2);
    public static final Color TEXTO_SUAVE = new Color(0x9BB0BC);
    /** Avisos de validacion, se muestran en linea dentro del panel. */
    public static final Color ERROR = new Color(0xF28B82);
    /** Acento de marca, para el encabezado de la portada. */
    public static final Color ACENTO = new Color(0x6FD3E8);
    /** Texto de la portada sobre las nubes claras del fondo. */
    public static final Color NEGRO_PURO = new Color(0x101418);

    // Acento de cada modulo, tambien es el hover de su boton en el menu
    public static final Color ACENTO_PILA = new Color(0x6FD3E8);
    public static final Color ACENTO_COLA = new Color(0xF2C14E);
    public static final Color ACENTO_CIRCULAR = new Color(0x5BC46B);
    public static final Color ACENTO_POLACA = new Color(0xFF6B5E);

    // Botones estilizados
    public static final Color BLANCO_HUESO = new Color(0xF4E9DA);
    public static final Color LILA_SUAVE = new Color(0xCBB6E4);
    public static final Color AMARILLO_PASTEL = new Color(0xFFF2B0);
    public static final Color CELESTE = new Color(0xA9DDF2);
    public static final Color AZULITO = new Color(0x6699CC);
    public static final Color AZUL_PRESION = new Color(0x3F6BA6);
    public static final Color GRIS_SUAVE = new Color(0xC9CFD5);
    public static final Color GRIS_OSCURO = new Color(0x59626B);
    public static final Color GRIS_PRESION = new Color(0x3D444B);

    /**
     * Radio de esquina de todos los botones y campos. En Swing el argumento es
     * la caja del arco, no el radio: con 20 la curva mide 10 px.
     */
    public static final int RADIO_BOTON = 20;
    /** Hueco que el reposo deja al borde del boton para crecer sin recortarse. */
    public static final int RESERVA_BOTON = 3;

    // Celdas de los arreglos lineales
    /** Celda con dato valido. */
    public static final Color CASILLA = new Color(0x1D3A4E);
    /** Celda que nunca se ha usado. */
    public static final Color CASILLA_VACIA = new Color(0x112431);
    /** Celda liberada por un desencolar, guarda un dato que ya no es de nadie. */
    public static final Color CASILLA_BASURA = new Color(0x152C3B);
    /** Texto tenue de las celdas liberadas. */
    public static final Color BASURA_TEXTO = new Color(0x51626E);

    // Menu
    /** Banda del borde inferior derecho que se recorta para ocultar el @ del autor. */
    public static final double RECORTE_MENU = 0.07;

    // Fuentes
    public static final Font FONT_TITULO = Fuentes.obtener(true, 32);
    public static final Font FONT_SUBTITULO = Fuentes.obtener(false, 16);
    public static final Font FONT_TEXTO = Fuentes.obtener(false, 16);
    /** Nombre del UNI en la portada: el texto mas grande, en negrita. */
    public static final Font FONT_UNI = Fuentes.obtener(true, 40);
    /** Numeros de indice y punteros sobre las celdas. */
    public static final Font FONT_INDICE = Fuentes.obtener(false, 16);
    /** Texto auxiliar chico: encabezados de traza y notas que no caben en 16. */
    public static final Font FONT_CHICA = Fuentes.obtener(false, 8);

    // Medidas
    public static final int MARGEN = 32;
    public static final int ANCHO_MINIMO = 720;
    public static final int ALTO_MINIMO = 520;
    /** Ritmo de los fondos animados heredados de LAB3. */
    public static final int CUADROS_POR_SEGUNDO = 15;
    /** Tope de carga de cuadros, guarda contra un prefijo mal escrito. */
    public static final int MAXIMO_CUADROS = 64;
}
