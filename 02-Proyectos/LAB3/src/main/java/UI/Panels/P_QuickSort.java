package UI.Panels;

import Recursivos.PasoQuickSort;
import Recursivos.QuickSort;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P_QuickSort extends P_EjercicioBase {

    private static final int[] VALORES_POR_DEFECTO = {9, 3, 7, 1, 5, 4};

    /** Prefijo de los cuadros del fondo animado: fondo_quicksort_00.png y siguientes. */
    private static final String FONDO = "fondo_quicksort";
    /** Tope de carga, solo un guarda contra un prefijo mal escrito. */
    private static final int MAXIMO_CUADROS = 64;

    private final JTextField txtCantidad =
            new JTextField(String.valueOf(VALORES_POR_DEFECTO.length), 4);
    private final JTextField txtValores =
            new JTextField(aTexto(VALORES_POR_DEFECTO), 22);

    private List<PasoQuickSort> pasos = List.of();
    private int[] mostrados = VALORES_POR_DEFECTO;
    private int[] resaltados = new int[0];
    private final List<BufferedImage> cuadros =
            Imagenes.cargarSecuencia(FONDO, MAXIMO_CUADROS);

    public P_QuickSort() {
        super("QuickSort", "Partición de Lomuto: cada intercambio es un paso de la animación.");
        animar(UIConstants.CUADROS_POR_SEGUNDO);
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Cantidad:"));
        fila.add(txtCantidad);
        fila.add(rotulo("Valores:"));
        fila.add(txtValores);
        JButton btnGenerar = new JButton("Generar");
        btnGenerar.addActionListener(evento -> reiniciar());
        fila.add(btnGenerar);
        addConfiguracion(fila);
        preparar();
        refrescar();
    }

    private static String aTexto(int[] valores) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) {
                texto.append(", ");
            }
            texto.append(valores[i]);
        }
        return texto.toString();
    }

    private int[] leerValores() {
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException excepcion) {
            avisar("La cantidad debe ser un número entero.");
            return null;
        }
        if (cantidad < 1 || cantidad > UIConstants.ELEMENTOS_MAXIMOS) {
            avisar("La cantidad debe estar entre 1 y "
                    + UIConstants.ELEMENTOS_MAXIMOS + " para que quepan en una fila.");
            return null;
        }

        String[] partes = txtValores.getText().split(",");
        if (partes.length != cantidad) {
            avisar("Tenés que escribir exactamente " + cantidad
                    + " valores separados por coma.");
            return null;
        }

        int[] valores = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String texto = partes[i].trim();
            try {
                valores[i] = Integer.parseInt(texto);
            } catch (NumberFormatException excepcion) {
                avisar("El valor \"" + texto + "\" no es un número entero.");
                return null;
            }
            if (valores[i] < 1) {
                avisar("Los valores deben ser mayores o iguales a 1.");
                return null;
            }
        }
        return valores;
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected boolean preparar() {
        int[] entrada = leerValores();
        if (entrada == null) {
            return false;
        }
        pasos = QuickSort.resolver(entrada);
        mostrados = entrada.clone();
        resaltados = new int[0];
        return true;
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
        PasoQuickSort paso = pasos.get(indice);
        mostrados = paso.valores();
        resaltados = new int[]{paso.indiceOrigen(), paso.indiceDestino()};
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        // El fondo va antes del corte por lista vacia, asi tambien se ve cuando
        // todavia no se han generado valores.
        if (!Imagenes.dibujarSecuencia(g2, ancho, alto, cuadros, cuadroActual(cuadros.size()))) {
            Imagenes.dibujarFondo(g2, ancho, alto, FONDO);
        }

        if (mostrados.length == 0) {
            return;
        }
        int margen = UIConstants.MARGEN;
        int base = alto - 2 * margen;
        int separacion = (ancho - 2 * margen) / mostrados.length;
        int anchoBarra = Math.max(separacion * 2 / 3, 2);
        int altoUtil = base - margen;

        int maximo = 1;
        for (int valor : mostrados) {
            maximo = Math.max(maximo, valor);
        }

        g2.setColor(UIConstants.BORDE);
        g2.fillRect(margen, base, ancho - 2 * margen, 2);

        for (int i = 0; i < mostrados.length; i++) {
            int x = margen + separacion * i + (separacion - anchoBarra) / 2;
            int altura = Math.max(altoUtil * mostrados[i] / maximo, 2);
            int y = base - altura;
            boolean resaltada = false;
            for (int indice : resaltados) {
                resaltada = resaltada || indice == i;
            }
            caja(g2, x, y, anchoBarra, altura, 4,
                    resaltada ? UIConstants.RESALTADO : UIConstants.PIEZA, UIConstants.BORDE);
            textoCentrado(g2, String.valueOf(mostrados[i]), x + anchoBarra / 2,
                    y - 6, UIConstants.TEXTO);
        }
    }
}
