package UI.Elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Area de dibujo que se estira con la ventana. Le entrega al pintor su tamano
 * real en cada repintado, por eso ninguna geometria queda guardada y nada puede
 * desincronizarse al redimensionar.
 */
public class Lienzo extends JPanel {

    public interface Pintor {
        void pintar(Graphics2D g2, int ancho, int alto);
    }

    private final Pintor pintor;

    public Lienzo(Pintor pintor) {
        this.pintor = pintor;
        setOpaque(true);
        setBackground(UIConstants.ESCENARIO);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        pintor.pintar(g2, getWidth(), getHeight());
        g2.dispose();
    }
}
