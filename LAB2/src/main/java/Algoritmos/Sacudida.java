package Algoritmos;

public class Sacudida implements IMetodoOrdenamiento {
    
    public void ordenar(int[] V) {
        int N = V.length;
        int Izq = 0;
        int Der = N - 1;
        boolean band = true;
        while (band && Izq < Der) {
            band = false;
            int K = Izq;
            for (int I = Der; I > Izq; I--) {
                if (V[I - 1] > V[I]) {
                    int temp = V[I - 1];
                    V[I - 1] = V[I];
                    V[I] = temp;
                    band = true;
                    K = I;
                }
            }
            Izq = K;
            K = Der;
            for (int I = Izq; I < Der; I++) {
                if (V[I] > V[I + 1]) {
                    int temp = V[I];
                    V[I] = V[I + 1];
                    V[I + 1] = temp;
                    band = true;
                    K = I;
                }
            }
            Der = K;
        }
    }
}