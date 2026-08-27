package Algoritmos;

public class Shell implements IMetodoOrdenamiento {

    @Override
    public void ordenar(int[] A) {
        int N = A.length;
        int ent = N + 1;
        while (ent > 0) {
            ent = (int) (ent / 2);
            boolean band = true;
            while (band) {
                band = false;
                int I = 0;
                while ((I + ent) < N) {
                    if (A[I] > A[I + ent]) {
                        int temp = A[I];
                        A[I] = A[I + ent];
                        A[I + ent] = temp;
                        band = true;
                    }
                    I = I + 1;
                }
            }
        }
    }
}