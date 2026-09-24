package UI.Elementos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MemberTile extends GlassPanel {

    private static final Color TEXT_COLOR = new Color(222, 240, 236);
    private static final Color ACCENT_COLOR = new Color(120, 220, 200);
    private static final Color RING_GREEN = new Color(90, 200, 180);

    private Image photo;

    private final JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel carnetLabel = new JLabel("", SwingConstants.CENTER);

    public MemberTile() {
        setPreferredSize(new Dimension(160, 160));
        setLayout(null);

        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setBounds(8, 106, 144, 24);

        carnetLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        carnetLabel.setForeground(ACCENT_COLOR);
        carnetLabel.setBounds(8, 132, 144, 20);

        add(nameLabel);
        add(carnetLabel);
    }

    public void setFullName(String fullName) {
        nameLabel.setText(fullName);
    }

    public void setCarnetNumber(String carnetNumber) {
        carnetLabel.setText(carnetNumber);
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
        repaint();
    }

    public Image getPhoto() {
        return photo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = 20;

        Color fill = new Color(12, 36, 44, 175);
        paintGlassFrame(g2, w, h, arc, fill);

        if (photo != null) {
            int ps = 80;
            int px = (w - ps) / 2;
            int py = 16;

            g2.setClip(new RoundRectangle2D.Double(px, py, ps, ps, 6, 6));
            int imgW = photo.getWidth(null);
            int imgH = photo.getHeight(null);
            if (imgW > 0 && imgH > 0) {
                double scale = Math.max(ps / (double) imgW, ps / (double) imgH);
                int dw = (int) (imgW * scale);
                int dh = (int) (imgH * scale);
                g2.drawImage(photo, px + (ps - dw) / 2, py + (ps - dh) / 2, dw, dh, null);
            }
            g2.setClip(null);

            g2.setColor(new Color(210, 218, 224, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Double(px, py, ps, ps, 6, 6));

            g2.setColor(new Color(250, 252, 254, 210));
            g2.setStroke(new BasicStroke(1.1f));
            g2.draw(new RoundRectangle2D.Double(px + 0.7, py + 0.7, ps - 1.4, ps - 1.4, 5, 5));
        }

        g2.dispose();
    }
}