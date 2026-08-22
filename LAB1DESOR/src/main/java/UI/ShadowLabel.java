package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ShadowLabel extends JLabel {

    public ShadowLabel() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        String text = getText();
        if (text == null || text.isEmpty()) {
            g2.dispose();
            return;
        }
        FontMetrics fm = g2.getFontMetrics(getFont());
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.setFont(getFont());
        g2.setColor(new Color(120, 230, 210, 90));
        g2.drawString(text, x + 1, y);
        g2.drawString(text, x - 1, y);
        g2.drawString(text, x, y + 1);
        g2.drawString(text, x, y - 1);

        g2.setColor(new Color(0, 6, 8, 120));
        g2.drawString(text, x + 1, y + 2);

        g2.setColor(getForeground());
        g2.drawString(text, x, y);
        g2.dispose();
    }
}