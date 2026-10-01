package UI.Elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel con fondo de video partido en cuadros, como el de QuickSort.
 *
 * <p>Carga la secuencia {@code prefijo_00.png}, {@code prefijo_01.png}... con
 * {@link Imagenes#cargarSecuencia} y la repinta a ritmo fijo derivado del
 * reloj, asi el fondo no se acelera si Swing agrupa repintados. Si no hay
 * cuadros pinta el color plano de siempre.
 */
public class FondoAnimado extends JPanel {

    private final List<BufferedImage> cuadros;
    private final Timer animacion;
    private final long inicio;
    private final int intervalo;
    /** Banda de la esquina inferior derecha que se recorta del cover. */
    private final double recorte;

    protected FondoAnimado(String prefijo, int maximo, int cuadrosPorSegundo) {
        this(prefijo, maximo, cuadrosPorSegundo, 0.0);
    }

    /**
     * Variante con recorte: descarta una banda de la esquina inferior derecha de
     * cada cuadro (el @ del autor del arte), ved {@link Imagenes#dibujarSecuenciaRecortada}.
     */
    protected FondoAnimado(String prefijo, int maximo, int cuadrosPorSegundo,
                           double recorte) {
        cuadros = Imagenes.cargarSecuencia(prefijo, maximo);
        this.recorte = recorte;
        setOpaque(true);
        setBackground(UIConstants.FONDO);
        if (cuadros.isEmpty()) {
            animacion = null;
            inicio = 0;
            intervalo = 1;
            return;
        }
        inicio = System.currentTimeMillis();
        intervalo = Math.max(1000 / Math.max(cuadrosPorSegundo, 1), 1);
        animacion = new Timer(intervalo, evento -> repaint());
        animacion.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        boolean pintado = recorte > 0.0
                ? Imagenes.dibujarSecuenciaRecortada((Graphics2D) g, getWidth(), getHeight(),
                        cuadros, cuadroActual(), recorte)
                : Imagenes.dibujarSecuencia((Graphics2D) g, getWidth(), getHeight(),
                        cuadros, cuadroActual());
        if (!pintado) {
            super.paintComponent(g);
        }
    }

    /** Cuadro que toca, derivado del reloj y no de un contador que se sume. */
    private int cuadroActual() {
        if (cuadros.isEmpty()) {
            return 0;
        }
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