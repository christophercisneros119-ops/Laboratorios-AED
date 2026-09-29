package UI.Panels;

import UI.Elements.FondoAnimado;
import UI.Elements.Navegacion;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class P_Menu extends FondoAnimado {

    private static final String FONDO = "fondo_menu";
    /** Tope de carga, solo un guarda contra un prefijo mal escrito. */
    private static final int MAXIMO_CUADROS = 64;
    /**
     * Ritmo del fondo del menú. Los cuadros salen muestreados cada 2 del video
     * original, así que reproducirlos a 15 fps devuelve la velocidad real y el
     * fondo deja de correr.
     */
    private static final int FPS_FONDO = 15;

    private static final int ANCHO_BOTON = 360;
    private static final int ALTO_BOTON = 64;
    private static final int SEPARACION = 10;

    public P_Menu() {
        super(FONDO, MAXIMO_CUADROS, FPS_FONDO);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        JPanel interior = new JPanel();
        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.setOpaque(false);

        interior.add(texto("Algoritmos recursivos", UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(4));
        interior.add(texto("Cada simulación reproduce su solución paso a paso.",
                UIConstants.FONT_SUBTITULO, UIConstants.TEXTO_SUAVE));
        interior.add(Box.createVerticalStrut(6 * SEPARACION));
        interior.add(boton("Torres de Hanói", new P_Hanoi()));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("Salto de la rana", new P_Rana()));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("8 Reinas", new P_Reinas()));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("QuickSort", new P_QuickSort()));
        interior.add(Box.createVerticalStrut(4 * SEPARACION));
        interior.add(boton("Atrás", new UNI_Portada()));

        add(interior);
    }

    private JButton boton(String texto, JPanel destino) {
        JButton boton = new JButton(texto);
        boton.setFont(UIConstants.FONT_TEXTO);
        boton.setAlignmentX(CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        boton.setMaximumSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        boton.addActionListener(evento -> Navegacion.irA(this, destino));
        return boton;
    }

    static JLabel texto(String contenido, Font fuente, Color color) {
        JLabel etiqueta = new JLabel(contenido);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        etiqueta.setAlignmentX(CENTER_ALIGNMENT);
        return etiqueta;
    }
}
