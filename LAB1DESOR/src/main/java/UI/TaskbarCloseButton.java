package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

public class TaskbarCloseButton extends JComponent {

    private boolean hovered;

    public TaskbarCloseButton() {
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

        double r = Math.min(getWidth(), getHeight()) / 2.0;
        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        g2.setColor(new Color(0, 6, 8, 90));
        g2.fill(new Ellipse2D.Double(cx - r + 1, cy - r + 2, r * 2, r * 2));

        RadialGradientPaint orb = new RadialGradientPaint(
                new Point2D.Double(cx - r * 0.3, cy - r * 0.3),
                (float) (r * 1.6),
                new float[]{0f, 0.6f, 1f},
                new Color[]{
                        new Color(125, 205, 145),
                        new Color(52, 132, 72),
                        new Color(28, 78, 44)});
        g2.setPaint(orb);
        g2.fill(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));

        g2.setColor(new Color(238, 243, 247, hovered ? 235 : 170));
        g2.setStroke(new BasicStroke(hovered ? 2.2f : 1.4f));
        g2.draw(new Ellipse2D.Double(cx - r + 1, cy - r + 1, r * 2 - 2, r * 2 - 2));

        float ax = (float) (cx + r * 0.12);
        float aw = (float) (r * 0.34);
        float ay = (float) (cy - r * 0.3);
        float ah = (float) (r * 0.6);

        int[] xs = {(int) (ax - aw), (int) ax, (int) (ax - aw)};
        int[] ys = {(int) ay, (int) (ay + ah / 2.0), (int) (ay + ah)};

        g2.setColor(new Color(255, 255, 255, 240));
        g2.fillPolygon(xs, ys, 3);

        g2.setColor(new Color(255, 255, 255, 150));
        g2.fill(new Ellipse2D.Double(cx - r * 0.4, cy - r * 0.55, r * 0.35, r * 0.28));

        g2.dispose();
    }
}