package Algoritmos;

public class Baraja implements IMetodoOrdenamiento {

    @Override
    public void ordenar(int[] V) {
        int N = V.length - 1;
        for (int I = 1; I <= N; I++) {
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