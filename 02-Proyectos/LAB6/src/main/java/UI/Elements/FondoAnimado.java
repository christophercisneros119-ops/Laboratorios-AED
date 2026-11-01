package UI.Elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FondoAnimado extends JPanel {

    private final List<BufferedImage> cuadros;
    private final Timer animacion;
    private final long inicio;
    private final int intervalo;
    private final double recorte;

    public FondoAnimado(String prefijo, int maximo, int cuadrosPorSegundo) {
        this(prefijo, maximo, cuadrosPorSegundo, 0.0);
    }

    public FondoAnimado(String prefijo, int maximo, int cuadrosPorSegundo, double recorte) {
        this.cuadros = Imagenes.cargarSecuencia(prefijo, maximo);
        this.recorte = recorte;
        setOpaque(true);
        setBackground(UIConstants.FONDO);
        if (cuadros.isEmpty()) {
            this.animacion = null;
            this.inicio = 0;
            this.intervalo = 1;
            return;
        }
        this.inicio = System.currentTimeMillis();
        this.intervalo = Math.max(1000 / Math.max(cuadrosPorSegundo, 1), 1);
        this.animacion = new Timer(intervalo, e -> repaint());
        this.animacion.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        boolean pintado = recorte > 0.0
                ? Imagenes.dibujarSecuenciaRecortada((Graphics2D) g, getWidth(), getHeight(), cuadros, cuadroActual(), recorte)
                : Imagenes.dibujarSecuencia((Graphics2D) g, getWidth(), getHeight(), cuadros, cuadroActual());
        if (!pintado) {
            super.paintComponent(g);
        }
    }

    private int cuadroActual() {
        if (cuadros.isEmpty()) return 0;
        long transcurrido = System.currentTimeMillis() - inicio;
        return (int) (transcurrido / intervalo) % cuadros.size();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (animacion != null) {
            animacion.stop();
        }
    }
}
