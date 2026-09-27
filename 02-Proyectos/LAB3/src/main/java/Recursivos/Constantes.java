package Recursivos;

public final class Constantes {

    private Constantes() {}

    public static final int TORRES_TOTAL = 3;
    public static final int TORRE_ORIGEN = 0;
    public static final int TORRE_AUXILIAR = 1;
    public static final int TORRE_DESTINO = 2;
    public static final int DISCOS_MAXIMOS = 7;

    public static final int RANAS_VERDES = 3;
    public static final int RANAS_CAFE = 3;
    public static final int CASILLAS_TOTAL = RANAS_VERDES + RANAS_CAFE + 1;

    // Minimo teorico del problema: n * (n + 2) ranas por lado.
    public static final int MOVIMIENTOS_RANA = RANAS_VERDES * (RANAS_VERDES + 2);

    public static final int LADO_TABLERO = 8;
}
