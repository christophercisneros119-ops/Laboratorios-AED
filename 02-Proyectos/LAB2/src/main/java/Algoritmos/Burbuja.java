package Algoritmos;

public class Burbuja implements MetodoOrdenamiento {

    private final boolean conSenal;

    public Burbuja() {
        this.conSenal = false;
    }

    public Burbuja(boolean conSenal) {
        this.conSenal = conSenal;
    }

    public void ordenar(int[] A, int N) {
        if (conSenal) {
            ordenarConSenal(A, N);
        } else {
            ordenarBasico(A, N);
        }
    }

    private void ordenarBasico(int[] A, int N) {
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

    private void ordenarConSenal(int[] A, int N) {
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
