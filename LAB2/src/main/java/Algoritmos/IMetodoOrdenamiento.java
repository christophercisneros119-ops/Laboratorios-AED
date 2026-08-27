package Algoritmos;

public interface IMetodoOrdenamiento {

    void ordenar(int[] arreglo);

    static int[] leerArreglo(String texto) {
        String[] partes = texto.split(",");
        int[] arreglo = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            arreglo[i] = Integer.parseInt(partes[i].trim());
        }
        return arreglo;
    }

    static String formatear(int[] arreglo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arreglo.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arreglo[i]);
        }
        return sb.toString();
    }
}