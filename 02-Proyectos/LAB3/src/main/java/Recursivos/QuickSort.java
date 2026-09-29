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
        int limite = primero;
        for (int explorado = primero; explorado < ultimo; explorado++) {
            if (valores[explorado] <= pivote) {
                if (limite != explorado) {
                    intercambiar(valores, limite, explorado, primero, ultimo, pasos);
                }
                limite = limite + 1;
            }
        }
        if (limite != ultimo) {
            intercambiar(valores, limite, ultimo, primero, ultimo, pasos);
        }
        return limite;
    }

    private static void intercambiar(double[] valores, int origen, int destino,
                                     int primero, int ultimo, List<PasoQuickSort> pasos) {
        double auxiliar = valores[origen];
        valores[origen] = valores[destino];
        valores[destino] = auxiliar;
        pasos.add(new PasoQuickSort(valores, origen, destino, primero, ultimo));
    }
}
