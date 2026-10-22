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

/** Carga fondos y fotos de resources una sola vez y los pinta. */
public final class Imagenes {

    private static final String CARPETA = "/imagenes/";
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final Map<String, List<BufferedImage>> SECUENCIAS = new HashMap<>();

    private Imagenes() {}

    public static BufferedImage obtener(String nombre) {
        return cargar(nombre);
    }

    // Carga prefijo_00.png, prefijo_01.png... hasta el primer hueco.
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

    public static boolean dibujarSecuencia(Graphics2D g2, int ancho, int alto,
                                           List<BufferedImage> cuadros, int indice) {
        if (cuadros == null || cuadros.isEmpty()) {
            return false;
        }
        int i = Math.floorMod(indice, cuadros.size());
        return dibujarCubriendo(g2, ancho, alto, cuadros.get(i), 0, 0);
    }

    // Recorta la esquina inferior derecha para dejar fuera el @ del autor del arte.
    public static boolean dibujarSecuenciaRecortada(Graphics2D g2, int ancho, int alto,
                                                    List<BufferedImage> cuadros, int indice,
                                                    double recorte) {
        if (cuadros == null || cuadros.isEmpty()) {
            return false;
        }
        int i = Math.floorMod(indice, cuadros.size());
        BufferedImage original = cuadros.get(i);
        if (original == null) {
            return false;
        }
        int iw = original.getWidth();
        int ih = original.getHeight();
        if (iw <= 0 || ih <= 0) {
            return false;
        }
        double fraccion = Math.max(0.0, Math.min(0.9, recorte));
        int sw = iw - (int) Math.round(iw * fraccion);
        int sh = ih - (int) Math.round(ih * fraccion);
        if (sw < 1 || sh < 1) {
            return false;
        }
        double escala = Math.max(ancho / (double) sw, alto / (double) sh);
        int dw = (int) Math.round(sw * escala);
        int dh = (int) Math.round(sh * escala);
        if (dw < 1 || dh < 1) {
            return false;
        }
        int dx = (ancho - dw) / 2;
        int dy = (alto - dh) / 2;
        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, dx, dy, dx + dw, dy + dh, 0, 0, sw, sh, null);
        suave.dispose();
        return true;
    }

    // Escala hasta cubrir el lienzo y centra, con interpolacion suave.
    private static boolean dibujarCubriendo(Graphics2D g2, int ancho, int alto,
                                            BufferedImage original, int origenX, int origenY) {
        if (original == null || original.getWidth() <= 0 || original.getHeight() <= 0) {
            return false;
        }
        double escala = Math.max(ancho / (double) original.getWidth(),
                alto / (double) original.getHeight());
        int destinoAncho = (int) Math.round(original.getWidth() * escala);
        int destinoAlto = (int) Math.round(original.getHeight() * escala);
        int dx = (ancho - destinoAncho) / 2 + origenX;
        int dy = (alto - destinoAlto) / 2 + origenY;
        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, dx, dy, destinoAncho, destinoAlto, null);
        suave.dispose();
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
