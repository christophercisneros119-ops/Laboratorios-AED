package UI.Elements;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Carga los sprites y fondos de {@code src/main/resources/imagenes} una sola vez
 * y los pinta.
 *
 * <p>Los sprites de tablero y pieza usan escalado entero para no deformar el pixel
 * art; los fondos de escenario se estiran hasta cubrir el lienzo.
 *
 * <p>Si el archivo no existe devuelve {@code null}, para que quien dibuja pueda
 * seguir usando las formas planas de siempre y la aplicacion no se rompa.
 */
public final class Imagenes {

    private static final String CARPETA = "/imagenes/";
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final Map<String, List<BufferedImage>> SECUENCIAS = new HashMap<>();

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
     * Dibuja el fondo de un escenario hasta cubrir todo el lienzo, centrado.
     *
     * <p>Cada ejercicio tiene su propio fondo, asi que el nombre lo pasa quien
     * dibuja. Se escala por el mayor de los dos ejes para que siempre cubra y
     * nunca queden bandas vacias; lo que sobra en el eje que no domina se
     * descarta centrado.
     *
     * <p>A diferencia de los sprites, aqui si se usa interpolacion suave: el
     * fondo es una escena a pantalla completa, no pixel art, y Lienzo deja
     * NEAREST_NEIGHBOR para que los bordes de tablero y piezas sigan duros.
     *
     * @return {@code false} si el archivo no existe, para que asome el color plano
     *         que Lienzo ya pinta de fondo
     */
    public static boolean dibujarFondo(Graphics2D g2, int ancho, int alto, String nombre) {
        return dibujarCubriendo(g2, ancho, alto, cargar(nombre));
    }

    /**
     * Dibuja un fondo de varios cuadros y devuelve si se pudo pintar alguno.
     *
     * <p>El indice se acota por cantidad, asi que un cuadro que falta no rompe
     * nada: el fondo animated se degrada solo a los cuadros que si estan.
     *
     * @return {@code false} si la lista esta vacia, para que asome el color plano
     */
    public static boolean dibujarSecuencia(Graphics2D g2, int ancho, int alto,
                                            List<BufferedImage> cuadros, int indice) {
        if (cuadros == null || cuadros.isEmpty()) {
            return false;
        }
        int i = Math.floorMod(indice, cuadros.size());
        return dibujarCubriendo(g2, ancho, alto, cuadros.get(i));
    }

    /**
     * Escala la imagen hasta cubrir el lienzo y la centra, con interpolacion suave.
     *
     * <p>Se dibuja sobre una copia para no dejar NEAREST_NEIGHBOR cambiado: el
     * tablero y las piezas que dibuja el mismo lienzo necesitan bordes duros.
     */
    private static boolean dibujarCubriendo(Graphics2D g2, int ancho, int alto,
                                            BufferedImage original) {
        if (original == null) {
            return false;
        }
        int iw = original.getWidth();
        int ih = original.getHeight();
        if (iw <= 0 || ih <= 0) {
            return false;
        }
        double escala = Math.max(ancho / (double) iw, alto / (double) ih);
        int destinoAncho = (int) Math.round(iw * escala);
        int destinoAlto = (int) Math.round(ih * escala);
        int x = (ancho - destinoAncho) / 2;
        int y = (alto - destinoAlto) / 2;

        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, x, y, destinoAncho, destinoAlto, null);
        suave.dispose();
        return true;
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

    /**
     * Carga los cuadros de un fondo animado: {@code prefijo_00.png},
     * {@code prefijo_01.png}... y sigue hasta el primero que falta.
     *
     * <p>Se detiene en el primer hueco a proposito, asi agregar un cuadro nuevo es
     * solo(drop) el archivo y no hay que tocar ningun numero en el codigo.
     *
     * <p>Se cachean igual que los sprites, porque 24 imagenes de 960x540 abiertas
     * de nuevo en cada repintado serian unos 36 MB por vuelta.
     */
    public static List<BufferedImage> cargarSecuencia(String prefijo, int maximo) {
        String clave = prefijo.toLowerCase();
        if (SECUENCIAS.containsKey(clave)) {
            return SECUENCIAS.get(clave);
        }
        List<BufferedImage> cuadros = new ArrayList<>();
        for (int i = 0; i < maximo; i++) {
            BufferedImage cuadro = cargar(clave + "_" + String.format("%02d", i));
            if (cuadro == null) {
                break;
            }
            cuadros.add(cuadro);
        }
        SECUENCIAS.put(clave, List.copyOf(cuadros));
        return SECUENCIAS.get(clave);
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
