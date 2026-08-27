package Algoritmos;

public class BusquedaBinaria {

    public static int buscar(int[] V, int X) {
        int N = V.length - 1;
        int inicio = 0;
        int fin = N;
        boolean band = false;
        int centro = 0;
        while (inicio <= fin && !band) {
            centro = (inicio + fin) / 2;
            if (X == V[centro]) {
                band = true;
            } else if (X < V[centro]) {
                fin = centro - 1;
            } else {
                inicio = centro + 1;
            }
        }
        return band ? centro : -1;
    }
}