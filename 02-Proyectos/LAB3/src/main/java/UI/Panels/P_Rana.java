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

        for (int casilla = 0; casilla < Constantes.CASILLAS_TOTAL; casilla++) {
            int centroX = separacion * (casilla + 1);

            // Escenario: la casilla.
            g2.setColor(UIConstants.CASILLA_ALT);
            g2.fillOval(centroX - diametro / 2, centroY - diametro / 2, diametro, diametro);

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
}
