package UI.Elements;

import java.awt.Color;
import java.awt.Font;

public final class UIConstants {

    private UIConstants() {}

    public static final Color FONDO = new Color(0x0E2230);
    public static final Color ESCENARIO = new Color(0x16303F);
    public static final Color BORDE = new Color(0x2E5165);
    public static final Color RESALTADO = new Color(0xF2C14E);

    public static final Color TEXTO = new Color(0xE6EEF2);
    public static final Color TEXTO_SUAVE = new Color(0x9BB0BC);
    public static final Color ERROR = new Color(0xF28B82);
    public static final Color ACENTO = new Color(0x6FD3E8);
    public static final Color NEGRO_PURO = new Color(0x101418);

    public static final Color BLANCO_HUESO = new Color(0xF4E9DA);
    public static final Color LILA_SUAVE = new Color(0xCBB6E4);
    public static final Color AMARILLO_PASTEL = new Color(0xFFF2B0);
    public static final Color CELESTE = new Color(0xA9DDF2);
    public static final Color AZULITO = new Color(0x6699CC);
    public static final Color AZUL_PRESION = new Color(0x3F6BA6);
    public static final Color GRIS_SUAVE = new Color(0xC9CFD5);
    public static final Color GRIS_OSCURO = new Color(0x59626B);
    public static final Color GRIS_PRESION = new Color(0x3D444B);

    public static final int RADIO_BOTON = 20;
    public static final int RESERVA_BOTON = 3;

    public static final Font FONT_TITULO = Fuentes.obtener(true, 32);
    public static final Font FONT_SUBTITULO = Fuentes.obtener(false, 16);
    public static final Font FONT_TEXTO = Fuentes.obtener(false, 16);
    public static final Font FONT_UNI = Fuentes.obtener(true, 40);
    public static final Font FONT_INDICE = Fuentes.obtener(false, 16);
    public static final Font FONT_CHICA = Fuentes.obtener(false, 8);

    public static final int MARGEN = 32;
    public static final int ANCHO_MINIMO = 720;
    public static final int ALTO_MINIMO = 520;
    public static final int CUADROS_POR_SEGUNDO = 15;
    public static final int MAXIMO_CUADROS = 64;
}
