package UI.Elements;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

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
        return CACHE.computeIfAbsent(clave, k -> {
            Font fuente = cargar(negrita ? BOLD : REGULAR, ajustado);
            return fuente != null ? fuente : new Font(UIManager.getFont("Label.font").getFontName(),
                    negrita ? Font.BOLD : Font.PLAIN, ajustado);
        });
    }

    public static int dePixel(int tamano) {
        return Math.max(1, tamano / TAM_NATIVO) * TAM_NATIVO;
    }

    private static Font cargar(String nombre, int tamano) {
        try (InputStream in = Fuentes.class.getResourceAsStream(CARPETA + nombre + ".ttf")) {
            if (in == null) return null;
            return Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.PLAIN, tamano);
        } catch (FontFormatException | IOException e) {
            return null;
        }
    }
}
