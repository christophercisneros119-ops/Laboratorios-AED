package UI.Elements;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;

/**
 * Carga Silkscreen desde {@code src/main/resources/fuentes}.
 *
 * <p>Silkscreen es una fuente de pixel art: solo se ve nitida en su tamano nativo
 * de 8 px y en sus multiplicos enteros. Pedirle 13 o 20 la deja borrosa, asi que
 * {@link #dePixel(int)} fuerza los multiplicos de 8.
 *
 * <p>Si el archivo no esta, se cae a la fuente del sistema para que la interfaz
 * siga siendo legible.
 */
public final class Fuentes {

    private static final String CARPETA = "/fuentes/";
    private static final String REGULAR = "Silkscreen-Regular";
    private static final String BOLD = "Silkscreen-Bold";

    /** Tamano nativo de Silkscreen, en pixeles. */
    private static final int TAM_NATIVO = 8;

    private static final Map<String, Font> CACHE = new HashMap<>();

    private Fuentes() {}

    /** Devuelve la fuente pedida, o la del sistema si el recurso no se pudo leer. */
    public static Font obtener(boolean negrita, int tamano) {
        int ajustado = dePixel(tamano);
        String clave = (negrita ? BOLD : REGULAR) + "-" + ajustado;
        if (CACHE.containsKey(clave)) {
            return CACHE.get(clave);
        }
        Font fuente = cargar(negrita ? BOLD : REGULAR, ajustado);
        if (fuente == null) {
            fuente = new Font(UIManager.getFont("Label.font").getFontName(),
                    negrita ? Font.BOLD : Font.PLAIN, ajustado);
        }
        CACHE.put(clave, fuente);
        return fuente;
    }

    /**
     * Ajusta el tamano al multiplo entero de 8 mas cercano por debajo. Asi cada
     * pixel de la fuente cae sobre un pixel de pantalla y no se difumina.
     */
    public static int dePixel(int tamano) {
        int multiplo = tamano / TAM_NATIVO;
        if (multiplo < 1) {
            multiplo = 1;
        }
        return multiplo * TAM_NATIVO;
    }

    private static Font cargar(String nombre, int tamano) {
        try (InputStream in = Fuentes.class.getResourceAsStream(CARPETA + nombre + ".ttf")) {
            if (in == null) {
                return null;
            }
            return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.PLAIN, tamano);
        } catch (FontFormatException | java.io.IOException excepcion) {
            return null;
        }
    }
}
