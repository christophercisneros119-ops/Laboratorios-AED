package Recursivos;

public record PasoReina(int fila, int columna) {

    public String descripcion() {
        return "Reina " + (fila + 1) + " de " + Constantes.LADO_TABLERO
                + " en (" + fila + ", " + columna + ")";
    }
}
