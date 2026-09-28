package UI.Panels;

import UI.Elements.FondoAnimado;
import UI.Elements.Navegacion;
import UI.Elements.UIConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Portada provisional. Los nombres van como texto porque las fotos todavia no
 * existen; cuando lleguen se reemplazan por los tiles con imagen. El fondo es un
 * video partido en cuadros, como el de QuickSort.
 */
public class UNI_Portada extends FondoAnimado {

    private static final String[] INTEGRANTES = {
        "Janelly Romero  ·  2025-1905U",
        "Moises Alemán  ·  2025-2560U",
        "Christopher Cisneros  ·  2025-0032U"
    };

    /** Prefijo de los cuadros del fondo animado: fondo_portada_00.png y siguientes. */
    private static final String FONDO = "fondo_portada";
    /** Tope de carga, solo un guarda contra un prefijo mal escrito. */
    private static final int MAXIMO_CUADROS = 64;

    public UNI_Portada() {
        super(FONDO, MAXIMO_CUADROS, UIConstants.CUADROS_POR_SEGUNDO);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(UIConstants.MARGEN, UIConstants.MARGEN,
                UIConstants.MARGEN, UIConstants.MARGEN));

        JPanel interior = new JPanel();
        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.setOpaque(false);

        interior.add(P_Menu.texto("UNIVERSIDAD NACIONAL DE INGENIERÍA",
                UIConstants.FONT_SUBTITULO, UIConstants.TEXTO_SUAVE));
        interior.add(Box.createVerticalStrut(10));
        interior.add(P_Menu.texto("Algoritmización y Estructuras de Datos",
                UIConstants.FONT_SUBTITULO, UIConstants.TEXTO_SUAVE));
        interior.add(Box.createVerticalStrut(36));
        interior.add(P_Menu.texto("Laboratorio #3", UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(4));
        interior.add(P_Menu.texto("Algoritmos recursivos", UIConstants.FONT_TITULO,
                UIConstants.RESALTADO));
        interior.add(Box.createVerticalStrut(36));
        interior.add(P_Menu.texto("MSc. Eliezer Aburto Plata",
                UIConstants.FONT_TEXTO, UIConstants.TEXTO_SUAVE));
        interior.add(Box.createVerticalStrut(20));
        for (String integrante : INTEGRANTES) {
            interior.add(P_Menu.texto(integrante, UIConstants.FONT_TEXTO, UIConstants.TEXTO));
            interior.add(Box.createVerticalStrut(4));
        }
        interior.add(Box.createVerticalStrut(28));

        JButton btnComenzar = new JButton("Comenzar");
        btnComenzar.setFont(UIConstants.FONT_TEXTO);
        btnComenzar.setPreferredSize(new Dimension(220, 64));
        btnComenzar.setMinimumSize(new Dimension(220, 64));
        btnComenzar.setMaximumSize(new Dimension(220, 64));
        btnComenzar.addActionListener(evento -> Navegacion.irA(this, new P_Menu()));

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);
        fila.add(btnComenzar);
        interior.add(fila);

        add(interior);
    }
}
