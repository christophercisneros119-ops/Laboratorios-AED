package UI.Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/** Area de dibujo que entrega su tamano real en cada repintado. */
public class Lienzo extends JPanel {

    public interface Pintor {
        void pintar(Graphics2D g2, int ancho, int alto);
    }

    private final Pintor pintor;

    public Lienzo(Pintor pintor, Color fondo) {
        this.pintor = pintor;
        setOpaque(true);
        setBackground(fondo);
    }

    public Lienzo(Pintor pintor) {
        this(pintor, UIConstants.ESCENARIO);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // Sin antialiasing: el pixel art necesita bordes duros.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        pintor.pintar(g2, getWidth(), getHeight());
        g2.dispose();
    }
}
