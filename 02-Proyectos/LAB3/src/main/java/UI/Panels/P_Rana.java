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

    private List<PasoRana> pasos = List.of();
    private char[] casillas = SaltoRana.estadoInicial();

    public P_Rana() {
        super("Salto de la rana",
                "Las ranas verdes cruzan hacia la derecha y las cafés hacia la izquierda.");
        preparar();
        refrescar();
    }

    @Override
    protected boolean preparar() {
        pasos = SaltoRana.resolver();
        casillas = SaltoRana.estadoInicial();
        return true;
    }

    @Override
    protected int totalPasos() {
        return pasos.size();
    }

    @Override
    protected void aplicarPaso(int indice) {
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
        int centroY = alto / 2;
        int factor = factorEscenario(diametro);

        for (int casilla = 0; casilla < Constantes.CASILLAS_TOTAL; casilla++) {
            int centroX = separacion * (casilla + 1);

            // Escenario: la piedra solo en la casilla central, nenufar en las otras
            // seis. El factor se comparte para que los dos sprites caigan en la
            // misma grilla de pixeles y no se desfasen entre si al redimensionar.
            String nombre = casilla == CENTRO ? "piedra" : "nenufar";
            if (!Imagenes.dibujarCentrado(g2, nombre, centroX, centroY, factor)) {
                g2.setColor(UIConstants.CASILLA_ALT);
                g2.fillOval(centroX - diametro / 2, centroY - diametro / 2, diametro, diametro);
            }

            // Piezas: la rana que ocupa la casilla.
            char rana = casillas[casilla];
            if (rana == SaltoRana.CASILLA_VACIA) {
                continue;
            }
            Color color = rana == SaltoRana.RANA_VERDE
                    ? UIConstants.RANA_VERDE : UIConstants.RANA_CAFE;
            int relleno = diametro * 3 / 4;
            g2.setColor(color);
            g2.fillOval(centroX - relleno / 2, centroY - relleno / 2, relleno, relleno);
        }
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
}
