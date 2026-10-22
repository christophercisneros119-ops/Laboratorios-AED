package Listas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Ejercicio2 {

    public static void main(String[] args) {
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<Integer> positivos = new ArrayList<>();
        ArrayList<Integer> negativos = new ArrayList<>();

        while (true) {
            String entrada = JOptionPane.showInputDialog("Ingrese un numero entero (escriba fin para terminar)");
            if (entrada == null || entrada.equalsIgnoreCase("fin")) {
                break;
            }
            try {
                numeros.add(Integer.parseInt(entrada));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tiene que escribir un numero entero");
            }
        }

        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i) >= 0) {
                positivos.add(numeros.get(i));
            } else {
                negativos.add(numeros.get(i));
            }
        }

        String texto = "Lista de numeros:\n";
        for (int i = 0; i < numeros.size(); i++) {
            texto += numeros.get(i) + "   ";
        }
        texto += "\n\nLista de positivos:\n";
        for (int i = 0; i < positivos.size(); i++) {
            texto += positivos.get(i) + "   ";
        }
        texto += "\n\nLista de negativos:\n";
        for (int i = 0; i < negativos.size(); i++) {
            texto += negativos.get(i) + "   ";
        }

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }
}
