package com.mycompany.lab3;

import Paneles.Ejercicio2;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Lab3 { 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Crear el contenedor principal (Ventana JFrame)
            JFrame frame = new JFrame("Simulación - El Salto de la Rana");
            // 2. Configurar que la aplicación finalice al cerrar la ventana
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // 3. Maximizar la ventana para pantallas grandes
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // 4. Insertar el panel gráfico (Ejercicio2) dentro de la ventana
            frame.add(new Ejercicio2());
            // 5. Hacer visible la ventana
            frame.setVisible(true);
        });
    }
}