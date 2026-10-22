package UI.Panels;

import Estructuras.Cola;
import Estructuras.DesbordamientoException;
import Estructuras.SubdesbordamientoException;
import UI.Elements.BotonEstilizado;
import UI.Elements.CampoTexto;
import UI.Elements.DibujoArreglo;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Modulo de colas lineales (ejercicio 3 de la guia): las personas que visitan
 * el banco y esperan su turno, insertadas con Encolar y atendidas con
 * Desencolar. Las celdas que el frente deja atras se pintan atenuadas: son la
 * basura que la cola lineal nunca reutiliza.
 */
public class P_Colas extends P_ModuloBase {

    private static final int CAPACIDAD_MINIMA = 1;
    private static final int CAPACIDAD_MAXIMA = 16;
    private static final int ANCHO_CELDA_MAXIMO = 140;
    private static final int ALTO_CELDA_MAXIMO = 96;
    private static final int SEPARACION_CELDA = 8;

    private Cola cola;
    private final CampoTexto txtCapacidad = new CampoTexto(4);
    private final CampoTexto txtDato = new CampoTexto(14);

    public P_Colas() {
        super("Colas",
                "Las personas que visitan el banco esperan su turno: primero en entrar, primero en salir (FIFO).",
                UIConstants.ACENTO_COLA);
        nota("Capacidad de 1 a 16. Encolar agrega al final de la fila; Desencolar atiende al frente.");
        txtDato.setPista("Ej: María López");

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Capacidad:"));
        fila.add(txtCapacidad);
        BotonEstilizado btnCrear = boton("Crear", 110);
        fila.add(btnCrear);
        fila.add(Box.createHorizontalStrut(24));
        fila.add(rotulo("Persona:"));
        fila.add(txtDato);
        BotonEstilizado btnEncolar = boton("Encolar", 130);
        BotonEstilizado btnDesencolar = boton("Desencolar", 150);
        fila.add(btnEncolar);
        fila.add(btnDesencolar);
        addOperaciones(fila);

        btnCrear.addActionListener(evento -> crear());
        btnEncolar.addActionListener(evento -> encolar());
        btnDesencolar.addActionListener(evento -> desencolar());

        actualizarEstado();
    }

    private void crear() {
        int capacidad;
        try {
            capacidad = Integer.parseInt(txtCapacidad.getText().trim());
        } catch (NumberFormatException excepcion) {
            avisar("La capacidad debe ser un numero entero");
            return;
        }
        if (capacidad < CAPACIDAD_MINIMA || capacidad > CAPACIDAD_MAXIMA) {
            avisar("La capacidad va de " + CAPACIDAD_MINIMA + " a " + CAPACIDAD_MAXIMA);
            return;
        }
        cola = new Cola(capacidad);
        txtDato.setText("");
        limpiarAviso();
        actualizarEstado();
        refrescar();
    }

    private void encolar() {
        if (cola == null) {
            avisar("Cree primero la cola con una capacidad");
            return;
        }
        String dato = txtDato.getText().trim();
        if (dato.isEmpty()) {
            avisar("Escriba el nombre de la persona");
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
        if (cola == null) {
            avisar("Cree primero la cola con una capacidad");
            return;
        }
        try {
            informar("Se atendio: " + cola.desencolar());
        } catch (SubdesbordamientoException excepcion) {
            avisar(excepcion.getMessage());
        }
        actualizarEstado();
        refrescar();
    }

    /** Empieza de nuevo: cola vacia con la misma capacidad. */
    @Override
    protected void reiniciar() {
        if (cola != null) {
            cola = new Cola(cola.capacidad());
        }
        actualizarEstado();
    }

    private void actualizarEstado() {
        if (cola == null) {
            estado("Cola sin crear");
        } else if (cola.estaVacia()) {
            estado("Frente = -1 · Final = -1 · cola vacia");
        } else {
            estado("Frente = " + cola.frente() + " · Final = " + cola.fin()
                    + " · " + cola.tamano() + " de " + cola.capacidad());
        }
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        if (cola == null) {
            mensajeCentrado(g2, ancho, alto,
                    "Defina la capacidad y presione Crear");
            return;
        }
        int[] caja = filaCeldas(ancho, alto, cola.capacidad());
        int x0 = caja[0];
        int y = caja[1];
        int anchoCelda = caja[2];
        int altoCelda = caja[3];
        int paso = anchoCelda + SEPARACION_CELDA;

        for (int i = 0; i < cola.capacidad(); i++) {
            int x = x0 + i * paso;
            String dato = cola.obtener(i);
            boolean activa = !cola.estaVacia()
                    && i >= cola.frente() && i <= cola.fin();
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

    /**
     * Geometria de la fila horizontal: celdas de ancho parejo centradas, con
     * indices abajo y punteros arriba.
     */
    private int[] filaCeldas(int ancho, int alto, int cantidad) {
        int margen = UIConstants.MARGEN;
        int disponible = ancho - 2 * margen - (cantidad - 1) * SEPARACION_CELDA;
        int anchoCelda = Math.max(Math.min(disponible / cantidad, ANCHO_CELDA_MAXIMO), 28);
        int altoCelda = Math.max(Math.min(anchoCelda + 16, ALTO_CELDA_MAXIMO), 44);
        int total = cantidad * anchoCelda + (cantidad - 1) * SEPARACION_CELDA;
        int x0 = (ancho - total) / 2;
        // La fila sube un poco: abajo quedan los indices y arriba los punteros.
        int y = alto / 2 - altoCelda / 2 - 12;
        return new int[]{x0, y, anchoCelda, altoCelda};
    }
}
