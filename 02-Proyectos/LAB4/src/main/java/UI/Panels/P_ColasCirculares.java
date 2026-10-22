package UI.Panels;

import Estructuras.ColaCircular;
import Estructuras.DesbordamientoException;
import Estructuras.SubdesbordamientoException;
import UI.Elements.BotonEstilizado;
import UI.Elements.CampoTexto;
import UI.Elements.DibujoArreglo;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/** Modulo de cola circular: trabajos de impresion (ejercicio 4). */
public class P_ColasCirculares extends P_ModuloBase {

    // La guia fija 10 estaciones de trabajo para imprimir.
    private static final int ESTACIONES = 10;
    private static final int ANCHO_CELDA_MAXIMO = 140;
    private static final int ALTO_CELDA_MAXIMO = 96;
    private static final int SEPARACION_CELDA = 8;

    private ColaCircular cola = new ColaCircular(ESTACIONES);
    private final CampoTexto txtDato = new CampoTexto(14);

    public P_ColasCirculares() {
        super("Colas circulares",
                "Los documentos que llegan a la imprenta esperan su turno en las 10 estaciones de la empresa.",
                UIConstants.ACENTO_CIRCULAR);
        nota("Cada trabajo es un nombre corto que usted asigna (Ej: Contratos). El final envuelve a la celda 0 y reutiliza las celdas libres.");
        txtDato.setPista("Ej: Contratos");

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Trabajo:"));
        fila.add(txtDato);
        BotonEstilizado btnEncolar = boton("Encolar", 130);
        BotonEstilizado btnDesencolar = boton("Desencolar", 150);
        fila.add(btnEncolar);
        fila.add(btnDesencolar);
        addOperaciones(fila);

        btnEncolar.addActionListener(evento -> encolar());
        btnDesencolar.addActionListener(evento -> desencolar());

        actualizarEstado();
    }

    private void encolar() {
        String dato = txtDato.getText().trim();
        if (dato.isEmpty()) {
            avisar("Escriba el nombre del trabajo de impresion");
            return;
        }
        try {
            cola.encolar(dato);
            txtDato.setText("");
            limpiarAviso();
        } catch (DesbordamientoException excepcion) {
            avisar(excepcion.getMessage());
        }
        actualizarEstado();
        refrescar();
    }

    private void desencolar() {
        try {
            informar("Se imprime: " + cola.desencolar());
        } catch (SubdesbordamientoException excepcion) {
            avisar(excepcion.getMessage());
        }
        actualizarEstado();
        refrescar();
    }

    // Cola circular vacia de 10 estaciones.
    @Override
    protected void reiniciar() {
        cola = new ColaCircular(ESTACIONES);
        actualizarEstado();
    }

    private void actualizarEstado() {
        if (cola.estaVacia()) {
            estado("Frente = -1 · Final = -1 · cola vacia");
        } else {
            estado("Frente = " + cola.frente() + " · Final = " + cola.fin()
                    + " · " + cola.tamano() + " de " + cola.capacidad());
        }
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        int margen = UIConstants.MARGEN;
        int disponible = ancho - 2 * margen - (ESTACIONES - 1) * SEPARACION_CELDA;
        int anchoCelda = Math.max(Math.min(disponible / ESTACIONES, ANCHO_CELDA_MAXIMO), 28);
        int altoCelda = Math.max(Math.min(anchoCelda + 16, ALTO_CELDA_MAXIMO), 44);
        int paso = anchoCelda + SEPARACION_CELDA;
        int total = ESTACIONES * anchoCelda + (ESTACIONES - 1) * SEPARACION_CELDA;
        int x0 = (ancho - total) / 2;
        int y = alto / 2 - altoCelda / 2 - 12;

        for (int i = 0; i < ESTACIONES; i++) {
            int x = x0 + i * paso;
            String dato = cola.obtener(i);
            boolean activa = cola.esActiva(i);
            boolean basura = dato != null && !activa;
            Color relleno = activa ? UIConstants.CASILLA
                    : (basura ? UIConstants.CASILLA_BASURA : UIConstants.CASILLA_VACIA);
            Color borde = activa && (i == cola.frente() || i == cola.fin())
                    ? acento() : UIConstants.BORDE;
            Color tinta = basura ? UIConstants.BASURA_TEXTO : UIConstants.TEXTO;
            DibujoArreglo.celda(g2, x, y, anchoCelda, altoCelda, dato,
                    relleno, borde, tinta);
            DibujoArreglo.indice(g2, x + anchoCelda / 2, y + altoCelda + 16, i);
        }

        // Flecha de envoltura debajo de los indices: de la celda 9 a la 0.
        DibujoArreglo.envoltura(g2, x0 + 9 * paso + anchoCelda / 2,
                x0 + anchoCelda / 2, y + altoCelda + 34, acento());

        if (!cola.estaVacia()) {
            int xFrente = x0 + cola.frente() * paso + anchoCelda / 2;
            int xFin = x0 + cola.fin() * paso + anchoCelda / 2;
            if (cola.frente() == cola.fin()) {
                DibujoArreglo.punteroAbajo(g2, xFrente, y, 0, "Frente = Final", acento());
            } else {
                DibujoArreglo.punteroAbajo(g2, xFrente, y, 1, "Frente", acento());
                DibujoArreglo.punteroAbajo(g2, xFin, y, 0, "Final", acento());
            }
        }
    }
}
