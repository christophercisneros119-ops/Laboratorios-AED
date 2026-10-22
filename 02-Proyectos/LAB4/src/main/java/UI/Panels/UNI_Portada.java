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

/** Portada del laboratorio con las tarjetas de los integrantes. */
public class UNI_Portada extends FondoAnimado {

    private static final String FONDO = "fondo_portada";
    private static final int LADO_TARJETA = 128;

    public UNI_Portada() {
        super(FONDO, UIConstants.MAXIMO_CUADROS, UIConstants.CUADROS_POR_SEGUNDO);
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
        interior.add(P_Menu.texto("Laboratorio #4", UIConstants.FONT_TITULO, UIConstants.TEXTO));
        interior.add(Box.createVerticalStrut(4));
        interior.add(P_Menu.texto("Pilas, colas y notaciones polacas",
                UIConstants.FONT_TITULO, UIConstants.ACENTO));
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
