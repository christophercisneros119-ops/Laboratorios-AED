package UI.Panels;

import Polaca.ConversorPolaco;
import Polaca.ExpresionInvalidaException;
import Polaca.PasoPolaco;
import Polaca.ResultadoPolaco;
import UI.Elements.BotonEstilizado;
import UI.Elements.CampoTexto;
import UI.Elements.DibujoArreglo;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.List;
import javax.swing.JPanel;

/**
 * Modulo de notacion polaca (ejercicio 1 de la guia): convierte la expresion
 * infija a prefija o postfija mostrando el paso a paso. La traza numerada va
 * a la izquierda, la pila de operadores del paso resaltado a la derecha y los
 * botones Anterior/Siguiente recorren los pasos, que es lo que la guia pide
 * ir imprimiendo mientras se convierte.
 */
public class P_NotacionPolaca extends P_ModuloBase {

    private static final int ALTO_FILA_TRAZA = 26;
    /** Franja inferior reservada para la nota del paso, nadie la pisa. */
    private static final int ALTO_NOTA = 24;
    /** Fraccion del ancho que ocupa la columna de la traza. */
    private static final double FRACCION_TRAZA = 0.56;

    private final CampoTexto txtExpresion = new CampoTexto(18);
    private final BotonEstilizado btnAnterior;
    private final BotonEstilizado btnSiguiente;
    private ResultadoPolaco resultado;
    private String tipo = "postfija";
    private int pasoActual;

    public P_NotacionPolaca() {
        super("Notación polaca",
                "Convierta la expresión infija a prefija o postfija, paso a paso con la pila de operadores.",
                UIConstants.ACENTO_POLACA);
        nota("Una letra A-Z por operando. Operadores ^ * / + - entre dos operandos. Paréntesis para agrupar: (A+B)*C. Espacios ignorados.");
        txtExpresion.setPista("Ej: A+B*C");

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Expresión:"));
        fila.add(txtExpresion);
        BotonEstilizado btnPrefija = boton("Prefija", 120);
        BotonEstilizado btnPostfija = boton("Postfija", 130);
        fila.add(btnPrefija);
        fila.add(btnPostfija);
        addOperaciones(fila);

        // La navegacion vive en el pie: en la fila de arriba no cabe.
        JPanel navegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnAnterior = boton("Anterior", 120);
        btnSiguiente = boton("Siguiente", 130);
        navegacion.add(btnAnterior);
        navegacion.add(btnSiguiente);
        addControlesPie(navegacion);

        btnPrefija.addActionListener(evento -> convertir(true));
        btnPostfija.addActionListener(evento -> convertir(false));
        btnAnterior.addActionListener(evento -> mover(-1));
        btnSiguiente.addActionListener(evento -> mover(1));

        actualizarNavegacion();
    }

    private void convertir(boolean prefija) {
        try {
            resultado = prefija
                    ? ConversorPolaco.aPrefija(txtExpresion.getText())
                    : ConversorPolaco.aPostfija(txtExpresion.getText());
            tipo = prefija ? "prefija" : "postfija";
            pasoActual = 0;
            limpiarAviso();
        } catch (ExpresionInvalidaException excepcion) {
            avisar("Expresión inválida: " + excepcion.getMessage());
        }
        actualizarNavegacion();
        refrescar();
    }

    /** Empieza de nuevo: sin conversion, sin traza y el campo limpio. */
    @Override
    protected void reiniciar() {
        resultado = null;
        txtExpresion.setText("");
        actualizarNavegacion();
    }

    private void mover(int delta) {
        if (resultado == null) {
            return;
        }
        int total = resultado.pasos().size();
        pasoActual = Math.max(0, Math.min(pasoActual + delta, total - 1));
        actualizarNavegacion();
        refrescar();
    }

    private void actualizarNavegacion() {
        if (resultado == null) {
            btnAnterior.setEnabled(false);
            btnSiguiente.setEnabled(false);
            estado("Sin conversión");
            return;
        }
        int total = resultado.pasos().size();
        btnAnterior.setEnabled(pasoActual > 0);
        btnSiguiente.setEnabled(pasoActual < total - 1);
        estado("Paso " + (pasoActual + 1) + " de " + total);
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        if (resultado == null) {
            mensajeCentrado(g2, ancho, alto,
                    "Escriba la expresión infija y elija Prefija o Postfija");
            return;
        }
        pintarResultado(g2, ancho);
        pintarTraza(g2, ancho, alto);
        pintarPilaPaso(g2, ancho, alto);
    }

    /** Las dos lineas de arriba: la infija original y la conversion lograda. */
    private void pintarResultado(Graphics2D g2, int ancho) {
        g2.setFont(UIConstants.FONT_TITULO);
        FontMetrics medidas = g2.getFontMetrics();
        String infija = "Infija: " + resultado.infija();
        g2.setColor(UIConstants.TEXTO);
        g2.drawString(infija, (ancho - medidas.stringWidth(infija)) / 2, 46);

        String conversion = Character.toUpperCase(tipo.charAt(0)) + tipo.substring(1)
                + ": " + resultado.resultado();
        g2.setColor(acento());
        g2.drawString(conversion, (ancho - medidas.stringWidth(conversion)) / 2, 84);
    }

    /**
     * Columna de la izquierda: la traza numerada con el simbolo leido, la
     * pila y la salida. Solo se ven las filas que caben, corridas para que el
     * paso resaltado siempre este en pantalla.
     */
    private void pintarTraza(Graphics2D g2, int ancho, int alto) {
        int margen = UIConstants.MARGEN;
        int anchoTraza = (int) (ancho * FRACCION_TRAZA);
        int yInicio = 120;
        int maxFilas = Math.max((alto - yInicio - margen - ALTO_NOTA) / ALTO_FILA_TRAZA, 1);
        int total = resultado.pasos().size();
        int primera = Math.max(0, Math.min(pasoActual - maxFilas + 1, total - maxFilas));

        g2.setFont(UIConstants.FONT_CHICA);
        g2.setColor(UIConstants.TEXTO_SUAVE);
        g2.drawString("PASO", margen, yInicio - 8);
        g2.drawString("SIMB", margen + 50, yInicio - 8);
        g2.drawString("PILA", margen + 110, yInicio - 8);
        g2.drawString("SALIDA", margen + 270, yInicio - 8);

        for (int f = 0; f < maxFilas && primera + f < total; f++) {
            PasoPolaco paso = resultado.pasos().get(primera + f);
            int yTexto = yInicio + 16 + f * ALTO_FILA_TRAZA;
            boolean actual = primera + f == pasoActual;
            if (actual) {
                g2.setColor(acento());
                g2.drawRect(margen - 8, yTexto - 14, anchoTraza, ALTO_FILA_TRAZA - 2);
            }
            g2.setFont(UIConstants.FONT_TEXTO);
            g2.setColor(actual ? acento() : UIConstants.TEXTO_SUAVE);
            g2.drawString(String.valueOf(paso.numero()), margen, yTexto);
            g2.drawString(paso.simbolo().isBlank() ? "-" : paso.simbolo(),
                    margen + 50, yTexto);

            // La pila se recorta a su columna para que no invada la salida.
            Shape clipViejo = g2.getClip();
            g2.setClip(margen + 104, yTexto - 14, 160, 22);
            String pilaTexto = paso.pila().isEmpty()
                    ? "(vacia)" : String.join(" ", paso.pila());
            g2.drawString(pilaTexto, margen + 110, yTexto);
            g2.setClip(clipViejo);

            g2.setColor(actual ? acento() : UIConstants.TEXTO);
            g2.setClip(margen + 264, yTexto - 14, anchoTraza - 272, 22);
            g2.drawString(paso.salida(), margen + 270, yTexto);
            g2.setClip(clipViejo);
        }
    }

    /** Columna de la derecha: la pila de operadores del paso resaltado. */
    private void pintarPilaPaso(Graphics2D g2, int ancho, int alto) {
        PasoPolaco paso = resultado.pasos().get(pasoActual);
        int margen = UIConstants.MARGEN;
        int xPanel = margen + (int) (ancho * FRACCION_TRAZA) + 40;
        int anchoPanel = Math.max(ancho - xPanel - margen, 150);

        g2.setFont(UIConstants.FONT_SUBTITULO);
        FontMetrics medidas = g2.getFontMetrics();
        String titulo = "Pila del paso " + paso.numero();
        g2.setColor(UIConstants.TEXTO);
        g2.drawString(titulo, xPanel + (anchoPanel - medidas.stringWidth(titulo)) / 2, 120);

        int capacidad = Math.max(resultado.infija().length(), 1);
        int separacion = 4;
        // La pila vive entre el titulo (y 140) y la franja inferior de la nota.
        int disponible = alto - 140 - ALTO_NOTA;
        int altoCelda = Math.max(Math.min(disponible / capacidad - separacion, 30), 12);
        int anchoCelda = Math.max(Math.min(anchoPanel - 70, 150), 60);
        int x = xPanel + (anchoPanel - anchoCelda) / 2;
        int yBase = Math.min(140 + capacidad * (altoCelda + separacion), alto - ALTO_NOTA);
        List<String> contenido = paso.pila();

        for (int i = 0; i < capacidad; i++) {
            int yCelda = yBase - (i + 1) * altoCelda - i * separacion;
            boolean activa = i < contenido.size();
            Color relleno = activa ? UIConstants.CASILLA : UIConstants.CASILLA_VACIA;
            Color borde = activa && i == contenido.size() - 1 ? acento() : UIConstants.BORDE;
            DibujoArreglo.celda(g2, x, yCelda, anchoCelda, altoCelda,
                    activa ? contenido.get(i) : null, relleno, borde, UIConstants.TEXTO);
        }
        if (!contenido.isEmpty()) {
            int yTope = yBase - contenido.size() * altoCelda
                    - (contenido.size() - 1) * separacion + altoCelda / 2;
            DibujoArreglo.punteroIzquierda(g2, x + anchoCelda, yTope, "Tope", acento());
        }

        // La nota va centrada al pie de todo el lienzo, dentro de su franja.
        textoAjustado(g2, "Nota: " + paso.nota(), 0, alto - 14, ancho,
                UIConstants.TEXTO_SUAVE);
    }

    /** Texto centrado en 16 px que baja a 8 px si no cabe en el ancho dado. */
    private void textoAjustado(Graphics2D g2, String texto, int x, int baseY,
                                int anchoMax, Color color) {
        g2.setFont(UIConstants.FONT_TEXTO);
        FontMetrics medidas = g2.getFontMetrics();
        if (medidas.stringWidth(texto) > anchoMax) {
            g2.setFont(UIConstants.FONT_CHICA);
            medidas = g2.getFontMetrics();
        }
        g2.setColor(color);
        g2.drawString(texto, x + (anchoMax - medidas.stringWidth(texto)) / 2, baseY);
    }
}
