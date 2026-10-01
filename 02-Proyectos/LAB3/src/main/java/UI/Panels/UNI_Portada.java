package UI.Panels;

import UI.Elements.BotonEstilizado;
import UI.Elements.FondoAnimado;
import UI.Elements.Navegacion;
import UI.Elements.TarjetaMiembro;
import UI.Elements.UIConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Portada del laboratorio. Los integrantes salen como tarjetas cuadradas con
 * su foto (marco de pixel art) en fila. Si una foto falta, la tarjeta
 * muestra las iniciales y la fila se mantiene.
 */
public class UNI_Portada extends FondoAnimado {

    /** Prefijo de los cuadros del fondo animado: fondo_portada_00.png y siguientes. */
    private static final String FONDO = "fondo_portada";
    /** Tope de carga, solo un guarda contra un prefijo mal escrito. */
    private static final int MAXIMO_CUADROS = 64;
    /** Lado de cada tarjeta de integrante, en pixeles. */
    private static final int LADO_TARJETA = 128;
    /**
     * Ritmo de la portada. Los cuadros salen muestreados cada 2 del video
     * original (~0,07 s reales), asi que reproducirlos a 15 fps devuelve la
     * velocidad real y el fondo deja de correr.
     */
    private static final int FPS_FONDO = 15;

    public UNI_Portada() {
        super(FONDO, MAXIMO_CUADROS, FPS_FONDO);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        JPanel interior = new JPanel();
        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.setOpaque(false);

        interior.add(P_Menu.texto("UNIVERSIDAD NACIONAL DE INGENIERÍA",
                UIConstants.FONT_UNI, UIConstants.ACENTO));
        interior.add(Box.createVerticalGlue());
        interior.add(Box.createVerticalGlue());
        interior.add(Box.createVerticalStrut(10));
        interior.add(P_Menu.texto("Algoritmización y Estructuras de Datos",
                UIConstants.FONT_TITULO, UIConstants.NEGRO_PURO));
        interior.add(Box.createVerticalStrut(22));
        interior.add(P_Menu.texto("Laboratorio #3", UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(4));
        interior.add(P_Menu.texto("Algoritmos recursivos", UIConstants.FONT_TITULO,
                UIConstants.ACENTO));
        interior.add(Box.createVerticalStrut(18));
        interior.add(P_Menu.texto("MSc. Eliezer Aburto Plata",
                UIConstants.FONT_TEXTO, UIConstants.TEXTO_SUAVE));
        interior.add(Box.createVerticalGlue());
        interior.add(filaIntegrantes());
        interior.add(Box.createVerticalStrut(12));

        BotonEstilizado btnComenzar = BotonEstilizado.textoAjustado("Comenzar",
                UIConstants.BLANCO_HUESO, UIConstants.LILA_SUAVE,
                UIConstants.AMARILLO_PASTEL);
        btnComenzar.addActionListener(evento -> Navegacion.irA(this, new P_Menu()));

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);
        fila.add(btnComenzar);
        interior.add(fila);

        GridBagConstraints limites = new GridBagConstraints();
        limites.gridx = 0;
        limites.gridy = 0;
        limites.weightx = 1.0;
        limites.weighty = 1.0;
        limites.fill = GridBagConstraints.BOTH;
        limites.anchor = GridBagConstraints.PAGE_START;
        add(interior, limites);
    }

    private JPanel filaIntegrantes() {
        JPanel[] celdas = {
                celda("janelly", "JR", "Janelly Romero", "2025-1905U"),
                celda("moises", "MA", "Moisés Alemán", "2025-2560U"),
                celda("christopher", "CC", "Christopher Cisneros", "2025-0032U"),
        };
        int ancho = 0;
        for (JPanel celda : celdas) {
            ancho = Math.max(ancho, celda.getPreferredSize().width);
        }
        for (JPanel celda : celdas) {
            Dimension tamano = celda.getPreferredSize();
            tamano.width = ancho;
            celda.setPreferredSize(tamano);
            celda.setMaximumSize(tamano);
        }
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);
        for (JPanel celda : celdas) {
            fila.add(celda);
        }
        return fila;
    }

    private JPanel celda(String foto, String iniciales, String nombre, String carnet) {
        JPanel celda = new JPanel();
        celda.setLayout(new BoxLayout(celda, BoxLayout.Y_AXIS));
        celda.setOpaque(false);
        celda.add(new TarjetaMiembro(foto, iniciales, LADO_TARJETA));
        celda.add(Box.createVerticalStrut(10));
        celda.add(P_Menu.texto(nombre, UIConstants.FONT_TEXTO, UIConstants.TEXTO));
        celda.add(Box.createVerticalStrut(4));
        celda.add(P_Menu.texto(carnet, UIConstants.FONT_SUBTITULO, UIConstants.TEXTO_SUAVE));
        return celda;
    }
}