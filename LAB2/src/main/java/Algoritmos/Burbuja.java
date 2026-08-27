package Algoritmos;

public class Burbuja implements IMetodoOrdenamiento {

    @Override
    public void ordenar(int[] A) {
        int N = A.length;
        for (int P = 1; P < N; P++) {
            for (int I = 0; I < N - P; I++) {
                if (A[I] > A[I + 1]) {
                    int temp = A[I];
                    A[I] = A[I + 1];
                    A[I + 1] = temp;
                }
            }
        }
    }
}