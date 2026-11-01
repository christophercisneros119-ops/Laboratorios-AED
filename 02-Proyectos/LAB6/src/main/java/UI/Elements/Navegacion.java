package UI.Elements;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class Navegacion {

    private Navegacion() {}

    public static void irA(JComponent origen, JPanel destino) {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(origen);
        if (ventana != null) {
            ventana.setContentPane(destino);
            ventana.revalidate();
            ventana.repaint();
        }
    }
}
