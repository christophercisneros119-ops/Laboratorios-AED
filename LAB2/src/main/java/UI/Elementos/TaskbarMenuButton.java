package UI.Elementos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class TaskbarMenuButton extends JComponent {

    private boolean hovered;
    private Image icon;

    public TaskbarMenuButton() {
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        java.net.URL url = getClass().getResource("/img/menu-icon.png");
        if (url != null) {
            icon = new ImageIcon(url).getImage();
        }
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

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Sonido.click();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double r = Math.min(getWidth(), getHeight()) / 2.0;
        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        g2.setColor(new Color(0, 8, 10, 90));
        g2.fill(new Ellipse2D.Double(cx - r - 1, cy - r + 3, r * 2 + 2, r * 2 + 2));

        if (hovered) {
            g2.setColor(new Color(120, 240, 160, 70));
            g2.fill(new Ellipse2D.Double(cx - r - 4, cy - r - 4, r * 2 + 8, r * 2 + 8));
        }

        RadialGradientPaint orb = new RadialGradientPaint(
                new Point2D.Double(cx - r * 0.35, cy - r * 0.35),
                (float) (r * 1.7),
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(255, 255, 255, 240),
                        new Color(86, 170, 106),
                        new Color(34, 96, 54)});
        g2.setPaint(orb);
        g2.fill(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));

        g2.setColor(new Color(245, 250, 252, hovered ? 250 : 185));
        g2.setStroke(new BasicStroke(hovered ? 2.6f : 1.8f));
        g2.draw(new Ellipse2D.Double(cx - r + 1, cy - r + 1, r * 2 - 2, r * 2 - 2));

        if (icon != null) {
            int s = (int) (r * 1.15);
            g2.setClip(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));
            g2.drawImage(icon, (int) (cx - s / 2.0), (int) (cy - s / 2.0), s, s, null);
            g2.setClip(null);
        } else {
            g2.setColor(new Color(255, 255, 255, 235));
            float bw = (float) (r * 0.72);
            float bh = (float) (r * 0.16);
            float gx = (float) (cx - bw / 2.0);
            for (int i = 0; i < 3; i++) {
                float gy = (float) (cy - r * 0.32 + i * r * 0.32);
                g2.fillRoundRect((int) gx, (int) gy, (int) bw, (int) bh, (int) bh, (int) bh);
            }
        }

        g2.setColor(new Color(255, 255, 255, 200));
        g2.fill(new Ellipse2D.Double(cx - r * 0.45, cy - r * 0.55, r * 0.5, r * 0.38));

        g2.dispose();
    }
}
