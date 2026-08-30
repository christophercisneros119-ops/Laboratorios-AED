package Algoritmos;

public class Baraja implements MetodoOrdenamiento {

    public void ordenar(int[] V, int N) {
        for (int I = 1; I < N; I++) {
            int aux = V[I];
            int K = I - 1;
            while ((K >= 0) && (aux < V[K])) {
                V[K + 1] = V[K];
                K = K - 1;
            }
            V[K + 1] = aux;
        }
    }
}
