package UI.Panels;

import Recursivos.Constantes;
import Recursivos.PasoRana;
import Recursivos.SaltoRana;
import UI.Elements.Imagenes;
import UI.Elements.UIConstants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class P_Rana extends P_EjercicioBase {

    /**
     * Casilla del centro, la unica que arranca vacia: ahi va la piedra y no un
     * nenufar. Es el mismo indice que deja hueco {@code SaltoRana.estadoInicial()}.
     */
    private static final int CENTRO = Constantes.RANAS_VERDES;

    /**
     * Caja de nenufar.png y piedra.png. Los dos sprites son de 24 px.
     *
     * <p>Ojo con el arte real: dentro de esos 24 px el nenufar solo ocupa 21x6 y
     * la piedra 18x4, el resto es margen transparente. Por eso la proporcion se
     * mide contra la caja y no contra lo que se ve, y por eso el agua se ve
     * igual de libre entre un nenufar y la piedra.
     */
    private static final int LADO_ESCENARIO = 24;

    /**
     * Cuanto se estira la caja del escenario respecto de la pieza.
     *
     * <p>Algo mayor que 1 porque el arte es bajo y la rana va encima: con la
     * caja justa el nenufar saldria de 105 px contra 81 de la rana y el sprite
     * de la rana casi llenaria el nenufar. Con 1.25 el nenufar queda en 126 px
     * y la rana se lee encima sin quedar apretada.
     */
    private static final double PROPORCION_ESCENARIO = 1.25;

    /**
     * Fraccion de la altura del fondo dibujado donde va la linea de la escena.
     *
     * <p>En la foto el agua cercana va de 61% a 85% de la altura; con 0.65 la
     * linea queda en la parte alta del agua. Se mide contra el rectangulo del
     * cover, no contra el alto de la ventana, para que la escena se mantenga en
     * la misma fila del agua aunque cambie la proporcion.
     */
    private static final double AGUA = 0.65;

    /**
     * Proporcion del factor del escenario que usa la rana.
     *
     * <p>Un punto menor que 1 para que el nenufar se vea alrededor de la rana y
     * no quede tapado por completo: con factor 6 de la escena, la rana va a 5.
     */
    private static final double PROPORCION_RANA = 0.85;

    /**
     * Elevacion del salto como parte de la distancia horizontal recorrida: la
     * rana se levanta mas en los saltos de 2 casillas que en los avances de 1.
     */
    private static final double PROPORCION_ARCO = 0.12;

    /**
     * Ritmo del repintado del lienzo durante el paso. El avance de fase no lo
     * marca este reloj sino el de {@code inicioPaso}: solo vuelve a pintar.
     */
    private static final int FPS_RANA = 30;

    private List<PasoRana> pasos = List.of();
    private char[] casillas = SaltoRana.estadoInicial();

    /** Paso en curso, o {@code -1} fuera de la animacion (inicio o Reiniciar). */
    private int pasoActual = -1;

    /** Cuando arranco el paso, para derivar la fase del reloj y no de un contador. */
    private long inicioPaso;

    public P_Rana() {
        super("Salto de la rana",
                "Las ranas verdes cruzan hacia la derecha y las cafés hacia la izquierda.");
        preparar();
        animar(FPS_RANA);
        refrescar();
    }

    @Override
    protected int duracionPaso() {
        return UIConstants.DURACION_PASO_RANA;
    }

    @Override
    protected boolean preparar() {
        pasos = SaltoRana.resolver();
        casillas = SaltoRana.estadoInicial();
        pasoActual = -1;
        inicioPaso = System.currentTimeMillis();
        return true;
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
        pasoActual = indice;
        inicioPaso = System.currentTimeMillis();
        PasoRana paso = pasos.get(indice);
        char auxiliar = casillas[paso.casillaOrigen()];
        casillas[paso.casillaOrigen()] = casillas[paso.casillaDestino()];
        casillas[paso.casillaDestino()] = auxiliar;
    }

    @Override
    protected void pintarLienzo(Graphics2D g2, int ancho, int alto) {
        Imagenes.dibujarFondo(g2, ancho, alto, "fondo_rana");

        int separacion = ancho / (Constantes.CASILLAS_TOTAL + 1);
        int diametro = Math.max(Math.min(separacion, alto) / 2 - UIConstants.MARGEN / 2, 8);
        int centroY = centroAgua(ancho, alto);
        int factor = factorEscenario(diametro);
        int factorRana = factorRana(factor);
        int relleno = diametro * 3 / 4;

        double progreso = progreso();
        PasoRana movida = movida();
        char colorMovida = movida == null ? 0 : casillas[movida.casillaDestino()];

        // Escenario: la piedra solo en la casilla central, nenufar en las otras
        // seis. El factor se comparte para que los dos sprites caigan en la
        // misma grilla de pixeles y no se desfasen entre si al redimensionar.
        for (int casilla = 0; casilla < Constantes.CASILLAS_TOTAL; casilla++) {
            int centroX = centroDe(casilla, separacion);
            String nombre = casilla == CENTRO ? "piedra" : "nenufar";
            if (!Imagenes.dibujarCentrado(g2, nombre, centroX, centroY, factor)) {
                g2.setColor(UIConstants.CASILLA_ALT);
                g2.fillOval(centroX - diametro / 2, centroY - diametro / 2, diametro, diametro);
            }
        }

        // Ranitas quietas. La que viaja se dibuja aparte en su posicion del paso.
        for (int casilla = 0; casilla < Constantes.CASILLAS_TOTAL; casilla++) {
            if (movida != null && casilla == movida.casillaDestino()) {
                continue;
            }
            char rana = casillas[casilla];
            if (rana == SaltoRana.CASILLA_VACIA) {
                continue;
            }
            int centroX = centroDe(casilla, separacion);
            String sprite = sprite(rana, false, miraIzquierda(rana, casilla));
            dibujarRana(g2, sprite, centroX, centroY, factorRana, relleno,
                    colorDe(rana));
        }

        // La rana que se mueve en este paso.
        if (movida != null) {
            int origenX = centroDe(movida.casillaOrigen(), separacion);
            int destinoX = centroDe(movida.casillaDestino(), separacion);
            int distancia = Math.abs(movida.casillaDestino() - movida.casillaOrigen());

            // Fases de 800 ms (4 de 200): 0..200 quieta, 200..400 sube saltando,
            // 400..600 baja tambien en la pose del salto, 600..800 queda.
            boolean saltando = progreso >= 0.25 && progreso < 0.75;
            double avance = Math.min(Math.max((progreso - 0.25) / 0.5, 0.0), 1.0);
            boolean izquierda = colorMovida == SaltoRana.RANA_CAFE;
            if (avance >= 1.0 && enCasillaFinal(colorMovida, movida.casillaDestino())) {
                // Al aterrizar en su lado ya volta hacia el centro, como las que
                // llevan rato llegadas: es el "cruzan la mirada" del remate.
                izquierda = miraIzquierda(colorMovida, movida.casillaDestino());
            }

            String sprite = sprite(colorMovida, saltando, izquierda);
            double centroX = origenX + avance * (destinoX - origenX);
            double arco = separacion * distancia * PROPORCION_ARCO;
            double piesY = centroY - 4 * arco * avance * (1 - avance);
            dibujarRana(g2, sprite, centroX, piesY, factorRana, relleno,
                    colorDe(colorMovida));
        }
    }

    /** Fila del agua donde va la linea, anclada al rectangulo del cover del fondo. */
    private int centroAgua(int ancho, int alto) {
        int[] rect = Imagenes.rectanguloFondo(ancho, alto, "fondo_rana");
        if (rect == null) {
            return alto / 2;
        }
        return (int) Math.round(rect[1] + AGUA * rect[3]);
    }

    /** Progreso del paso en curso, de 0 a 1, derivado del reloj. */
    private double progreso() {
        if (pasoActual < 0) {
            return 0.0;
        }
        long ahora = System.currentTimeMillis();
        return Math.min(Math.max((ahora - inicioPaso) / (double) duracionPaso(), 0.0), 1.0);
    }

    /** Paso en curso o {@code null} fuera de la animacion. */
    private PasoRana movida() {
        if (pasoActual < 0 || pasoActual >= pasos.size()) {
            return null;
        }
        return pasos.get(pasoActual);
    }

    /**
     * Dibuja una rana anclando el arte por el borde de abajo (los pies).
     *
     * <p>{@code piesY} es la fila donde se apoyan los pies, que para una rana
     * quieta es el centro del nenufar y para la que viaja baja con el arco. Si
     * falta el sprite se dibuja la bola plana de siempre, tambien apoyada.
     */
    private void dibujarRana(Graphics2D g2, String sprite, double centroX, double piesY,
                             int factorRana, int relleno, Color color) {
        if (Imagenes.dibujarCentrado(g2, sprite, centroX,
                piesY - altoArte(factorRana, sprite) / 2.0, factorRana)) {
            return;
        }
        g2.setColor(color);
        g2.fillOval((int) Math.round(centroX - relleno / 2.0),
                (int) Math.round(piesY - relleno), relleno, relleno);
    }

    /**
     * Nombre del sprite de una rana a partir de su especie, la pose y hacia
     * donde mira. Los {@code lookingleft} no son espejo de los {@code lookingright}
     * (cada lado tiene su propio dibujo), y por eso no se voltea nada en codigo.
     */
    private static String sprite(char color, boolean saltando, boolean izquierda) {
        String especie = color == SaltoRana.RANA_VERDE ? "green" : "brown";
        String pose = saltando ? "jumping" : "idle";
        String lado = izquierda ? "lookingleft" : "lookingright";
        return "frog_" + especie + "_" + pose + "_" + lado;
    }

    /**
     * Casilla donde termina cada rana: las verdes a la derecha del centro y las
     * cafes a la izquierda. Ahi es donde se quedan mirando hacia el centro.
     */
    private static boolean enCasillaFinal(char rana, int casilla) {
        return rana == SaltoRana.RANA_VERDE ? casilla > CENTRO : casilla < CENTRO;
    }

    /**
     * Hacia donde mira una rana quieta: por el camino en su direccion de viaje
     * (verde a la derecha, cafe a la izquierda) y al llegar a su lado voltea
     * hacia el centro para cruzar la mirada con las del otro equipo.
     */
    private static boolean miraIzquierda(char rana, int casilla) {
        boolean haciaCentro = enCasillaFinal(rana, casilla);
        if (rana == SaltoRana.RANA_VERDE) {
            return haciaCentro;
        }
        return !haciaCentro;
    }

    /** Alto del arte visible de un sprite ya escalado, para anclar los pies. */
    private static int altoArte(int factor, String sprite) {
        int[] caja = Imagenes.cajaArte(sprite);
        return caja == null ? 0 : (caja[3] - caja[2] + 1) * factor;
    }

    private static Color colorDe(char rana) {
        return rana == SaltoRana.RANA_VERDE ? UIConstants.RANA_VERDE : UIConstants.RANA_CAFE;
    }

    private static int centroDe(int casilla, int separacion) {
        return separacion * (casilla + 1);
    }

    /**
     * Escala entera de los nenufares y de la piedra, nunca menor que 1.
     *
     * <p>Un solo factor para los dos: si cada uno calculara el suyo, al cambiar
     * el tamano de la ventana podrian quedar en factores distintos y la piedra
     * del centro se quedaria mas chica que los nenufares que la rodean.
     */
    private static int factorEscenario(int diametro) {
        return Math.max(1, (int) Math.round(PROPORCION_ESCENARIO * diametro / LADO_ESCENARIO));
    }

    /** Factor entero de las ranas, algo menor que el del escenario. */
    private static int factorRana(int factorEscenario) {
        return Math.max(1, (int) Math.round(PROPORCION_RANA * factorEscenario));
    }
}