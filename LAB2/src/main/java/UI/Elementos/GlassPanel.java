package UI.Elementos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;

public class GlassPanel extends JComponent {

    public GlassPanel() {
        setOpaque(false);
    }

    protected void paintGlassFrame(Graphics2D g2, int w, int h, int arc, Color fill) {
        g2.setColor(new Color(0, 8, 10, 80));
        g2.fill(new RoundRectangle2D.Double(2, 5, w - 4, h - 6, arc, arc));

        g2.setColor(fill);
        g2.fill(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, arc, arc));

        g2.setClip(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, arc, arc));
        LinearGradientPaint reflection = new LinearGradientPaint(
                0, 0, w * 0.6f, h * 0.6f,
                new float[]{0f, 0.55f, 1f},
                new Color[]{
                        new Color(255, 255, 255, 120),
                        new Color(255, 255, 255, 25),
                        new Color(255, 255, 255, 0)});
        g2.setPaint(reflection);
        g2.fillRect(0, 0, w, h / 2);
        g2.setClip(null);

        g2.setColor(new Color(255, 255, 255, 120));
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(new RoundRectangle2D.Double(0.6, 0.6, w - 1.2, h - 1.2, arc, arc));
    }
}