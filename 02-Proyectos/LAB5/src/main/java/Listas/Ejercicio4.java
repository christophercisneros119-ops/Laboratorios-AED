package Listas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Ejercicio4 {

    public static void main(String[] args) {
        ArrayList<Integer> lista = new ArrayList<>();

        while (true) {
            String entrada = JOptionPane.showInputDialog("Ingrese un numero entero positivo (escriba fin para terminar)");
            if (entrada == null || entrada.equalsIgnoreCase("fin")) {
                break;
            }
            try {
                int n = Integer.parseInt(entrada);
                if (n > 0) {
                    lista.add(n);
                } else {
                    JOptionPane.showMessageDialog(null, "El numero tiene que ser positivo");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tiene que escribir un numero entero");
            }
        }

        String texto = "Elementos de la Lista:\n" + imprimirRecursivo(lista, 0);

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }

    public static String imprimirRecursivo(ArrayList<Integer> lista, int i) {
        if (i == lista.size()) {
            return "";
        }
        return lista.get(i) + " " + imprimirRecursivo(lista, i + 1);
    }
}
