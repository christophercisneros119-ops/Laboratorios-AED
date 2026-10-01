package UI.Panels;

import Recursivos.PasoQuickSort;
import Recursivos.QuickSort;
import UI.Elements.BotonEstilizado;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P_QuickSort extends P_EjercicioBase {

    /** El problema pide una cantidad fija: 10 barras, sin campo de cantidad. */
    private static final int BARRAS_TOTAL = 10;

    /** Prefijo de los cuadros del fondo animado: fondo_quicksort_00.png y siguientes. */
    private static final String FONDO = "fondo_quicksort";
    /** Tope de carga, solo un guarda contra un prefijo mal escrito. */
    private static final int MAXIMO_CUADROS = 64;

    private final JTextField txtValores = new JTextField(24);

    private List<PasoQuickSort> pasos = List.of();
    private double[] mostrados = new double[0];
    private int[] resaltados = new int[0];
    private final List<BufferedImage> cuadros =
            Imagenes.cargarSecuencia(FONDO, MAXIMO_CUADROS);

    public P_QuickSort() {
        super("QuickSort",
                "Partición de Lomuto: 10 barras fijas; escribe 10 enteros positivos separados por coma.",
                UIConstants.FONDO_QUICKSORT);
        animar(UIConstants.CUADROS_POR_SEGUNDO);
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Valores:"));
        fila.add(txtValores);
        fila.add(rotulo("Velocidad:"));
        fila.add(velocidad());
        BotonEstilizado btnGenerar = BotonEstilizado.texto("Generar", 150,
                UIConstants.GRIS_SUAVE, UIConstants.GRIS_OSCURO, UIConstants.GRIS_PRESION);
        txtValores.setFont(UIConstants.FONT_TEXTO);
        btnGenerar.addActionListener(evento -> reiniciar());
        fila.add(btnGenerar);
        // Enter en el campo genera igual que el boton.
        txtValores.addActionListener(evento -> reiniciar());
        addConfiguracion(fila);
        // No llamar a preparar() aqui: el campo viene vacio y no hay barras que
        // mostrar hasta que el usuario genere una serie valida.
        refrescar();
    }

    private double[] leerValores() {
        String texto = txtValores.getText().trim();

        // Signos: no existen en esta entrada, de un vistazo y con su propio aviso.
        if (texto.contains("-")) {
            avisar("Los números no deben llevar signo; escriba enteros positivos.");
            return null;
        }
        // Decimales: el problema pide enteros, el punto se avisa antes que el
        // resto de los simbolos para dar el mensaje mas util.
        if (texto.contains(".")) {
            avisar("Los números deben ser enteros; no escriba decimales.");
            return null;
        }
        // Primero lo mas molesto: cualquier otro caracter (letras, simbolos...)
        // se avisa de inmediato, sin importar cuantos valores haya.
        if (texto.matches(".*[^\\d,\\s].*")) {
            avisar("Ingrese solo números separados por coma; no escriba otros caracteres.");
            return null;
        }

        // split(patron, -1) conserva los vacios finales: "1,2,3" sin un numero
        // no debe contar como 9 valores.
        String[] partes = texto.split(",", -1);
        if (partes.length != BARRAS_TOTAL) {
            avisar("Debe ingresar exactamente " + BARRAS_TOTAL
                    + " números separados por coma.");
            return null;
        }

        double[] valores = new double[BARRAS_TOTAL];
        for (int i = 0; i < BARRAS_TOTAL; i++) {
            String porcion = partes[i].trim();
            if (porcion.isEmpty()) {
                avisar("Hay un valor vacío; revise las comas.");
                return null;
            }
            if (!porcion.matches("\\d+")) {
                avisar("El valor \"" + porcion + "\" no es un número entero.");
                return null;
            }
            valores[i] = Double.parseDouble(porcion);
            if (valores[i] < 1.0) {
                avisar("Los valores deben ser enteros mayores que 0.");
                return null;
            }
        }
        return valores;
    }

    @Override
    protected boolean preparar() {
        double[] entrada = leerValores();
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
        int[] ventana = Imagenes.ventanaFoto(ancho, alto);
        int vx = ventana[0];
        int vy = ventana[1];
        int aw = ventana[2];
        int ah = ventana[3];
        // El fondo va antes del corte por lista vacia, asi tambien se ve cuando
        // todavia no se han generado valores.
        if (!Imagenes.dibujarFondoVentana(g2, ventana, cuadros,
                cuadroActual(cuadros.size()))) {
            Imagenes.dibujarFondoVentana(g2, ventana, FONDO);
        }

        if (mostrados.length == 0) {
            return;
        }
        int margen = UIConstants.MARGEN;
        int base = vy + ah - margen;
        int separacion = (aw - 2 * margen) / mostrados.length;
        int anchoBarra = Math.max(separacion * 2 / 3, 2);

        g2.setFont(fuenteNumeros(ah));
        // El numero de cada barra se dibuja arriba de la punta; se reserva la
        // altura del glifo para que la espada mas alta no lo recorte en el borde.
        int cabeza = g2.getFontMetrics().getAscent() + 8;
        int altoUtil = base - (vy + margen) - cabeza;

        double maximo = 1.0;
        for (double valor : mostrados) {
            maximo = Math.max(maximo, valor);
        }

        g2.setColor(UIConstants.BORDE);
        g2.fillRect(vx + margen, base, aw - 2 * margen, 2);

        for (int i = 0; i < mostrados.length; i++) {
            int x = vx + margen + separacion * i + (separacion - anchoBarra) / 2;
            int altura = Math.max((int) Math.round(altoUtil * mostrados[i] / maximo), 2);
            int y = base - altura;
            boolean resaltada = false;
            for (int indice : resaltados) {
                resaltada = resaltada || indice == i;
            }

            if (resaltada) {
                Color halo = new Color(UIConstants.RESALTADO.getRed(),
                        UIConstants.RESALTADO.getGreen(),
                        UIConstants.RESALTADO.getBlue(), 55);
                g2.setColor(halo);
                g2.fillRoundRect(x - 2, y - 2, anchoBarra + 4, altura + 4, 8, 8);
            }

            // La barra es una espada con punta arriba y empuñadura en el piso:
            // solo la hoja se estira al cambiar la altura. Sin sprite, cae a la
            // caja plana de siempre.
            if (!Imagenes.dibujarEspada(g2, "espada", x + anchoBarra / 2,
                    base, anchoBarra, altura)) {
                caja(g2, x, y, anchoBarra, altura, 4,
                        resaltada ? UIConstants.RESALTADO : UIConstants.PIEZA,
                        UIConstants.BORDE);
            }

            if (resaltada) {
                g2.setColor(UIConstants.RESALTADO);
                g2.drawRoundRect(x - 2, y - 2, anchoBarra + 4, altura + 4, 8, 8);
            }

            textoEncima(g2, rotular(mostrados[i]), x + anchoBarra / 2,
                    y, ah, UIConstants.TEXTO_SUAVE);
        }
    }

    /** 3 se muestra "3"; 3.5 se muestra "3.5", sin el ".0" de los enteros. */
    private static String rotular(double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}