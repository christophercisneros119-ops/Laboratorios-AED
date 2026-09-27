package Recursivos;

public record PasoQuickSort(int[] valores, int indiceOrigen, int indiceDestino,
                            int primero, int ultimo) {

    public PasoQuickSort {
        valores = valores.clone();
    }

    public String descripcion() {
        return "Intercambio de las posiciones " + indiceOrigen + " y " + indiceDestino;
    }
}
