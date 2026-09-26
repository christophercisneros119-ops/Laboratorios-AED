package EjerciciosRecursivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================
 *  UNIVERSIDAD NACIONAL DE INGENIERÍA - AED - Guía 3
 *  Ejercicio 2: "El salto de la rana" (BACKEND)
 * ============================================================
 *
 * Esta clase contiene TODA la lógica (backend) del acertijo:
 *   - El algoritmo recursivo SaltoRana(N, ESTADO, MOVIMIENTOS),
 *     tal como aparece en el pseudocódigo de la guía.
 *   - El manejo del estado paso a paso, para que el FRONTEND
 *     (un JPanel/JFrame Form hecho con el diseñador de NetBeans)
 *     solo tenga que llamar a sus métodos públicos y repintarse.
 *
 * No depende de Swing/AWT: puedes probarla por consola (main)
 * o usarla desde cualquier interfaz gráfica.
 *
 * Reglas del acertijo:
 *   - Ranas verdes (V): solo se mueven/saltan hacia la DERECHA.
 *   - Ranas café  (C): solo se mueven/saltan hacia la IZQUIERDA.
 *   - Ninguna rana retrocede.
 *   - Movimiento simple: avanzar a una piedra vacía adyacente.
 *   - Salto: pasar por encima de UNA rana del color contrario si
 *     la siguiente piedra está vacía.
 *
 *   Estado inicial: V V V _ C C C
 *   Estado meta:    C C C _ V V V
 */
public class Ejercicio_2 {

    // ---------- Constantes del tablero ----------
    public static final int NUM_PIEDRAS = 7;
    public static final char VACIO = '_';
    public static final char VERDE = 'V';
    public static final char CAFE = 'C';

    /**
     * Representa un movimiento (salto) realizado por una rana.
     * Clase interna para que todo quede en un único archivo.
     */
    public static class Movimiento {
        private final int desde;
        private final int hasta;
        private final char rana;       // 'V' = verde, 'C' = café
        private final boolean esSalto; // true = saltó sobre otra rana

        public Movimiento(int desde, int hasta, char rana, boolean esSalto) {
            this.desde = desde;
            this.hasta = hasta;
            this.rana = rana;
            this.esSalto = esSalto;
        }

        public int getDesde() { return desde; }
        public int getHasta() { return hasta; }
        public char getRana() { return rana; }
        public boolean isEsSalto() { return esSalto; }

        public String getDescripcion(int numeroPaso) {
            String color = (rana == VERDE) ? "Verde" : "Café";
            String accion = esSalto ? "salta sobre otra rana" : "avanza";
            return String.format("Paso %d: Rana %s %s: piedra %d -> piedra %d",
                    numeroPaso, color, accion, desde + 1, hasta + 1);
        }

        @Override
        public String toString() {
            String color = (rana == VERDE) ? "Verde" : "Café";
            String accion = esSalto ? "salta" : "avanza";
            return String.format("Rana %s %s: piedra %d -> piedra %d", color, accion, desde + 1, hasta + 1);
        }
    }

    // ---------- Estado usado por el algoritmo recursivo ----------
    private char[] estado;
    private final List<Movimiento> movimientos = new ArrayList<>();
    private boolean solucionEncontrada;

    // ---------- Estado usado para reproducir la solución paso a paso ----------
    private char[] estadoActual;
    private int indicePaso;

    public Ejercicio_2() {
        reiniciar();
    }

    // ================= API pública para el frontend =================

    /** Vuelve al estado inicial y calcula de nuevo la secuencia completa de movimientos. */
    public final void reiniciar() {
        estado = estadoInicial();
        movimientos.clear();
        solucionEncontrada = false;
        saltoRana();               // llena "movimientos" con la solución completa

        estadoActual = estadoInicial();
        indicePaso = 0;
    }

    /** Estado actual del tablero (para dibujarlo). Longitud 7: 'V', 'C' o '_'. */
    public char[] getEstadoActual() {
        return estadoActual.clone();
    }

    /** Lista completa de movimientos de la solución (ya calculada). */
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    /** Cuántos movimientos ya se han aplicado. */
    public int getIndicePaso() {
        return indicePaso;
    }

    /** Cuántos movimientos tiene la solución en total (15 movimientos). */
    public int getTotalMovimientos() {
        return movimientos.size();
    }

    /** true cuando ya se aplicaron todos los movimientos (estado = meta). */
    public boolean isResuelto() {
        return indicePaso >= movimientos.size();
    }

    /**
     * Aplica el siguiente movimiento de la solución sobre el estado actual
     * y lo devuelve (o null si ya no quedan movimientos).
     */
    public Movimiento aplicarSiguienteMovimiento() {
        if (isResuelto()) return null;

        Movimiento m = movimientos.get(indicePaso);
        char tmp = estadoActual[m.getDesde()];
        estadoActual[m.getDesde()] = estadoActual[m.getHasta()];
        estadoActual[m.getHasta()] = tmp;
        indicePaso++;
        return m;
    }

    public static char[] estadoInicial() {
        return new char[]{VERDE, VERDE, VERDE, VACIO, CAFE, CAFE, CAFE};
    }

    public static char[] estadoMeta() {
        return new char[]{CAFE, CAFE, CAFE, VACIO, VERDE, VERDE, VERDE};
    }

    // ================= Algoritmo recursivo (backend puro) =================

    private boolean esSolucion() {
        return Arrays.equals(estado, estadoMeta());
    }

    /**
     * SaltoRana(N, ESTADO, MOVIMIENTOS) — Backtracking con Intercambiar / Deshacer.
     */
    private void saltoRana() {
        // 1. Si (ES_SOLUCION(ESTADO))
        if (esSolucion()) {
            solucionEncontrada = true;
            return;
        }

        // 2. sino -> Para i <- 0 hasta N
        for (int i = 0; i < NUM_PIEDRAS && !solucionEncontrada; i++) {

            if (estado[i] == VERDE) {
                // Mover a la derecha
                if (i + 1 < NUM_PIEDRAS && estado[i + 1] == VACIO) {
                    intercambiar(i, i + 1);
                    movimientos.add(new Movimiento(i, i + 1, VERDE, false));

                    saltoRana();
                    if (solucionEncontrada) return;

                    deshacer(i, i + 1);
                    movimientos.remove(movimientos.size() - 1);
                }
                // Saltar sobre C
                if (i + 2 < NUM_PIEDRAS && estado[i + 1] == CAFE && estado[i + 2] == VACIO) {
                    intercambiar(i, i + 2);
                    movimientos.add(new Movimiento(i, i + 2, VERDE, true));

                    saltoRana();
                    if (solucionEncontrada) return;

                    deshacer(i, i + 2);
                    movimientos.remove(movimientos.size() - 1);
                }

            } else if (estado[i] == CAFE) {
                // Mover a la izquierda
                if (i - 1 >= 0 && estado[i - 1] == VACIO) {
                    intercambiar(i, i - 1);
                    movimientos.add(new Movimiento(i, i - 1, CAFE, false));

                    saltoRana();
                    if (solucionEncontrada) return;

                    deshacer(i, i - 1);
                    movimientos.remove(movimientos.size() - 1);
                }
                // Saltar sobre V
                if (i - 2 >= 0 && estado[i - 1] == VERDE && estado[i - 2] == VACIO) {
                    intercambiar(i, i - 2);
                    movimientos.add(new Movimiento(i, i - 2, CAFE, true));

                    saltoRana();
                    if (solucionEncontrada) return;

                    deshacer(i, i - 2);
                    movimientos.remove(movimientos.size() - 1);
                }
            }
        }
    }

    private void intercambiar(int a, int b) {
        char tmp = estado[a];
        estado[a] = estado[b];
        estado[b] = tmp;
    }

    private void deshacer(int a, int b) {
        intercambiar(a, b);
    }
}