package UI.Elements;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;

/** Carga Silkscreen desde resources. Solo se ve nitida en multiplos de 8 px. */
public final class Fuentes {

    private static final String CARPETA = "/fuentes/";
    private static final String REGULAR = "Silkscreen-Regular";
    private static final String BOLD = "Silkscreen-Bold";

    private static final int TAM_NATIVO = 8;

    private static final Map<String, Font> CACHE = new HashMap<>();

    private Fuentes() {}

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

    // Baja al multiplo de 8 mas cercano para que la fuente no se difumine.
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
