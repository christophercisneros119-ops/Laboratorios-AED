package UI.Elementos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class SidebarPanel extends GlassPanel {

    public static final int SIDEBAR_WIDTH = 170;

    private final TaskbarCloseButton closeButton = new TaskbarCloseButton();
    private final MenuButton arreglosButton = new MenuButton("Métodos");
    private final MenuButton aboutButton = new MenuButton("Sobre el\nlaboratorio");

    public SidebarPanel() {
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 600));
        setLayout(null);

        closeButton.setBounds(10, 12, 26, 26);
        arreglosButton.setBounds(15, 48, 140, 46);
        aboutButton.setBounds(15, 102, 140, 64);

        add(closeButton);
        add(arreglosButton);
        add(aboutButton);
    }

    public TaskbarCloseButton getCloseButton() {
        return closeButton;
    }

    public MenuButton getArreglosButton() {
        return arreglosButton;
    }

    public MenuButton getAboutButton() {
        return aboutButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(62, 66, 70, 210));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(210, 216, 222, 130));
        g2.drawLine(0, 0, 0, h);

        g2.setColor(new Color(255, 255, 255, 22));
        g2.fillRect(0, 0, w, h / 3);

        g2.dispose();
    }
}