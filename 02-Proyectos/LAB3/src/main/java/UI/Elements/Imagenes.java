package UI.Elements;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Carga los sprites de {@code src/main/resources/imagenes} una sola vez y los
 * pinta con escalado entero.
 *
 * <p>Si el archivo no existe devuelve {@code null}, para que quien dibuja pueda
 * seguir usando las formas planas de siempre y la aplicacion no se rompa.
 */
public final class Imagenes {

    private static final String CARPETA = "/imagenes/";
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    /**
     * Pixels transparentes de borde que traen los sprites (1 en queen.png). La
     * escala se calcula sobre el contenido, no sobre el sprite completo, para
     * que el arte ocupe la casilla completa sin deformarse.
     */
    private static final int MARGEN_SPRITE = 1;

    private Imagenes() {}

    /**
     * Devuelve el sprite ya escalado para ocupar un lado dado, o {@code null} si
     * no esta. La escala se calcula sobre el contenido util del sprite, asi el
     * factor siempre es entero y el pixel art no se deforma.
     */
    public static BufferedImage obtener(String nombre, int lado) {
        BufferedImage original = cargar(nombre);
        if (original == null || lado <= 0) {
            return null;
        }
        int contenido = original.getWidth() - 2 * MARGEN_SPRITE;
        int escala = lado / contenido;
        if (escala < 1) {
            escala = 1;
        }
        int destino = original.getWidth() * escala;
        BufferedImage escalada = new BufferedImage(destino, destino,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = escalada.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(original, 0, 0, destino, destino, null);
        g2.dispose();
        return escalada;
    }

    /**
     * Dibuja el sprite centrado en el punto indicado.
     *
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su representacion plana.
     */
    public static boolean dibujarCentrado(Graphics2D g2, String nombre,
                                          int centroX, int centroY, int lado) {
        BufferedImage sprite = obtener(nombre, lado);
        if (sprite == null) {
            return false;
        }
        int x = centroX - sprite.getWidth() / 2;
        int y = centroY - sprite.getHeight() / 2;
        g2.drawImage(sprite, x, y, null);
        return true;
    }

    private static BufferedImage cargar(String nombre) {
        String clave = nombre.toLowerCase();
        if (CACHE.containsKey(clave)) {
            return CACHE.get(clave);
        }
        BufferedImage imagen = null;
        try (InputStream in = Imagenes.class.getResourceAsStream(CARPETA + clave + ".png")) {
            if (in != null) {
                imagen = ImageIO.read(in);
            }
        } catch (IOException ex) {
            imagen = null;
        }
        CACHE.put(clave, imagen);
        return imagen;
    }
}
