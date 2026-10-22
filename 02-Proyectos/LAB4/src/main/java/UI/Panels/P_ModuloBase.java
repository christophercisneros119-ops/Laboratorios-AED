package UI.Panels;

import UI.Elements.BotonEstilizado;
import UI.Elements.Lienzo;
import UI.Elements.Navegacion;
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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Estructura comun de los modulos: titulo, instrucciones, fila de operaciones,
 * aviso en linea, lienzo y pie con Regresar y estado. Cada modulo aporta sus
 * controles y su forma de pintar. A diferencia del LAB3 no hay reproduccion
 * automatica: el usuario opera la estructura a mano.
 */
public abstract class P_ModuloBase extends JPanel {

    private final JPanel cabecera = new JPanel();
    private final JPanel pie;
    /** Un espacio vacio reserva el alto de la linea de avisos sin mostrar nada. */
    private final JLabel lblAviso = new JLabel(" ");
    private final JLabel lblEstado = new JLabel(" ");
    private final Lienzo lienzo;
    private final Color acento;

    protected P_ModuloBase(String titulo, String instrucciones, Color acento) {
        this.acento = acento;
        lienzo = new Lienzo(this::pintarLienzo, UIConstants.FONDO);

        BotonEstilizado btnAtras = BotonEstilizado.textoAjustado("Regresar",
                UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
        btnAtras.addActionListener(evento -> Navegacion.irA(this, new P_Menu()));

        // El Reiniciar es una accion del ejercicio, no navegacion: por eso
        // va en gris junto al Regresar celeste.
        BotonEstilizado btnReiniciar = BotonEstilizado.textoAjustado("Reiniciar",
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        btnReiniciar.addActionListener(evento -> {
            reiniciar();
            limpiarAviso();
            refrescar();
        });

        setLayout(new BorderLayout(0, UIConstants.MARGEN / 2));
        setBackground(UIConstants.FONDO);
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setOpaque(false);
        cabecera.add(etiqueta(titulo, UIConstants.FONT_TITULO, UIConstants.TEXTO));
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(etiqueta(instrucciones, UIConstants.FONT_SUBTITULO,
                UIConstants.TEXTO_SUAVE));
        add(cabecera, BorderLayout.NORTH);

        lblAviso.setFont(UIConstants.FONT_SUBTITULO);
        lblAviso.setForeground(UIConstants.ERROR);
        lblAviso.setAlignmentX(LEFT_ALIGNMENT);

        lblEstado.setFont(UIConstants.FONT_TEXTO);
        lblEstado.setForeground(UIConstants.TEXTO_SUAVE);

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        izquierda.setOpaque(false);
        izquierda.add(btnAtras);
        izquierda.add(btnReiniciar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        derecha.setOpaque(false);
        derecha.add(lblEstado);

        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.add(izquierda, BorderLayout.WEST);
        pie.add(derecha, BorderLayout.EAST);
        this.pie = pie;

        add(lienzo, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
    }

    /** Fila de operaciones del modulo, con los campos y botones que opere. */
    protected final void addOperaciones(JPanel fila) {
        fila.setOpaque(false);
        fila.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.add(Box.createVerticalStrut(10));
        cabecera.add(fila);
        // La validacion se muestra en linea, nunca en un dialogo modal: con la
        // pantalla completa exclusiva un JDialog puede hacer desaparecer la
        // ventana. La linea siempre se reserva para que el diseno no salte.
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(lblAviso);
    }

    /**
     * Regla corta de uso, en letra chica y gris bajo las instrucciones: dice
     * que escribir y como. Se llama antes de la fila de operaciones.
     */
    protected final void nota(String texto) {
        JLabel lblNota = new JLabel(texto);
        lblNota.setFont(UIConstants.FONT_CHICA);
        lblNota.setForeground(UIConstants.TEXTO_SUAVE);
        lblNota.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.add(Box.createVerticalStrut(4));
        cabecera.add(lblNota);
    }

    /** Aviso de validacion o de error, en linea y en color de error. */
    protected final void avisar(String mensaje) {
        lblAviso.setForeground(UIConstants.ERROR);
        lblAviso.setText(mensaje);
    }

    /** Nota informativa en la misma linea de los avisos, sin color de error. */
    protected final void informar(String mensaje) {
        lblAviso.setForeground(UIConstants.TEXTO_SUAVE);
        lblAviso.setText(mensaje);
    }

    protected final void limpiarAviso() {
        lblAviso.setText(" ");
    }

    /** Texto del estado (Tope, Frente y Final) que acompania al boton Regresar. */
    protected final void estado(String texto) {
        lblEstado.setText(texto);
    }

    /** Repinta el lienzo, se llama despues de cada operacion. */
    protected final void refrescar() {
        lienzo.repaint();
    }

    /** Fila de controles extra al centro del pie, entre Regresar y el estado. */
    protected final void addControlesPie(JPanel fila) {
        fila.setOpaque(false);
        pie.add(fila, BorderLayout.CENTER);
    }

    /** Mensaje tenue centrado, para cuando aun no hay nada que pintar. */
    protected final void mensajeCentrado(Graphics2D g2, int ancho, int alto,
                                          String mensaje) {
        g2.setFont(UIConstants.FONT_TEXTO);
        FontMetrics medidas = g2.getFontMetrics();
        g2.setColor(UIConstants.TEXTO_SUAVE);
        g2.drawString(mensaje, (ancho - medidas.stringWidth(mensaje)) / 2, alto / 2);
    }

    /** Rotulo de las filas de operaciones. */
    protected final JLabel rotulo(String contenido) {
        JLabel etiqueta = new JLabel(contenido);
        etiqueta.setFont(UIConstants.FONT_TEXTO);
        etiqueta.setForeground(UIConstants.TEXTO);
        return etiqueta;
    }

    /** Boton de accion con la paleta gris comun de los modulos. */
    protected final BotonEstilizado boton(String etiqueta, int ancho) {
        return BotonEstilizado.texto(etiqueta, ancho, UIConstants.GRIS_SUAVE,
                UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
    }

    protected final Color acento() {
        return acento;
    }

    private static JLabel etiqueta(String texto, Font fuente, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        etiqueta.setAlignmentX(LEFT_ALIGNMENT);
        return etiqueta;
    }

    /** Vacia la estructura del modulo para empezar de nuevo. */
    protected abstract void reiniciar();

    /** Dibuja el escenario del modulo con el tamano real del lienzo. */
    protected abstract void pintarLienzo(Graphics2D g2, int ancho, int alto);
}
