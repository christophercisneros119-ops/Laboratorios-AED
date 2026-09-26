package Paneles;

import EjerciciosRecursivos.Ejercicio_2;
import javax.sound.sampled.FloatControl;
import java.awt.Toolkit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class Ejercicio2 extends javax.swing.JPanel {
    
    // ---------- Atributos del Backend y Control ----------
    private final Ejercicio_2 juego = new Ejercicio_2();
    private final JLabel[] piedrasGUI = new JLabel[Ejercicio_2.NUM_PIEDRAS];
    private Timer timerAnimacion;

    // ---------- Imágenes cargadas en memoria ----------
    private Image imgFondo;
    private Image imgPiedra;
    private Image imgRanaVerde;
    private Image imgRanaCafe;

    // ---------- Componentes Visuales ----------
    private JPanel pnlNorte;
    private JPanel pnlTablero;
    private JPanel pnlSur;

    private JLabel lblTitulo;
    private JLabel lblInfo;

    private JButton btnReiniciar;
    private JButton btnSiguiente;
    private JButton btnAuto;

    /**
     * Creates new form Ejercicio2
     */
    public Ejercicio2() {
        initComponents();
        cargarRecursosImagenes();
        initCustomComponents();
    }
    
    /**
     * Carga las imágenes desde el paquete /imagenes/
     */
    private void cargarRecursosImagenes() {
        imgFondo = obtenerImagen("/imagenes/fondo.png");
        imgPiedra = obtenerImagen("/imagenes/piedra.png");
        imgRanaVerde = obtenerImagen("/imagenes/rana_verde.png");
        imgRanaCafe = obtenerImagen("/imagenes/rana_cafe.png");
    }

    private Image obtenerImagen(String ruta) {
        URL resource = getClass().getResource(ruta);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            System.err.println("No se pudo cargar la imagen en la ruta: " + ruta);
            return null;
        }
    }

    /**
     * Renderiza la imagen de fondo escalada a todo el tamaño del JPanel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgFondo != null) {
            g.drawImage(imgFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(220, 230, 242));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Configura la interfaz responsiva y conecta los eventos.
     */
    private void initCustomComponents() {
        this.setLayout(new BorderLayout(15, 15));
        this.setOpaque(true);

        // ================= 1. PANEL NORTE (TÍTULO E INFORMACIÓN) =================
        pnlNorte = new JPanel(new GridLayout(2, 1, -1, -1));
        pnlNorte.setOpaque(false);

        lblTitulo = new JLabel("Simulación: El Salto de la Rana", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblInfo = new JLabel("Estado inicial. Presione 'Siguiente Paso' o 'Auto' para comenzar.", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfo.setForeground(Color.WHITE);

        pnlNorte.add(lblTitulo);
        pnlNorte.add(lblInfo);
        this.add(pnlNorte, BorderLayout.NORTH);

        // ================= 2. PANEL CENTRO (TABLERO RESPONSIVO) =================
        pnlTablero = new JPanel(new GridLayout(1, Ejercicio_2.NUM_PIEDRAS, 10, 0));
        pnlTablero.setOpaque(false);

        for (int i = 0; i < Ejercicio_2.NUM_PIEDRAS; i++) {
            JLabel lblPiedra = new JLabel();
            lblPiedra.setHorizontalAlignment(SwingConstants.CENTER);
            lblPiedra.setVerticalAlignment(SwingConstants.CENTER);
            lblPiedra.setOpaque(false);

            piedrasGUI[i] = lblPiedra;
            pnlTablero.add(lblPiedra);
        }
        this.add(pnlTablero, BorderLayout.CENTER);

        // ================= 3. PANEL SUR (CONTROLES) =================
        pnlSur = new JPanel();
        pnlSur.setOpaque(false);

        btnReiniciar = new JButton("Reiniciar");
        btnSiguiente = new JButton("Siguiente Paso");
        btnAuto = new JButton("Auto");

        Font fontBotones = new Font("Segoe UI", Font.BOLD, 14);
        btnReiniciar.setFont(fontBotones);
        btnSiguiente.setFont(fontBotones);
        btnAuto.setFont(fontBotones);

        pnlSur.add(btnReiniciar);
        pnlSur.add(btnSiguiente);
        pnlSur.add(btnAuto);
        this.add(pnlSur, BorderLayout.SOUTH);

        // ================= 4. TIMER Y EVENTOS =================
        timerAnimacion = new Timer(750, e -> avanzarPaso());

        btnSiguiente.addActionListener(e -> avanzarPaso());

        btnReiniciar.addActionListener(e -> reiniciarJuego());

        btnAuto.addActionListener(e -> {
            if (timerAnimacion.isRunning()) {
                timerAnimacion.stop();
                btnAuto.setText("Auto");
            } else {
                if (juego.isResuelto()) {
                    reiniciarJuego();
                }
                timerAnimacion.start();
                btnAuto.setText("Pausar");
            }
        });

        // Evento para reescalar imágenes al cambiar el tamaño de la ventana
        pnlTablero.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarVista();
            }
        });

        reiniciarJuego();
    }

    private void reiniciarJuego() {
        if (timerAnimacion.isRunning()) {
            timerAnimacion.stop();
            btnAuto.setText("Auto");
        }
        juego.reiniciar();
        lblInfo.setText("Estado inicial. Presione 'Siguiente Paso' o 'Auto' para comenzar.");
        actualizarVista();
    }

    private void avanzarPaso() {
        if (!juego.isResuelto()) {
            Ejercicio_2.Movimiento m = juego.aplicarSiguienteMovimiento();
            if (m != null) {
                lblInfo.setText(m.getDescripcion(juego.getIndicePaso()));
                reproducirSonidoCroac();
                actualizarVista();
            }
        } else {
            timerAnimacion.stop();
            btnAuto.setText("Auto");
            lblInfo.setText("¡Solución alcanzada con éxito en " + juego.getTotalMovimientos() + " pasos!");
        }
    }
    
    /**
     * Genera dinámicamente el efecto de sonido "croac" (gutural) en tiempo real.
     */
    private void reproducirSonidoCroac() {
        new Thread(() -> {
            try {
                float sampleRate = 44100f;
                double duration = 0.22; // 220 ms
                int numSamples = (int) (duration * sampleRate);
                
                byte[] buffer = new byte[numSamples * 2];

                for (int i = 0; i < numSamples; i++) {
                    double t = (double) i / sampleRate;

                    // Tono base grave descendente (220 Hz a 130 Hz)
                    double freq = 220 - (90 * (t / duration));

                    // Modulación de textura rasposa
                    double croakModulation = 0.4 + 0.6 * Math.sin(2.0 * Math.PI * 45 * t);

                    // Envolvente de volumen
                    double envelope = Math.sin(Math.PI * (t / duration));

                    // Generación de onda con multiplicador de presencia
                    double sample = Math.sin(2.0 * Math.PI * freq * t) * croakModulation * envelope;

                    // Potenciamos la ganancia digital y recortamos excesos para evitar distorsión dura
                    int val = (int) (sample * 65000.0);
                    val = Math.max(-32767, Math.min(32767, val));

                    short sample16 = (short) val;

                    // Guardar en Little-Endian (16 bits)
                    buffer[2 * i] = (byte) (sample16 & 0xFF);
                    buffer[2 * i + 1] = (byte) ((sample16 >> 8) & 0xFF);
                }

                AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    Toolkit.getDefaultToolkit().beep();
                    return;
                }

                try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                    line.open(format);

                    // Maximiza el volumen en la tarjeta de sonido a nivel de Master Gain
                    if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(gainControl.getMaximum());
                    }

                    line.start();
                    line.write(buffer, 0, buffer.length);
                    line.drain();
                }
            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    /**
     * Mapea el estado del backend y genera la composición visual (Piedra + Rana).
     */
    private void actualizarVista() {
        char[] estado = juego.getEstadoActual();

        for (int i = 0; i < estado.length; i++) {
            char c = estado[i];
            Image ranaActual = null;

            if (c == Ejercicio_2.VERDE) {
                ranaActual = imgRanaVerde;
            } else if (c == Ejercicio_2.CAFE) {
                ranaActual = imgRanaCafe;
            }

            int w = piedrasGUI[i].getWidth();
            int h = piedrasGUI[i].getHeight();

            if (w > 0 && h > 0) {
                ImageIcon iconoCompuesto = crearIconoCompuesto(imgPiedra, ranaActual, w, h);
                piedrasGUI[i].setIcon(iconoCompuesto);
            }
        }
        repaint();
    }

    /**
     * Combina la imagen de la piedra y la rana en una sola imagen en memoria
     * con renderizado suavizado.
     */
    private ImageIcon crearIconoCompuesto(Image piedra, Image rana, int width, int height) {
        BufferedImage combo = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combo.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 1. Calcula un tamaño cuadrado usando el 60% del lado más pequeño
        int tamano = (int) (Math.min(width, height) * 0.60);
        tamano = Math.min(tamano, 130); // Limite máximo de 130px

        // 2. Calcula las coordenadas x e y para centrar la imagen dentro de la celda
        int x = (width - tamano) / 2;
        int y = (height - tamano) / 2;

        // 3. Dibuja la piedra centrada
        if (piedra != null) {
        g2d.drawImage(piedra, x, y, tamano, tamano, null);
        }

        // 4. Dibuja la rana centrada con un ligero ajuste vertical hacia arriba
        if (rana != null) {
        int yRana = y - (int) (tamano * 0.08);
        g2d.drawImage(rana, x, yRana, tamano, tamano, null);
        }
        g2d.dispose();
        return new ImageIcon(combo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
