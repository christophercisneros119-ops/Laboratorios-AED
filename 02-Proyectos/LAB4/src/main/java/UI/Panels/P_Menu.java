package UI.Panels;

import UI.Elements.BotonEstilizado;
import UI.Elements.FondoAnimado;
import UI.Elements.Navegacion;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/** Menu con las cuatro secciones del laboratorio. */
public class P_Menu extends FondoAnimado {

    private static final String FONDO = "fondo_menu";

    private static final int ANCHO_BOTON = 360;
    private static final int SEPARACION = 28;
    private static final int SEPARACION_TITULO = 40;

    private final JLayeredPane capas = new JLayeredPane();
    private final BotonEstilizado btnAtras = BotonEstilizado.textoAjustado("Regresar",
            UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
    private final JPanel interior = new JPanel();

    public P_Menu() {
        super(FONDO, UIConstants.MAXIMO_CUADROS, UIConstants.CUADROS_POR_SEGUNDO,
                UIConstants.RECORTE_MENU);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.setOpaque(false);

        interior.add(Box.createVerticalGlue());
        interior.add(texto("Pilas, colas y notaciones polacas",
                UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(SEPARACION_TITULO));
        interior.add(boton("Pilas", new P_Pilas(), UIConstants.ACENTO_PILA));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("Colas", new P_Colas(), UIConstants.ACENTO_COLA));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("Colas circulares", new P_ColasCirculares(),
                UIConstants.ACENTO_CIRCULAR));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("Notación polaca", new P_NotacionPolaca(),
                UIConstants.ACENTO_POLACA));
        interior.add(Box.createVerticalGlue());

        btnAtras.addActionListener(evento -> Navegacion.irA(this, new UNI_Portada()));

        capas.setOpaque(false);
        capas.add(btnAtras, Integer.valueOf(JLayeredPane.PALETTE_LAYER));
        capas.add(interior, Integer.valueOf(JLayeredPane.DEFAULT_LAYER));
        capas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evento) {
                reubicar();
            }
        });

        GridBagConstraints limites = new GridBagConstraints();
        limites.gridx = 0;
        limites.gridy = 0;
        limites.weightx = 1.0;
        limites.weighty = 1.0;
        limites.fill = GridBagConstraints.BOTH;
        add(capas, limites);
    }

    // Centra la columna y ancla el Regresar abajo a la izquierda.
    private void reubicar() {
        int ancho = capas.getWidth();
        int alto = capas.getHeight();
        if (ancho <= 0 || alto <= 0) {
            return;
        }
        interior.setBounds(0, 0, ancho, alto);
        Dimension tamano = btnAtras.getPreferredSize();
        btnAtras.setBounds(UIConstants.MARGEN,
                alto - tamano.height - UIConstants.MARGEN,
                tamano.width, tamano.height);
        // El interior vive en un JLayeredPane: el layout se le pide a mano.
        interior.doLayout();
        capas.repaint();
    }

    private BotonEstilizado boton(String etiqueta, JPanel destino, Color acento) {
        BotonEstilizado boton = BotonEstilizado.texto(etiqueta, ANCHO_BOTON,
                UIConstants.GRIS_SUAVE, acento, UIConstants.GRIS_OSCURO);
        boton.setAlignmentX(CENTER_ALIGNMENT);
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
