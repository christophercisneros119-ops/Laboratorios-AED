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

public final class Imagenes {

    private static final String CARPETA = "/imagenes/";
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final Map<String, List<BufferedImage>> SECUENCIAS = new HashMap<>();

    private Imagenes() {}

    public static BufferedImage obtener(String nombre) {
        return cargar(nombre);
    }

    public static List<BufferedImage> cargarSecuencia(String prefijo, int maximo) {
        String clave = prefijo.toLowerCase();
        return SECUENCIAS.computeIfAbsent(clave, k -> {
            List<BufferedImage> cuadros = new ArrayList<>();
            for (int i = 0; i < maximo; i++) {
                BufferedImage cuadro = cargar(clave + "_" + String.format("%02d", i));
                if (cuadro == null) break;
                cuadros.add(cuadro);
            }
            return List.copyOf(cuadros);
        });
    }

    public static boolean dibujarSecuencia(Graphics2D g2, int ancho, int alto,
                                           List<BufferedImage> cuadros, int indice) {
        if (cuadros == null || cuadros.isEmpty()) return false;
        int i = Math.floorMod(indice, cuadros.size());
        return dibujarCubriendo(g2, ancho, alto, cuadros.get(i), 0, 0);
    }

    public static boolean dibujarSecuenciaRecortada(Graphics2D g2, int ancho, int alto,
                                                    List<BufferedImage> cuadros, int indice,
                                                    double recorte) {
        if (cuadros == null || cuadros.isEmpty()) return false;
        BufferedImage original = cuadros.get(Math.floorMod(indice, cuadros.size()));
        if (original == null) return false;

        int iw = original.getWidth();
        int ih = original.getHeight();
        if (iw <= 0 || ih <= 0) return false;

        double fraccion = Math.clamp(recorte, 0.0, 0.9);
        int sw = iw - (int) Math.round(iw * fraccion);
        int sh = ih - (int) Math.round(ih * fraccion);
        if (sw < 1 || sh < 1) return false;

        double escala = Math.max(ancho / (double) sw, alto / (double) sh);
        int dw = (int) Math.round(sw * escala);
        int dh = (int) Math.round(sh * escala);
        if (dw < 1 || dh < 1) return false;

        int dx = (ancho - dw) / 2;
        int dy = (alto - dh) / 2;

        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, dx, dy, dx + dw, dy + dh, 0, 0, sw, sh, null);
        suave.dispose();
        return true;
    }

    private static boolean dibujarCubriendo(Graphics2D g2, int ancho, int alto,
                                            BufferedImage original, int origenX, int origenY) {
        if (original == null || original.getWidth() <= 0 || original.getHeight() <= 0) {
            return false;
        }
        double escala = Math.max(ancho / (double) original.getWidth(), alto / (double) original.getHeight());
        int destinoAncho = (int) Math.round(original.getWidth() * escala);
        int destinoAlto = (int) Math.round(original.getHeight() * escala);
        int dx = (ancho - destinoAncho) / 2 + origenX;
        int dy = (alto - destinoAlto) / 2 + origenY;

        Graphics2D suave = (Graphics2D) g2.create();
        suave.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        suave.drawImage(original, dx, dy, destinoAncho, destinoAlto, null);
        suave.dispose();
        return true;
    }

    private static BufferedImage cargar(String nombre) {
        String clave = nombre.toLowerCase();
        return CACHE.computeIfAbsent(clave, k -> {
            try (InputStream in = Imagenes.class.getResourceAsStream(CARPETA + k + ".png")) {
                return in != null ? ImageIO.read(in) : null;
            } catch (IOException e) {
                return null;
            }
        });
    }
}
