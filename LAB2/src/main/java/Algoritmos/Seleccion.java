package Algoritmos;

public class Seleccion implements IMetodoOrdenamiento {
    
    public void ordenar(int[] A) {
        int N = A.length - 1;
        for (int I = 0; I < N; I++) {
            int menor = A[I];
            int K = I;
            for (int J = I + 1; J <= N; J++) {
                if (A[J] < menor) {
                    menor = A[J];
                    K = J;
                }
            }
            A[K] = A[I];
            A[I] = menor;
        }
    }
}