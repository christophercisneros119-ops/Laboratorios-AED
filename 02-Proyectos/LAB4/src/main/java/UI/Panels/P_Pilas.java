package UI.Panels;

import Estructuras.DesbordamientoException;
import Estructuras.Pila;
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

/** Modulo de pilas: centros comerciales de la ciudad (ejercicio 2). */
public class P_Pilas extends P_ModuloBase {

    private static final int CAPACIDAD_MINIMA = 1;
    private static final int CAPACIDAD_MAXIMA = 16;
    private static final int ALTO_CELDA_MAXIMO = 64;
    private static final int ANCHO_CELDA_MAXIMO = 260;
    private static final int SEPARACION_CELDA = 6;

    private Pila pila;
    private final CampoTexto txtCapacidad = new CampoTexto(4);
    private final CampoTexto txtDato = new CampoTexto(14);

    public P_Pilas() {
        super("Pilas",
                "Los centros comerciales de la ciudad se guardan en la pila: el último en entrar es el primero en salir (LIFO).",
                UIConstants.ACENTO_PILA);
        nota("Capacidad de 1 a 16. Escriba un nombre y presione Apilar; Desapilar retira el que está en la cima.");
        txtDato.setPista("Ej: Metro Centro");

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.add(rotulo("Capacidad:"));
        fila.add(txtCapacidad);
        BotonEstilizado btnCrear = boton("Crear", 110);
        fila.add(btnCrear);
        fila.add(Box.createHorizontalStrut(24));
        fila.add(rotulo("Centro comercial:"));
        fila.add(txtDato);
        BotonEstilizado btnApilar = boton("Apilar", 130);
        BotonEstilizado btnDesapilar = boton("Desapilar", 150);
        fila.add(btnApilar);
        fila.add(btnDesapilar);
        addOperaciones(fila);

        btnCrear.addActionListener(evento -> crear());
        btnApilar.addActionListener(evento -> apilar());
        btnDesapilar.addActionListener(evento -> desapilar());

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
        pila = new Pila(capacidad);
        txtDato.setText("");
        limpiarAviso();
        actualizarEstado();
        refrescar();
    }

    private void apilar() {
        if (pila == null) {
            avisar("Cree primero la pila con una capacidad");
            return;
        }
        String dato = txtDato.getText().trim();
        if (dato.isEmpty()) {
            avisar("Escriba el nombre del centro comercial");
            return;
        }
        try {
            pila.push(dato);
            txtDato.setText("");
            limpiarAviso();
        } catch (DesbordamientoException excepcion) {
            avisar(excepcion.getMessage());
        }
        actualizarEstado();
        refrescar();
    }

    private void desapilar() {
        if (pila == null) {
            avisar("Cree primero la pila con una capacidad");
            return;
        }
        try {
            informar("Se elimino: " + pila.pop());
        } catch (SubdesbordamientoException excepcion) {
            avisar(excepcion.getMessage());
        }
        actualizarEstado();
        refrescar();
    }

    // Pila vacia con la misma capacidad.
    @Override
    protected void reiniciar() {
        if (pila != null) {
            pila = new Pila(pila.capacidad());
        }
        actualizarEstado();
    }

    private void actualizarEstado() {
        if (pila == null) {
            estado("Pila sin crear");
        } else if (pila.estaVacia()) {
            estado("Tope = -1 · pila vacia · " + pila.capacidad() + " celdas");
        } else {
            estado("Tope = " + pila.tope() + " · " + pila.tamano() + " de "
                    + pila.capacidad() + (pila.estaLlena() ? " · llena" : ""));
        }
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        if (pila == null) {
            mensajeCentrado(g2, ancho, alto,
                    "Defina la capacidad y presione Crear");
            return;
        }
        int capacidad = pila.capacidad();
        int margen = UIConstants.MARGEN;
        int altoDisponible = alto - 2 * margen;
        int altoCelda = Math.min(altoDisponible / capacidad
                - SEPARACION_CELDA, ALTO_CELDA_MAXIMO);
        int anchoCelda = Math.max(Math.min(ancho / 2, ANCHO_CELDA_MAXIMO), 140);
        int totalAlto = capacidad * (altoCelda + SEPARACION_CELDA);
        int x = ancho / 2 - anchoCelda / 2;
        int yBase = margen + (altoDisponible - totalAlto) / 2 + totalAlto;

        for (int i = 0; i < capacidad; i++) {
            int yCelda = yBase - (i + 1) * altoCelda - i * SEPARACION_CELDA;
            boolean activa = i <= pila.tope();
            Color relleno = activa ? UIConstants.CASILLA : UIConstants.CASILLA_VACIA;
            Color borde = i == pila.tope() ? acento() : UIConstants.BORDE;
            DibujoArreglo.celda(g2, x, yCelda, anchoCelda, altoCelda,
                    activa ? pila.obtener(i) : null, relleno, borde, UIConstants.TEXTO);
            DibujoArreglo.indice(g2, x - 14, yCelda + altoCelda / 2 + 3, i);
        }
        if (!pila.estaVacia()) {
            int yTope = yBase - (pila.tope() + 1) * altoCelda
                    - pila.tope() * SEPARACION_CELDA + altoCelda / 2;
            DibujoArreglo.punteroIzquierda(g2, x + anchoCelda, yTope, "Tope", acento());
        }
    }
}
