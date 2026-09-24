package com.mycompany.lab2;

import UI.Panels.UNI_Portada;
import javax.swing.*;

public class LAB2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Laboratorio #2 - Métodos de Ordenación y Búsqueda");

            UNI_Portada uni_portada = new UNI_Portada();

            ventana.add(uni_portada);

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setLocationRelativeTo(null);

            UI.Elements.Sound.iniciarMusica();

            ventana.setVisible(true);
        });
    }
}