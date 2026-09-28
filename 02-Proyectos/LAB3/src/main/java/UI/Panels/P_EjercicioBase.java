package UI.Panels;

import UI.Elements.Fuentes;
import UI.Elements.Lienzo;
import UI.Elements.Navegacion;
import UI.Elements.Reproductor;
import UI.Elements.UIConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Estructura comun de los cuatro ejercicios: titulo, zona de ingreso opcional,
 * lienzo y controles. Cada ejercicio solo aporta su estado y su forma de pintar.
 */
public abstract class P_EjercicioBase extends JPanel {

    private final JPanel cabecera = new JPanel();
    private final JLabel lblContador = new JLabel();
    /** Un espacio vacio reserva el alto de la linea de avisos sin mostrar nada. */
    private final JLabel lblAviso = new JLabel(" ");
    private final JButton btnIniciar = new JButton("Iniciar");
    private final Lienzo lienzo = new Lienzo(this::pintarLienzo);
    private final Reproductor reproductor = new Reproductor(this::totalPasos, this::avanzar,
            duracionPaso());
    private Timer animacion;
    private long inicioAnimacion;
    private int intervaloAnimacion;

    protected P_EjercicioBase(String titulo, String instrucciones) {
        setLayout(new BorderLayout(0, UIConstants.MARGEN / 2));
        setBackground(UIConstants.FONDO);
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setOpaque(false);
        cabecera.add(etiqueta(titulo, UIConstants.FONT_TITULO, UIConstants.TEXTO));
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(etiqueta(instrucciones, UIConstants.FONT_SUBTITULO, UIConstants.TEXTO_SUAVE));
        add(cabecera, BorderLayout.NORTH);

        lblAviso.setFont(UIConstants.FONT_SUBTITULO);
        lblAviso.setForeground(UIConstants.TEXTO_SUAVE);
        lblAviso.setAlignmentX(LEFT_ALIGNMENT);

        add(lienzo, BorderLayout.CENTER);
        add(construirControles(), BorderLayout.SOUTH);
    }

    private static JLabel etiqueta(String texto, java.awt.Font fuente, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        etiqueta.setAlignmentX(LEFT_ALIGNMENT);
        return etiqueta;
    }

    private JPanel construirControles() {
        JButton btnAtras = new JButton("Atrás");
        JButton btnReiniciar = new JButton("Reiniciar");
        btnAtras.setFont(UIConstants.FONT_TEXTO);
        btnReiniciar.setFont(UIConstants.FONT_TEXTO);
        btnIniciar.setFont(UIConstants.FONT_TEXTO);
        btnAtras.addActionListener(evento -> Navegacion.irA(this, new P_Menu()));
        btnIniciar.addActionListener(evento -> alternar());
        btnReiniciar.addActionListener(evento -> reiniciar());

        lblContador.setFont(UIConstants.FONT_TEXTO);
        lblContador.setForeground(UIConstants.TEXTO_SUAVE);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        fila.setOpaque(false);
        fila.add(btnIniciar);
        fila.add(btnReiniciar);
        fila.add(lblContador);
        fila.add(Box.createHorizontalStrut(24));
        fila.add(btnAtras);
        return fila;
    }

    /** Fila de ingreso opcional, la agrega el ejercicio que necesite datos. */
    protected final void addConfiguracion(JPanel fila) {
        fila.setOpaque(false);
        fila.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.add(Box.createVerticalStrut(8));
        cabecera.add(fila);
        // La validacion se muestra en linea, nunca en un dialogo modal: con la
        // pantalla completa exclusiva un JDialog puede hacer desaparecer la
        // ventana. La linea siempre se reserva para que el diseno no salte.
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(lblAviso);
    }

    /**
     * Avisa un dato invalido sin abrir dialogo alguno. Reemplaza al
     * JOptionPane: un dialogo modal sobre la ventana en pantalla completa
     * exclusiva hace que Windows la oculte y la app parece cerrarse.
     */
    protected final void avisar(String mensaje) {
        lblAviso.setForeground(UIConstants.ERROR);
        lblAviso.setText(mensaje);
    }

    private void limpiarAviso() {
        lblAviso.setText(" ");
    }

    private void alternar() {
        if (reproductor.terminado()) {
            reiniciar();
        }
        reproductor.alternar();
        actualizarEstado();
    }

    public final void reiniciar() {
        reproductor.reiniciar();
        if (!preparar()) {
            return;
        }
        limpiarAviso();
        lienzo.repaint();
        actualizarEstado();
    }

    private void avanzar() {
        aplicarPaso(reproductor.paso());
        lienzo.repaint();
        actualizarEstado();
    }

    /**
     * Los paneles llaman esto al final de su constructor, ya con su estado
     * inicial preparado, para que la pantalla abra completa y no a medias.
     */
    protected final void refrescar() {
        actualizarEstado();
    }

    private void actualizarEstado() {
        int total = reproductor.total();
        boolean listo = reproductor.terminado();
        btnIniciar.setText(reproductor.activo() ? "Pausa" : (listo ? "Repetir" : "Iniciar"));
        if (total <= 0) {
            lblContador.setText("No hay pasos que mostrar");
        } else if (listo) {
            lblContador.setText("Listo · " + total + " pasos");
        } else {
            lblContador.setText("Paso " + (reproductor.paso() + 1) + " / " + total);
        }
    }

    /**
     * Repinta el lienzo a ritmo fijo, para que un fondo de varios cuadros se vea
     * moverse aunque el algoritmo este quieto.
     *
     * <p>Solo dispara el repintado: que cuadro toca lo decide el panel con
     * {@link #cuadroActual(int)}, para que el fondo y las piezas avancen siempre
     * en el mismo paint.
     */
    protected final void animar(int cuadrosPorSegundo) {
        if (animacion != null) {
            return;
        }
        inicioAnimacion = System.currentTimeMillis();
        intervaloAnimacion = Math.max(1000 / Math.max(cuadrosPorSegundo, 1), 1);
        animacion = new Timer(intervaloAnimacion, evento -> lienzo.repaint());
        animacion.start();
    }

    /**
     * Cuadro que toca del fondo animado.
     *
     * <p>Se deriva del reloj y no de un contador que se sume en cada paint: asi el
     * fondo no se acelera si Swing agrupa varios repintados, y tampoco salta si
     * falta alguno.
     */
    protected final int cuadroActual(int total) {
        if (total <= 0) {
            return 0;
        }
        long transcurrido = System.currentTimeMillis() - inicioAnimacion;
        return (int) (transcurrido / intervaloAnimacion) % total;
    }

    // Al salir de la ventana el temporizador debe morir, si no sigue
    // repintando un panel que ya no esta en pantalla.
    @Override
    public void removeNotify() {
        super.removeNotify();
        reproductor.detener();
        if (animacion != null) {
            animacion.stop();
        }
    }

    protected static void caja(Graphics2D g2, int x, int y, int ancho, int alto,
                               int radio, Color relleno, Color borde) {
        g2.setColor(relleno);
        g2.fillRoundRect(x, y, ancho, alto, radio, radio);
        g2.setColor(borde);
        g2.drawRoundRect(x, y, ancho, alto, radio, radio);
    }

    protected static JLabel rotulo(String contenido) {
        JLabel etiqueta = new JLabel(contenido);
        etiqueta.setFont(UIConstants.FONT_TEXTO);
        etiqueta.setForeground(UIConstants.TEXTO_SUAVE);
        return etiqueta;
    }

    protected static void textoCentrado(Graphics2D g2, String texto, int centroX,
                                        int baseY, int alto, Color color) {
        g2.setFont(fuenteNumeros(alto));
        g2.setColor(color);
        FontMetrics medidas = g2.getFontMetrics();
        g2.drawString(texto, centroX - medidas.stringWidth(texto) / 2, baseY);
    }

    /**
     * Dibuja un numero centrado y ARRIBA de un borde (la barra de QuickSort),
     * con el hueco justo para que el nuevo tamano de fuente no tape la pieza.
     */
    protected static void textoEncima(Graphics2D g2, String texto, int centroX,
                                      int bordeArriba, int alto, Color color) {
        g2.setFont(fuenteNumeros(alto));
        g2.setColor(color);
        FontMetrics medidas = g2.getFontMetrics();
        g2.drawString(texto, centroX - medidas.stringWidth(texto) / 2,
                bordeArriba - medidas.getAscent() - 4);
    }

    /**
     * Numeros de la escena, escalados con el lienzo en multiplos de 8 como el
     * resto de la geometria: crecen en pantalla grande y nunca desbordan discos
     * o barras en una ventana chica.
     */
    protected static Font fuenteNumeros(int alto) {
        int tamano = Math.max(UIConstants.NUMERO_MINIMO,
                Math.min(UIConstants.NUMERO_MAXIMO, alto / 28));
        return Fuentes.obtener(true, tamano);
    }

    /** Valida los datos ingresados, calcula la solucion y reinicia el estado. */
    protected abstract boolean preparar();

    protected abstract int totalPasos();

    /**
     * Duracion de cada paso en milisegundos. Por defecto la comun de todos los
     * ejercicios; el que necesite otro ritmo la sobreescribe. Va aparte de la
     * geometria porque alimenta el temporizador y la fase del paso.
     */
    protected int duracionPaso() {
        return UIConstants.DURACION_PASO;
    }

    protected abstract void aplicarPaso(int indice);

    /**
     * Dibuja el escenario y las piezas. La geometria sale siempre del tamano
     * real del lienzo, aqui es donde despues se dibuja la imagen de fondo y la
     * de cada pieza usando las mismas coordenadas.
     */
    protected abstract void pintarLienzo(Graphics2D g2, int ancho, int alto);
}
