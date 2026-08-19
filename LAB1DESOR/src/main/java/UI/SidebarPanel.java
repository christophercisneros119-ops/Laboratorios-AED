package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class SidebarPanel extends GlassPanel {

    public static final int SIDEBAR_WIDTH = 170;

    private final TaskbarCloseButton closeButton = new TaskbarCloseButton();
    private final MenuButton aboutButton = new MenuButton("About this project");
    private final MenuButton codesButton = new MenuButton("Codes");
    private final MenuButton emptyButton = new MenuButton("");

    public SidebarPanel() {
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 600));
        setLayout(null);

        closeButton.setBounds(10, 12, 26, 26);
        aboutButton.setBounds(15, 48, 140, 46);
        codesButton.setBounds(15, 102, 140, 46);
        emptyButton.setBounds(15, 156, 140, 46);

        add(closeButton);
        add(aboutButton);
        add(codesButton);
        add(emptyButton);
    }

    public TaskbarCloseButton getCloseButton() {
        return closeButton;
    }

    public MenuButton getAboutButton() {
        return aboutButton;
    }

    public MenuButton getCodesButton() {
        return codesButton;
    }

    public MenuButton getEmptyButton() {
        return emptyButton;
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