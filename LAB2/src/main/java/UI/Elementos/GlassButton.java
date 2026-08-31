package UI.Elementos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class GlassButton extends JButton {

    private Color baseColor;
    private Color textColor;
    private Color borderColor;
    private boolean hovered;

    public GlassButton() {
        this("GlassButton", new Color(20, 26, 30), new Color(238, 245, 247), new Color(205, 214, 220));
    }

    public GlassButton(String text, Color baseColor, Color textColor, Color borderColor) {
        super(text);
        this.baseColor = baseColor;
        this.textColor = textColor;
        this.borderColor = borderColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(170, 52));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                Sonido.hover();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
        addActionListener(e -> Sonido.click());
    }

    @Override
    public void setBackground(Color bg) {
        this.baseColor = bg;
        repaint();
    }

    @Override
    public Color getBackground() {
        return baseColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = Math.min(16, h - 2);

        int alpha = hovered ? 130 : 75;
        g2.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha));
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

        String txt = getText();
        if (txt != null && !txt.isEmpty()) {
            Font f = getFont() != null ? getFont() : new Font("Segoe UI", Font.BOLD, 14);
            g2.setFont(f);
            FontMetrics fm = g2.getFontMetrics();
            String[] lines = txt.split("\n", -1);
            int lineH = fm.getHeight();
            int y0 = (h - lineH * lines.length) / 2 + fm.getAscent();

            for (String line : lines) {
                int x = (w - fm.stringWidth(line)) / 2;
                g2.setColor(new Color(0, 6, 8, 90));
                g2.drawString(line, x + 1, y0 + 1);
                g2.setColor(textColor);
                g2.drawString(line, x, y0);
                y0 += lineH;
            }
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // border is painted in paintComponent
    }
}
