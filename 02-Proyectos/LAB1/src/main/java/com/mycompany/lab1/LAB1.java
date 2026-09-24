

package com.mycompany.lab1;

import UI.Panels.UNI_Portada;
import javax.swing.*;

public class LAB1 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> { // Event dispatch Thread
            JFrame ventana = new JFrame("Laboratorio #1 - Arreglos desordenados y ordenados");

            // La portada gestiona su propia navegacion (sidebar -> Opciones)
            UNI_Portada uni_portada = new UNI_Portada();

            ventana.add(uni_portada);

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setLocationRelativeTo(null);

            ventana.setVisible(true);
        });

    }
}
