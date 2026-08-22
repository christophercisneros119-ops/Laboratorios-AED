package UI;

import javax.swing.*;
import java.awt.*;

public class LAB1DESOR {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Laboratorio #1");

            // Instanciaciones
            UNI_Portada uni_portada = new UNI_Portada();
            Opciones_panel2 opciones = new Opciones_panel2();

            // Contenedor principal con CardLayout
            CardLayout layout = new CardLayout();
            JPanel contenedor = new JPanel(layout);

            contenedor.add(uni_portada, "Portada");
            contenedor.add(opciones, "Opciones");

            // Agrega el contenedor a la ventana
            ventana.add(contenedor);

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800,600); 
            ventana.setLocationRelativeTo(null);

            ventana.setVisible(true);
        });

    }
}