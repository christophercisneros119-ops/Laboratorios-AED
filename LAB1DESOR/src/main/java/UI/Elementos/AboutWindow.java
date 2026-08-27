package UI.Elementos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AboutWindow extends GlassPanel {

    public static final int ABOUT_W = 460;
    public static final int ABOUT_H = 280;

    private static final Color FILL_GREEN = new Color(198, 226, 208);
    private static final Color BORDER_GREEN = new Color(74, 124, 96);
    private static final Color DARK_TEXT = new Color(18, 40, 32);

    private static final int MARGIN_X = 34;
    private static final int BODY_TOP = 66;
    private static final int BODY_BOTTOM_GAP = 64;

    private static final String TEXTO = "Esta es una app de estructuras de datos desarrollada "
            + "en Java Swing con el objetivo de gestionar y comparar operaciones de "
            + "inserci\u00f3n, baja y modificaci\u00f3n en arreglos ordenados y desordenados "
            + "mediante una interfaz visual modular e intuitiva.";

    private final MenuButton regresarButton = new MenuButton("Regresar",
            new Color(236, 238, 239),
            new Color(38, 42, 46),
            new Color(160, 168, 174));

    public AboutWindow() {
        setSize(ABOUT_W, ABOUT_H);
        setOpaque(false);
        setLayout(null);

        JLabel titulo = new JLabel("Sobre el laboratorio", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(DARK_TEXT);
        titulo.setBounds(20, 26, ABOUT_W - 40, 30);
        add(titulo);

        regresarButton.setBounds((ABOUT_W - 130) / 2, ABOUT_H - 56, 130, 40);
        add(regresarButton);
    }

    public MenuButton getRegresarButton() {
        return regresarButton;
    }

    private List<String> wrapTexto(FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder actual = new StringBuilder();

        for (String palabra : TEXTO.split("\\s+")) {
            String candidato = actual.length() == 0 ? palabra : actual + " " + palabra;
            if (fm.stringWidth(candidato) <= maxWidth) {
                actual.setLength(0);
                actual.append(candidato);
            } else {
                if (actual.length() > 0) {
                    lines.add(actual.toString());
                }
                actual.setLength(0);
                actual.append(palabra);
            }
        }
        if (actual.length() > 0) {
            lines.add(actual.toString());
        }
        return lines;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = 10;

        g2.setColor(new Color(0, 12, 10, 110));
        g2.fill(new RoundRectangle2D.Double(3, 6, w - 6, h - 7, arc, arc));

        RoundRectangle2D marco = new RoundRectangle2D.Double(0, 0, w - 1, h - 1, arc, arc);
        g2.setColor(FILL_GREEN);
        g2.fill(marco);

        g2.setClip(marco);
        LinearGradientPaint brillo = new LinearGradientPaint(
                0, 0, 0, h * 0.45f,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 255, 255, 70), new Color(255, 255, 255, 0)});
        g2.setPaint(brillo);
        g2.fillRect(0, 0, w, h);
        g2.setClip(null);

        g2.setColor(BORDER_GREEN);
        g2.setStroke(new BasicStroke(1.6f));
        g2.draw(new RoundRectangle2D.Double(0.75, 0.75, w - 1.5, h - 1.5, arc, arc));

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        FontMetrics fm = g2.getFontMetrics();
        int maxWidth = w - MARGIN_X * 2;
        List<String> lines = wrapTexto(fm, maxWidth);

        int lineHeight = fm.getHeight();
        int areaAlto = h - BODY_TOP - BODY_BOTTOM_GAP;
        int y = BODY_TOP + Math.max(0, (areaAlto - lineHeight * lines.size()) / 2) + fm.getAscent();

        g2.setColor(DARK_TEXT);
        for (String line : lines) {
            int x = (w - fm.stringWidth(line)) / 2;
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        g2.dispose();
    }
}
