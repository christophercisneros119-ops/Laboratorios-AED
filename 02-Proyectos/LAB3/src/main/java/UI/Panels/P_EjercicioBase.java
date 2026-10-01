package UI.Panels;

import UI.Elements.BotonEstilizado;
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
import javax.swing.JComboBox;
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
    private final BotonEstilizado btnIniciar;
    private final BotonEstilizado btnReiniciar;
    private final Lienzo lienzo;
    private final BotonEstilizado btnAtras;
    private final JPanel centro = new JPanel(new BorderLayout());
    private final Reproductor reproductor = new Reproductor(this::totalPasos, this::avanzar,
            UIConstants.DURACION_PASO);
    private final Color colorMarco;
    private final Color colorTexto;
    private final Color colorSubtexto;
    private Timer animacion;
    private long inicioAnimacion;
    private int intervaloAnimacion;
    private JComboBox<String> velocidad;

    protected P_EjercicioBase(String titulo, String instrucciones, Color colorFondo) {
        this(titulo, instrucciones, colorFondo, UIConstants.TEXTO, UIConstants.TEXTO_SUAVE);
    }

    /**
     * Variante que deja elegir el color de los textos: sobre el marco beige de
     * Hanói, las letras claras no tienen contraste, asi que ese panel pasa su
     * cafe oscuro.
     */
    protected P_EjercicioBase(String titulo, String instrucciones, Color colorFondo,
                              Color colorTexto, Color colorSubtexto) {
        this.colorMarco = colorFondo;
        this.colorTexto = colorTexto;
        this.colorSubtexto = colorSubtexto;
        lienzo = new Lienzo(this::pintarLienzo, colorFondo);
        // Los tres botones de control comparten la misma paleta gris en los
        // cuatro ejercicios: reposo claro, hover oscuro, clic aun mas oscuro.
        btnIniciar = BotonEstilizado.texto("Iniciar", 150,
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        btnReiniciar = BotonEstilizado.texto("Reiniciar", 170,
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        // El Atras es un boton de texto en la fila de controles, no una flecha
        // gigante flotando sobre la foto del escenario.
        btnAtras = BotonEstilizado.textoAjustado("Regresar",
                UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
        btnAtras.addActionListener(evento -> Navegacion.irA(this, new P_Menu()));

        setLayout(new BorderLayout(0, UIConstants.MARGEN / 2));
        setBackground(colorFondo);
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setOpaque(false);
        cabecera.add(etiqueta(titulo, UIConstants.FONT_TITULO, colorTexto));
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(etiqueta(instrucciones, UIConstants.FONT_SUBTITULO, colorSubtexto));
        add(cabecera, BorderLayout.NORTH);

        lblAviso.setFont(UIConstants.FONT_SUBTITULO);
        lblAviso.setForeground(UIConstants.ERROR);
        lblAviso.setAlignmentX(LEFT_ALIGNMENT);

        centro.setOpaque(false);
        centro.add(lienzo, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
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
        btnReiniciar.addActionListener(evento -> reiniciar());
        btnIniciar.addActionListener(evento -> alternar());

        lblContador.setFont(UIConstants.FONT_TEXTO);
        lblContador.setForeground(colorSubtexto);

        // El Regresar vive solo a la izquierda: si va en la misma fila centrada
        // con Iniciar/Reiniciar se confunde con los botones de la ejecucion.
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        izquierda.setOpaque(false);
        izquierda.add(btnAtras);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        fila.setOpaque(false);
        fila.add(btnIniciar);
        fila.add(btnReiniciar);
        fila.add(lblContador);

        JPanel controles = new JPanel(new BorderLayout(12, 0));
        controles.setOpaque(false);
        controles.add(izquierda, BorderLayout.WEST);
        controles.add(fila, BorderLayout.CENTER);
        return controles;
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

    /** Selector de velocidad compartido (1 Lenta / 2 Media / 3 Rapida). */
    protected final JComboBox<String> velocidad() {
        if (velocidad == null) {
            velocidad = new JComboBox<>(UIConstants.VELOCIDADES);
            velocidad.setSelectedIndex(UIConstants.VELOCIDAD_MEDIA);
            velocidad.setFont(UIConstants.FONT_TEXTO);
            velocidad.addActionListener(evento -> {
                reproductor.cambiarRitmo(duracionPaso());
                iniciarPasoSiCorre();
            });
        }
        return velocidad;
    }

    /**
     * Si el combo cambia el ritmo con la reproduccion en curso, se reinicia la
     * temporizacion del paso en vuelo para que la nueva velocidad aplique ya.
     */
    private void iniciarPasoSiCorre() {
        if (!reproductor.activo()) {
            return;
        }
        reproductor.detener();
        reproductor.iniciar();
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
        if (total <= 0) {
            // Sin pasos (aun no se generaron valores) no ofrece Repetir.
            btnIniciar.setText("Iniciar");
            lblContador.setText("No hay pasos que mostrar");
        } else {
            btnIniciar.setText(reproductor.activo() ? "Pausa" : (listo ? "Repetir" : "Iniciar"));
            lblContador.setText(listo
                    ? "Listo · " + total + (total == 1 ? " paso" : " pasos")
                    : "Paso " + (reproductor.paso() + 1) + " / " + total);
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

    protected JLabel rotulo(String contenido) {
        JLabel etiqueta = new JLabel(contenido);
        etiqueta.setFont(UIConstants.FONT_TEXTO);
        etiqueta.setForeground(colorTexto);
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

    /**
     * Duracion de cada paso segun la velocidad elegida. Por defecto la comun de
     * todos los ejercicios (Hanoi, Reinas y QuickSort); el que toque otro ritmo
     * solo cambia {@link #duracionesVelocidades()}.
     */
    protected int[] duracionesVelocidades() {
        return new int[]{1000, 500, 250};
    }

    protected int duracionPaso() {
        int[] duraciones = duracionesVelocidades();
        if (velocidad != null) {
            int indice = velocidad.getSelectedIndex();
            if (indice >= 0 && indice < duraciones.length) {
                return duraciones[indice];
            }
        }
        return UIConstants.DURACION_PASO;
    }

    /** Valida los datos ingresados, calcula la solucion y reinicia el estado. */
    protected abstract boolean preparar();

    protected abstract int totalPasos();

    /** Aplica en el estado dibujado el paso dado de la reproduccion. */
    protected abstract void aplicarPaso(int indice);

    /**
     * Dibuja el escenario y las piezas. La geometria sale siempre del tamano
     * real del lienzo, aqui es donde despues se dibuja la imagen de fondo y la
     * de cada pieza usando las mismas coordenadas.
     */
    protected abstract void pintarLienzo(Graphics2D g2, int ancho, int alto);
}