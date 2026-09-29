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
    private static final Map<BufferedImage, int[]> ARTES = new HashMap<>();

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
     * Dibuja un fondo entero, sin recortar nada, apoyado en la base del lienzo.
     *
     * <p>Es el "contain" de los editores: el fondo se encaja completo dentro del
     * lienzo y se deja apoyado abajo, de modo que la escena crece de abajo hacia
     * arriba y lo que sobre de alto queda arriba. Sirve para escenarios anchos
     * que, con {@link #dibujarFondo}, se recortarian de los costados.
     *
     * @return {@code false} si el archivo no existe, para que asome el color plano
     */
    public static boolean dibujarFondoPie(Graphics2D g2, int ancho, int alto, String nombre) {
        BufferedImage original = cargar(nombre);
        if (original == null) {
            return false;
        }
        int iw = original.getWidth();
        int ih = original.getHeight();
        if (iw <= 0 || ih <= 0) {
            return false;
        }
        double escala = Math.min(ancho / (double) iw, alto / (double) ih);
        int destinoAncho = (int) Math.round(iw * escala);
        int destinoAlto = (int) Math.round(ih * escala);
        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, (ancho - destinoAncho) / 2, alto - destinoAlto,
                destinoAncho, destinoAlto, null);
        suave.dispose();
        return true;
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
    /**
     * Caja donde cae el fondo al cubrir el lienzo: {@code {x, y, ancho, alto}}.
     *
     * <p>Es la misma aritmetica del cover, expuesta para que un ejercicio pueda
     * anclar sus piezas a un punto del recurso y no a {@code alto / 2}: como el
     * fondo se escala para cubrir, la mitad del lienzo no cae en la misma fila
     * de la foto cuando cambia la proporcion de la ventana.
     *
     * @return {@code null} si el archivo no existe
     */
    public static int[] rectanguloFondo(int ancho, int alto, String nombre) {
        return encuadreCubriendo(ancho, alto, cargar(nombre));
    }

    /**
     * Aritmetica compartida del cover: escala por el eje que mas crece y centra
     * el sobrante del otro.
     *
     * @return {@code {x, y, ancho, alto}} o {@code null} si no hay nada que dibujar
     */
    private static int[] encuadreCubriendo(int ancho, int alto, BufferedImage original) {
        if (original == null) {
            return null;
        }
        int iw = original.getWidth();
        int ih = original.getHeight();
        if (iw <= 0 || ih <= 0) {
            return null;
        }
        double escala = Math.max(ancho / (double) iw, alto / (double) ih);
        int destinoAncho = (int) Math.round(iw * escala);
        int destinoAlto = (int) Math.round(ih * escala);
        return new int[]{(ancho - destinoAncho) / 2, (alto - destinoAlto) / 2,
                destinoAncho, destinoAlto};
    }

    /**
     * Escala la imagen hasta cubrir el lienzo y la centra, con interpolacion suave.
     *
     * <p>Se dibuja sobre una copia para no dejar NEAREST_NEIGHBOR cambiado: el
     * tablero y las piezas que dibuja el mismo lienzo necesitan bordes duros.
     */
    private static boolean dibujarCubriendo(Graphics2D g2, int ancho, int alto,
                                            BufferedImage original) {
        int[] rect = encuadreCubriendo(ancho, alto, original);
        if (rect == null) {
            return false;
        }
        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, rect[0], rect[1], rect[2], rect[3], null);
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
     * Caja del arte visible de un sprite: {@code {x0, x1, y0, y1}}.
     *
     * <p>Un sprite de escenario suele traer el lienzo transparente alrededor, y
     * ese sobrante no siempre queda igual a cada lado: en {@code piedra.png} el
     * arte ocupa las columnas 4 a 21 de una caja de 24, asi que su centro es
     * 12.5 y no 11.5. Al escalar, ese medio pixel se convierte en varios pixeles
     * y la piedra se ve corrida respecto del centro de la casilla.
     *
     * <p>Se cachea porque el recorrido es sobre todos los pixeles y solo hace
     * falta una vez por imagen. Un sprite completamente transparente devuelve la
     * caja entera, que es lo mismo que centrar por el lienzo.
     */
    private static int[] arte(BufferedImage original) {
        int[] caja = ARTES.get(original);
        if (caja != null) {
            return caja;
        }
        int x0 = -1;
        int y0 = -1;
        int x1 = -1;
        int y1 = -1;
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                if ((original.getRGB(x, y) >>> 24) > 0) {
                    // Minimo y maximo de verdad. Con "el primer pixel que sale"
                    // y "el ultimo" habria que tomar los extremos de la fila de
                    // arriba y los de la de abajo, y en un nenufar, que es mas
                    // ancho por el medio, eso no es la caja del dibujo.
                    if (x0 < 0) {
                        x0 = x;
                        y0 = y;
                    }
                    x0 = Math.min(x0, x);
                    y0 = Math.min(y0, y);
                    x1 = Math.max(x1, x);
                    y1 = Math.max(y1, y);
                }
            }
        }
        caja = x0 < 0
                ? new int[]{0, original.getWidth() - 1, 0, original.getHeight() - 1}
                : new int[]{x0, x1, y0, y1};
        ARTES.put(original, caja);
        return caja;
    }

    /**
     * Caja del arte visible de un sprite: {@code {x0, x1, y0, y1}}.
     *
     * <p>Es la misma medida privada {@link #arte(BufferedImage)}, expuesta para
     * que un panel pueda alinear una pieza por un borde del dibujo y no solo por
     * el centro: la rana se apoya con los pies en el nenufar y necesita la
     * altura real de su arte, no la de la caja de 27 px.
     *
     * @return {@code null} si el archivo no existe; se devuelve una copia para
     *         no dejar que nadie escriba la cache
     */
    public static int[] cajaArte(String nombre) {
        BufferedImage original = cargar(nombre);
        return original == null ? null : arte(original).clone();
    }

    /**
     * Dibuja un sprite centrado en un punto, a factor entero y sin recortar nada.
     *
     * <p>Es la variante sin el aro de {@link #dibujarPieza}: los nenufares, la
     * piedra y las ranas llegan con su lienzo transparente alrededor y sin marco
     * negro, asi que {@code sinMarco} no debe tocar a estos sprites. Recortales
     * 1 px se comeria arte de verdad.
     *
     * <p>El centro se toma del arte visible y no del lienzo, con
     * {@link #arte(BufferedImage)}, para que un dibujo que quede a un lado
     * salga igual de centrado en la casilla. La altura se recalcula aparte para no
     * deformar un sprite que no sea cuadrado, y el centro se redondea una sola
     * vez, igual que en {@code dibujarPieza}.
     *
     * @param factor escala entera del sprite; mantiene el pixel art nitido
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su representacion plana
     */
    public static boolean dibujarCentrado(Graphics2D g2, String nombre,
                                          double centroX, double centroY, int factor) {
        BufferedImage original = cargar(nombre);
        if (original == null || factor < 1) {
            return false;
        }
        int[] arte = arte(original);
        double desvioX = (arte[0] + arte[1]) / 2.0 - (original.getWidth() - 1) / 2.0;
        double desvioY = (arte[2] + arte[3]) / 2.0 - (original.getHeight() - 1) / 2.0;
        int destinoAncho = original.getWidth() * factor;
        int destinoAlto = original.getHeight() * factor;
        int x = (int) Math.round(centroX - destinoAncho / 2.0 - desvioX * factor);
        int y = (int) Math.round(centroY - destinoAlto / 2.0 - desvioY * factor);
        g2.drawImage(original, x, y, destinoAncho, destinoAlto, null);
        return true;
    }

    /**
     * Dibuja una espada como barra de QuickSort, a factor entero y sin deformar
     * los extremos.
     *
     * <p>El sprite se parte en tres bandas horizontales, igual que un 9-slice: la
     * punta (cuarto superior), la hoja (mitad central) y la empuñadura con la
     * guarda (cuarto inferior). El lienzo promete, entonces, un sprite orientado
     * con la punta arriba y la empuñadura abajo; la altura variable de la barra
     * solo estira la hoja, que es una barra gris uniforme, asi ni la punta ni la
     * agarradera se deforman cuando cambia el valor.
     *
     * <p>La hoja se toma de la mitad central, de modo que cualquier
     * {@code espada.png} dibujado con esa misma forma de tres partes funciona sin
     * tocar el codigo.
     *
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su barra plana
     */
    public static boolean dibujarEspada(Graphics2D g2, String nombre,
                                        int centroX, int baseY,
                                        int anchoBarra, int altoBarra) {
        BufferedImage original = cargar(nombre);
        if (original == null || altoBarra < 1) {
            return false;
        }
        int ancho = original.getWidth();
        int alto = original.getHeight();
        if (ancho < 1 || alto < 1) {
            return false;
        }
        int factor = Math.max(1, anchoBarra / ancho);
        int punta = alto / 4;
        int mango = alto / 4;
        int finHoja = alto - mango;

        int anchoDestino = ancho * factor;
        int x = centroX - anchoDestino / 2;
        int tope = baseY - altoBarra;
        int altoPunta = Math.min(punta * factor, altoBarra);
        int altoMango = Math.min(mango * factor, altoBarra - altoPunta);
        int altoHoja = altoBarra - altoPunta - altoMango;

        g2.drawImage(original, x, tope, x + anchoDestino, tope + altoPunta,
                0, 0, ancho, punta, null);
        if (altoHoja > 0) {
            g2.drawImage(original, x, tope + altoPunta, x + anchoDestino,
                    tope + altoPunta + altoHoja,
                    0, punta, ancho, finHoja, null);
        }
        g2.drawImage(original, x, baseY - altoMango, x + anchoDestino, baseY,
                0, finHoja, ancho, alto, null);
        return true;
    }

    /**
     * Dibuja un piso de tarta rellenando su rectangulo, sin recortar pintado.
     *
     * <p>El sprite trae el dibujo del piso en un lienzo transparente mas grande
     * que el propio piso. Se recorta solo el margen transparente, ningun pixel
     * dibujado se pierde, y la region visible se estira con vecino mas cercano
     * hasta llenar el rectangulo que ocupa el piso en el lienzo. Asi los pisos
     * de distinto ancho se arman a partir de un mismo sprite y quedan contiguos
     * en la torre.
     *
     * @return {@code false} si el archivo no existe, para que quien dibuja caiga
     *         en su caja plana
     */
    public static boolean dibujarPiso(Graphics2D g2, String nombre,
                                      int x, int y, int ancho, int alto) {
        BufferedImage original = cargar(nombre);
        if (original == null || ancho < 1 || alto < 1) {
            return false;
        }
        int[] caja = arte(original);
        int anchoArte = caja[1] - caja[0] + 1;
        int altoArte = caja[3] - caja[2] + 1;
        g2.drawImage(original, x, y, x + ancho, y + alto,
                caja[0], caja[2], caja[0] + anchoArte, caja[2] + altoArte, null);
        return true;
    }

    /**
     * Carga los cuadros de un fondo animado: {@code prefijo_00.png},
     * {@code prefijo_01.png}... y sigue hasta el primero que falta.
     *
     * <p>Se detiene en el primer hueco a proposito, asi agregar un cuadro nuevo es
     * solo dejar el archivo y no hay que tocar ningun numero en el codigo.
     *
     * <p>Se cachean igual que los sprites, porque 24 imagenes de 960x540 indexadas
     * abiertas de nuevo en cada repintado serian unos 11.5 MB por vuelta.
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

    public static BufferedImage obtener(String nombre) {
        return cargar(nombre);
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
