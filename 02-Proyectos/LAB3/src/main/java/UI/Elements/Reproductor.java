package UI.Elements;

import java.util.function.IntSupplier;
import javax.swing.Timer;

/**
 * Motor de animacion compartido por los cuatro ejercicios. Solo avanza un
 * indice de paso y avisa; el estado visual lo lleva cada panel.
 *
 * Hay una sola instancia por ejercicio y nunca se reemplaza, asi ningun
 * temporizador viejo puede quedar disparando contra un estado nuevo.
 */
public class Reproductor {

    private final IntSupplier totalPasos;
    private final Runnable alAvanzar;
    private final Timer temporizador;
    private int paso = -1;

    public Reproductor(IntSupplier totalPasos, Runnable alAvanzar) {
        this.totalPasos = totalPasos;
        this.alAvanzar = alAvanzar;
        this.temporizador = new Timer(UIConstants.DURACION_PASO, evento -> avanzar());
    }

    private void avanzar() {
        if (!temporizador.isRunning()) {
            // Evento viejo encolado antes de un reiniciar: que no avance nada.
            return;
        }
        paso++;
        // Se detiene antes de avisar para que el panel ya vea el estado final
        // y pueda ofrecer Repetir en vez de quedarse en Pausa.
        if (terminado()) {
            temporizador.stop();
        }
        alAvanzar.run();
    }

    public void iniciar() {
        if (total() <= 0 || terminado()) {
            return;
        }
        temporizador.setInitialDelay(0);
        temporizador.start();
    }

    public void alternar() {
        if (activo()) {
            detener();
        } else {
            iniciar();
        }
    }

    public void reiniciar() {
        detener();
        paso = -1;
    }

    public void detener() {
        temporizador.stop();
    }

    public boolean activo() {
        return temporizador.isRunning();
    }

    public boolean terminado() {
        return paso >= total() - 1;
    }

    public int paso() {
        return paso;
    }

    public int total() {
        return totalPasos.getAsInt();
    }
}
