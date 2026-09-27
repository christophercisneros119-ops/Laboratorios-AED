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
     * Grosor del aro opaco que traen los sprites de pieza (1 en queen.png).
     *
     * <p>Las piezas vienen con un cuadro negro de 1px alrededor. Al escalarse ese
     * cuadro crece con el factor y la pieza queda encerrada en un marco que se
     * come la celda, asi que se recorta antes de dibujar.
     */
    private static final int MARGEN_SPRITE = 1;

    private Imagenes() {}

    /** Sprite sin el aro exterior, ya cacheado para no recalcularlo cada frame. */
    private static BufferedImage sinMarco(BufferedImage original) {
        if (original == null) {
            return null;
        }
        int util = original.getWidth() - 2 * MARGEN_SPRITE;
        if (util <= 0) {
            return original;
        }
        // getSubImage comparte memoria con el original: se copia para que el
        // recorte no dependa de la imagen que se cargo.
        BufferedImage recorte = new BufferedImage(util, util, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = recorte.createGraphics();
        g2.drawImage(original, -MARGEN_SPRITE, -MARGEN_SPRITE, null);
        g2.dispose();
        return recorte;
    }

    /**
     * Dibuja un sprite entero, sin recortar, escalado por un factor entero.
     *
     * <p>El tablero se dibuja de una sola vez y no repetido como casilla: su arte
     * tiene textura propia y el marco debe quedar una unica vez alrededor.
     *
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su representacion plana
     */
    public static boolean dibujarEscalado(Graphics2D g2, String nombre,
                                          int x, int y, int factor) {
        BufferedImage original = cargar(nombre);
        if (original == null || factor < 1) {
            return false;
        }
        int lado = original.getWidth() * factor;
        g2.drawImage(original, x, y, lado, lado, null);
        return true;
    }

    /**
     * Dibuja una pieza centrada en un punto, sin su marco, a factor entero.
     *
     * <p>El centro se recibe en coma flotante a proposito. El centro del pixel
     * de indice {@code n} esta en {@code n + 0.5}, y como la celda del tablero
     * tiene ancho impar su centro geometrico cae exacto en medio pixel, asi que
     * redondear el centro antes de dibujar meteria un sesgo de medio pixel
     * siempre hacia el mismo lado. Se mantiene la geometria en float y se
     * redondea una sola vez, aqui, al calcular la esquina del blit.
     *
     * @param factor escala entera del sprite; mantiene el pixel art nitido
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su representacion plana
     */
    public static boolean dibujarPieza(Graphics2D g2, String nombre,
                                       double centroX, double centroY, int factor) {
        BufferedImage pieza = sinMarco(cargar(nombre));
        if (pieza == null || factor < 1) {
            return false;
        }
        int lado = pieza.getWidth() * factor;
        int x = (int) Math.round(centroX - lado / 2.0);
        int y = (int) Math.round(centroY - lado / 2.0);
        g2.drawImage(pieza, x, y, lado, lado, null);
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
