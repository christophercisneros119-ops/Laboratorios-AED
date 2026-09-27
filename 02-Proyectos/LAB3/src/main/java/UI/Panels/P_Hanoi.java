package UI.Panels;

import Recursivos.Constantes;
import Recursivos.Hanoi;
import Recursivos.PasoHanoi;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P_Hanoi extends P_EjercicioBase {

    private final JTextField txtDiscos =
            new JTextField(String.valueOf(Constantes.DISCOS_MAXIMOS), 4);

    private List<PasoHanoi> pasos = List.of();
    private List<List<Integer>> torres = torresVacias();
    private int discos = Constantes.DISCOS_MAXIMOS;

    /** El panel tiene que poder dibujarse antes de que se toque ningun boton. */
    private static List<List<Integer>> torresVacias() {
        List<List<Integer>> lista = new ArrayList<>();
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            lista.add(new ArrayList<>());
        }
        return lista;
    }

    public P_Hanoi() {
        super("Torres de Hanói",
                "Mueve los discos de la torre 1 a la torre 3 usando la torre 2.");
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Discos:"));
        fila.add(txtDiscos);
        JButton btnGenerar = new JButton("Generar");
        btnGenerar.addActionListener(evento -> reiniciar());
        fila.add(btnGenerar);
        addConfiguracion(fila);
        preparar();
        refrescar();
    }

    @Override
    protected boolean preparar() {
        int cantidad = leerDiscos();
        if (cantidad < 1) {
            return false;
        }
        discos = cantidad;
        pasos = Hanoi.resolver(cantidad);
        torres = torresVacias();
        for (int disco = discos; disco >= 1; disco--) {
            torres.get(Constantes.TORRE_ORIGEN).add(disco);
        }
        return true;
    }

    private int leerDiscos() {
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtDiscos.getText().trim());
        } catch (NumberFormatException excepcion) {
            avisar("La cantidad de discos debe ser un número entero.");
            return -1;
        }
        if (cantidad < 1 || cantidad > Constantes.DISCOS_MAXIMOS) {
            avisar("La cantidad de discos debe estar entre 1 y "
                    + Constantes.DISCOS_MAXIMOS + ".");
            return -1;
        }
        return cantidad;
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
        PasoHanoi paso = pasos.get(indice);
        torres.get(paso.torreOrigen()).removeLast();
        torres.get(paso.torreDestino()).add(paso.numeroDisco());
    }

    private static int posicionTorre(int ancho, int torre) {
        return ancho * (torre + 1) / (Constantes.TORRES_TOTAL + 1);
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        Imagenes.dibujarFondo(g2, ancho, alto, "fondo_hanoi");

        int base = alto - UIConstants.MARGEN;
        int altoDisco = alto / (discos + Constantes.TORRES_TOTAL);
        int grosor = Math.max(4, ancho / 200);
        int radio = Math.max(6, altoDisco / 3);

        // Escenario: el piso y los tres postes.
        g2.setColor(UIConstants.PIEZA_ALT);
        g2.fillRect(UIConstants.MARGEN, base - 4, ancho - 2 * UIConstants.MARGEN, 4);
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            int centro = posicionTorre(ancho, torre);
            g2.fillRect(centro - grosor / 2, alto / 5, grosor, base - alto / 5);
        }

        // Piezas: los discos, de abajo hacia arriba en cada torre.
        for (int torre = 0; torre < Constantes.TORRES_TOTAL; torre++) {
            List<Integer> pila = torres.get(torre);
            for (int nivel = 0; nivel < pila.size(); nivel++) {
                int disco = pila.get(nivel);
                int anchoDisco = ancho * disco / (Math.max(1, discos) * Constantes.TORRES_TOTAL);
                int centro = posicionTorre(ancho, torre);
                int y = base - (nivel + 1) * altoDisco;
                caja(g2, centro - anchoDisco / 2, y, anchoDisco, altoDisco - 2, radio,
                        UIConstants.PIEZA, UIConstants.BORDE);
                textoCentrado(g2, String.valueOf(disco), centro,
                        y + altoDisco / 2 + 4, UIConstants.TEXTO);
            }
        }
    }
}
