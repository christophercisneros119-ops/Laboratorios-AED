package Recursivos;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {

    private QuickSort() {}

    public static List<PasoQuickSort> resolver(double[] valores) {
        double[] copia = valores.clone();
        List<PasoQuickSort> pasos = new ArrayList<>();
        ordenar(copia, 0, copia.length - 1, pasos);
        return pasos;
    }

    private static void ordenar(double[] valores, int primero, int ultimo,
                                List<PasoQuickSort> pasos) {
        if (primero >= ultimo) {
            return;
        }
        int limite = particionar(valores, primero, ultimo, pasos);
        ordenar(valores, primero, limite - 1, pasos);
        ordenar(valores, limite + 1, ultimo, pasos);
    }

    // Arma el subrange alrededor del pivote y devuelve el indice donde quedo.
    private static int particionar(double[] valores, int primero, int ultimo,
                                   List<PasoQuickSort> pasos) {
        double pivote = valores[ultimo];
        int[] limite = {primero};
        explorar(valores, primero, primero, ultimo, pivote, limite, pasos);
        if (limite[0] != ultimo) {
            intercambiar(valores, limite[0], ultimo, primero, ultimo, pasos);
        }
        return limite[0];
    }

    /**
     * Rastreo del pivote en diagonal recursivo, sin bucles: compara la casilla
     * {@code explorado} contra el pivote y baja a la siguiente con
     * {@code explorado + 1}. El limite vive en un arreglo de una celda para que
     * la recursion pueda acumularlo sin devolverlo paso a paso.
     */
    private static void explorar(double[] valores, int explorado, int primero,
                                 int ultimo, double pivote, int[] limite,
                                 List<PasoQuickSort> pasos) {
        if (explorado >= ultimo) {
            return;
        }
        if (valores[explorado] <= pivote) {
            if (limite[0] != explorado) {
                intercambiar(valores, limite[0], explorado, primero, ultimo, pasos);
            }
            limite[0] = limite[0] + 1;
        }
        explorar(valores, explorado + 1, primero, ultimo, pivote, limite, pasos);
    }

    private static void intercambiar(double[] valores, int origen, int destino,
                                     int primero, int ultimo, List<PasoQuickSort> pasos) {
        double auxiliar = valores[origen];
        valores[origen] = valores[destino];
        valores[destino] = auxiliar;
        pasos.add(new PasoQuickSort(valores, origen, destino, primero, ultimo));
    }
}
