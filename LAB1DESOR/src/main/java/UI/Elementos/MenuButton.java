package UI.Elementos;

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

    private static final Color DEFAULT_BASE = new Color(20, 26, 30);
    private static final Color DEFAULT_TEXT = new Color(238, 245, 247);
    private static final Color DEFAULT_BORDER = new Color(205, 214, 220);

    private final String text;
    private final Color baseColor;
    private final Color textColor;
    private final Color borderColor;
    private final boolean solid;
    private boolean hovered;

    public MenuButton(String text) {
        this(text, DEFAULT_BASE);
    }

    public MenuButton(String text, Color baseColor) {
        this(text, baseColor, DEFAULT_TEXT, DEFAULT_BORDER, false);
    }

    public MenuButton(String text, Color baseColor, Color textColor, Color borderColor) {
        this(text, baseColor, textColor, borderColor, true);
    }

    private MenuButton(String text, Color baseColor, Color textColor, Color borderColor, boolean solid) {
        this.text = text;
        this.baseColor = baseColor;
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.solid = solid;
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

    public String getText() {
        return text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = Math.min(16, h - 2);

        int baseAlpha = solid ? (hovered ? 255 : 240) : (hovered ? 130 : 75);
        g2.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), baseAlpha));
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

        g2.setColor(new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(),
                hovered ? 230 : 150));
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, arc, arc));

        if (text != null && !text.isEmpty()) {
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            String[] lines = text.split("\n", -1);
            int lineHeight = fm.getHeight();
            int y0 = (h - lineHeight * lines.length) / 2 + fm.getAscent();

            for (String line : lines) {
                int x = (w - fm.stringWidth(line)) / 2;

                g2.setColor(new Color(0, 6, 8, solid ? 40 : 90));
                g2.drawString(line, x + 1, y0 + 1);
                g2.setColor(textColor);
                g2.drawString(line, x, y0);

                y0 += lineHeight;
            }
        }

        g2.dispose();
    }
}
