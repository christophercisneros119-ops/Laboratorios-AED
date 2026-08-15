package UI;

import javax.swing.*;

public class LAB1DESOR {

  public static void main(String[] args) {
    
    SwingUtilities.invokeLater(() -> {
        JFrame ventana = new JFrame("Portada");
        UNI_Portada uni_portada = new UNI_Portada();
        ventana.add(uni_portada);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600); 
        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);
  });

  }
}
