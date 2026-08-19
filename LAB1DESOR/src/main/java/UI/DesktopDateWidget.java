package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;

public class DesktopDateWidget extends JComponent {

    private static final Color WIDGET_FILL = new Color(150, 155, 160, 120);
    private static final Color TEXT_COLOR = new Color(245, 248, 250);

    public DesktopDateWidget() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(0, 8, 10, 60));
        g2.fillRoundRect(2, 4, w - 4, h - 4, 18, 18);
        g2.setColor(WIDGET_FILL);
        g2.fillRoundRect(0, 0, w - 1, h - 1, 18, 18);

        g2.setClip(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, 18, 18));
        LinearGradientPaint gloss = new LinearGradientPaint(
                0, 0, 0, h * 0.5f,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(255, 255, 255, 130),
                        new Color(255, 255, 255, 0)});
        g2.setPaint(gloss);
        g2.fillRect(0, 0, w, h / 2);
        g2.setClip(null);

        g2.setColor(new Color(235, 240, 244, 160));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, 18, 18);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 52));
        FontMetrics fm = g2.getFontMetrics();
        String big = "27";
        int x = (w - fm.stringWidth(big)) / 2;
        int y = (int) (h * 0.44);

        g2.setColor(new Color(255, 255, 255, 140));
        g2.drawString(big, x + 2, y + 2);
        g2.setColor(TEXT_COLOR);
        g2.drawString(big, x, y);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        FontMetrics fm2 = g2.getFontMetrics();
        String small = "Thursday, 2026";
        int x2 = (w - fm2.stringWidth(small)) / 2;
        int y2 = (int) (h * 0.76);

        g2.setColor(new Color(235, 240, 243, 220));
        g2.drawString(small, x2, y2);

        g2.dispose();
    }
}