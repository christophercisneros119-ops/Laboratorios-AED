package Algoritmos;

public class BurbujaSenal implements IMetodoOrdenamiento {

    @Override
    public void ordenar(int[] A) {
        int N = A.length;
        int I = 1;
        boolean band = false;
        while (I < N && !band) {
            band = true;
            for (int J = 0; J < N - 1; J++) {
                if (A[J] > A[J + 1]) {
                    int temp = A[J];
                    A[J] = A[J + 1];
                    A[J + 1] = temp;
                    band = false;
                }
            }
            I = I + 1;
        }
    }
}