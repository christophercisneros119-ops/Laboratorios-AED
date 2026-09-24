package Algoritmos;

public class BusquedaBinaria {

    public static int buscar(int[] V, int N, int X) {
        int inicio = 0;
        int fin = N - 1;
        while (inicio <= fin) {
            int centro = (inicio + fin) / 2;
            if (X == V[centro]) return centro;
            if (X < V[centro]) fin = centro - 1;
            else inicio = centro + 1;
        }
        return -1;
    }
}
