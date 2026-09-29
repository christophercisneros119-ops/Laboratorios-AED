package UI.Elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Tarjeta cuadrada de la portada con el marco de pixel art y la foto del
 * integrante adentro, a prueba de que la imagen no exista.
 *
 * <p>El marco es un bisel de dos tonos sin suavizado, igual que un frame de
 * pixel art: el tono exterior marca el contorno y el claro escala hacia
 * adentro. Dentro se estira la foto hasta cubrir el hueco, centrada y con
 * interpolacion suave, porque las fotos no son pixel art.
 *
 * <p>Si la imagen no esta, se pinta el hueco oscuro con las iniciales para que
 * la portada siga armada igual; las iniciales las pasa quien llama.
 */
public class TarjetaMiembro extends JPanel {

    /** Borde del marco exterior, en pixeles del lado. */
    private static final int MARCO = 6;
    private static final Color BORDE_TARJETA = new Color(0x3A4754);
    private static final Color BISEL_TARJETA = new Color(0x9AA8B4);
    private static final Color FONDO_TARJETA = new Color(0x0D1B26);

    private final BufferedImage foto;
    private final String iniciales;
    private final int lado;

    public TarjetaMiembro(String imagen, String iniciales, int lado) {
        foto = Imagenes.obtener(imagen);
        this.iniciales = iniciales;
        this.lado = lado;
        Dimension tamano = new Dimension(lado, lado);
        setPreferredSize(tamano);
        setMinimumSize(tamano);
        setMaximumSize(tamano);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int interior = lado - 2 * MARCO;
        g2.setColor(BORDE_TARJETA);
        g2.fillRect(0, 0, lado, lado);
        g2.setColor(BISEL_TARJETA);
        g2.fillRect(MARCO, MARCO, interior, interior);
        g2.setColor(FONDO_TARJETA);
        g2.fillRect(2 * MARCO, 2 * MARCO, interior - 2 * MARCO, interior - 2 * MARCO);

        int hueco = interior - 2 * MARCO;
        if (foto != null) {
            g2.setClip(2 * MARCO, 2 * MARCO, hueco, hueco);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            double escala = Math.max(hueco / (double) foto.getWidth(),
                    hueco / (double) foto.getHeight());
            int ancho = (int) Math.round(foto.getWidth() * escala);
            int alto = (int) Math.round(foto.getHeight() * escala);
            int x = 2 * MARCO + (hueco - ancho) / 2;
            int y = 2 * MARCO + (hueco - alto) / 2;
            g2.drawImage(foto, x, y, ancho, alto, null);
        } else {
            g2.setFont(UIConstants.FONT_TITULO);
            FontMetrics medidas = g2.getFontMetrics();
            int ancho = medidas.stringWidth(iniciales);
            int arriba = 2 * MARCO + (hueco + medidas.getAscent()) / 2;
            g2.setColor(UIConstants.TEXTO_SUAVE);
            g2.drawString(iniciales, 2 * MARCO + (hueco - ancho) / 2, arriba);
        }

        g2.dispose();
    }
}