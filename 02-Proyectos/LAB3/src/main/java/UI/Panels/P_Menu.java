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

/**
 * Menu del laboratorio: cada algoritmo entra con su propio color de escenario.
 * Los botones al pasar el mouse toman el tono del marco del ejercicio al que
 * llevan (beige de Hanói, celestito de la rana, gris de las reinas y verde
 * oliva de QuickSort), igual que la foto de su simulación.
 */
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
    /** Hueco vertical entre opciones: los botones no van apretados. */
    private static final int SEPARACION = 28;
    /** Separacion del titulo con la primera opcion. */
    private static final int SEPARACION_TITULO = 40;

    private final JLayeredPane capas = new JLayeredPane();
    private final BotonEstilizado btnAtras = BotonEstilizado.textoAjustado("Regresar",
            UIConstants.CELESTE, UIConstants.AZULITO, UIConstants.AZUL_PRESION);
    private final JPanel interior = new JPanel();

    public P_Menu() {
        super(FONDO, MAXIMO_CUADROS, FPS_FONDO, UIConstants.RECORTE_MENU);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.setOpaque(false);

        interior.add(Box.createVerticalGlue());
        interior.add(texto("Algoritmos recursivos", UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(SEPARACION_TITULO));
        interior.add(boton("Torres de Hanói", new P_Hanoi(), UIConstants.FONDO_HANOI));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("Salto de la rana", new P_Rana(), UIConstants.FONDO_RANA));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("8 Reinas", new P_Reinas(), UIConstants.FONDO_REINAS));
        interior.add(Box.createVerticalStrut(SEPARACION));
        interior.add(boton("QuickSort", new P_QuickSort(), UIConstants.FONDO_QUICKSORT));
        // Los dos glues empujan la columna desde arriba y desde abajo: la lista
        // queda al centro vertical de la pantalla en vez de pegada arriba.
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

    /**
     * Centra la lista y ancla el "Regresar" abajo a la izquierda.
     *
     * <p>Se usa la referencia {@code interior}, no un índice: el orden de
     * {@link JLayeredPane#getComponent} sigue el número de capa, así que
     * {@code getComponent(0)} devuelve el botón (PALETTE, capa 100) y no el
     * panel (DEFAULT, capa 300). El cast fallaba y el menú salía vacío.
     *
     * <p>El tamano sale del propio {@code capas}, no del panel: este tiene
     * borde y los hijos viven dentro, así la columna queda centrada de verdad.
     */
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
        // El interior vive dentro de un JLayeredPane (layout null): al cambiarle
        // los bounds hay que pedirle su propio layout a mano, porque Swing solo
        // reparte los hijos de un contenedor cuando la validacion llega desde
        // arriba. Sin esto el BoxLayout no corre y los botones quedan en 0x0.
        interior.doLayout();
        capas.repaint();
    }

    /** Opción del menú: al pasar el mouse toma el color del marco del algoritmo. */
    private BotonEstilizado boton(String etiqueta, JPanel destino, Color marco) {
        BotonEstilizado boton = BotonEstilizado.texto(etiqueta, ANCHO_BOTON,
                UIConstants.GRIS_SUAVE, marco, UIConstants.GRIS_OSCURO);
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