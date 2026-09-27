package com.mycompany.lab3;

import UI.Elements.UIConstants;
import UI.Panels.UNI_Portada;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LAB3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LAB3::iniciar);
    }

    private static void iniciar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Si el look and feel del sistema no esta disponible se usa el de Java.
        }

        JFrame ventana = new JFrame("Laboratorio #3 · Algoritmos Recursivos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(new UNI_Portada());
        ventana.setMinimumSize(new Dimension(UIConstants.ANCHO_MINIMO, UIConstants.ALTO_MINIMO));
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
