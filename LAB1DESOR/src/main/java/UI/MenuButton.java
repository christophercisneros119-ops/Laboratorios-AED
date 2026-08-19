package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;

public class MenuButton extends JComponent {

    private final String text;
    private boolean hovered;

    public MenuButton(String text) {
        this.text = text;
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = Math.min(16, h - 2);

        g2.setColor(new Color(20, 26, 30, hovered ? 130 : 75));
        g2.fill(new RoundRectangle2D.Double(1, 2, w - 2, h - 3, arc, arc));

        g2.setClip(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));
        LinearGradientPaint gloss = new LinearGradientPaint(
                0, 0, 0, h,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(255, 255, 255, hovered ? 150 : 70),
                        new Color(255, 255, 255, 12),
                        new Color(255, 255, 255, 0)});
        g2.setPaint(gloss);
        g2.fillRect(0, 0, w, h);
        g2.setClip(null);

        g2.setColor(new Color(205, 214, 220, hovered ? 200 : 120));
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));

        if (text != null && !text.isEmpty()) {
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            int x = (w - fm.stringWidth(text)) / 2;
            int y = (h - fm.getHeight()) / 2 + fm.getAscent();

            g2.setColor(new Color(0, 6, 8, 90));
            g2.drawString(text, x + 1, y + 1);
            g2.setColor(new Color(238, 245, 247));
            g2.drawString(text, x, y);
        }

        g2.dispose();
    }
}