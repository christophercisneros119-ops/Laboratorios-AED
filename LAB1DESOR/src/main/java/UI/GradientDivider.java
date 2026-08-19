
package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class GradientDivider extends JComponent {

    private static final Color LIGHT = new Color(30, 70, 72);
    private static final Color DARK = new Color(90, 200, 180);
    private static final Color BLUE = new Color(80, 170, 210);

    public GradientDivider() {
        setOpaque(false);
        setPreferredSize(new Dimension(300, 6));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(210, 240, 235, 160));
        g2.fillRoundRect(0, 0, w, h / 2, h, h);

        g2.setColor(LIGHT);
        g2.fillRoundRect(0, h / 2, w, h - h / 2, h, h);

        java.awt.LinearGradientPaint accent = new java.awt.LinearGradientPaint(
                0, 0, w, 0,
                new float[]{0f, 0.5f, 1f},
                new Color[]{DARK, BLUE, DARK});
        g2.setPaint(accent);
        g2.fillRoundRect(0, h / 2, w, 1, 1, 1);

        g2.dispose();
    }
}