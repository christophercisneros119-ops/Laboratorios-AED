package Arreglosordenados;

import javax.swing.JOptionPane;

/*
*   ENUNCIADO:
*   Una compania quiere almacenar en arreglos ordenados la siguiente informacion
*   para cada vendedor, la cual consiste en el nombre y total de ventas. Inicialmente los arreglos estan vacios.
*
*   Escribir un programa que permita realizar lo siguiente:
*
*   1. Dar de alta a un vendedor
*   2. Modificar el total de ventas de un vendedor determinado.
*   3. Imprimir el registro de un vendedor determinado.
*
*   Los arreglos deberan estar ordenados por el nombre del vendedor.
*/

public class Ejercicio6 {

    private final String[] nombre;
    private final String[] ventas;
    private int indice = -1; // Indice del ultimo elemento (-1 si esta vacio)
    private final int tam;   // Capacidad maxima de los arreglos

    public Ejercicio6(int capacidad) {
        this.tam = capacidad;
        this.nombre = new String[capacidad];
        this.ventas = new String[capacidad];
    }

    // Busca(V, N, X)
    public int BUSCA(String[] arr, int indice, String aBuscar) {
        int i = 0;

        while ((i <= indice) && (arr[i].compareTo(aBuscar) < 0)) {
            i++;
        }

        if ((i > indice) || (arr[i].compareTo(aBuscar) > 0))
            return -i;
        else
            return i;
    }

    // Dar de alta a un vendedor (InsertaOrdenado)
    public boolean darDeAlta(String n, String v) {
        if (indice >= (tam - 1)) {
            JOptionPane.showMessageDialog(null, "No hay espacio en el array");
            return false;
        }

        int pos = BUSCA(nombre, indice, n);
        if (pos >= 0 && n.equals(nombre[pos])) {
            JOptionPane.showMessageDialog(null, n + " ya existe");
            return false;
        }

        indice++;
        pos = Math.abs(pos);
        for (int i = indice; i >= pos + 1; i--) {
            nombre[i] = nombre[i - 1];
            ventas[i] = ventas[i - 1];
        }
        nombre[pos] = n;
        ventas[pos] = v;
        JOptionPane.showMessageDialog(null, "Vendedor registrado exitosamente.");
        return true;
    }

    // Modificar el total de ventas de un vendedor determinado
    // (Busca sobre la clave de ordenamiento + asignacion en el arreglo paralelo,
    //  el nombre no se altera por lo que el orden se conserva)
    public boolean modificarVentas(String n, String nuevasVentas) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return false;
        }

        int pos = BUSCA(nombre, indice, n);
        if (!(pos >= 0 && n.equals(nombre[pos]))) {
            JOptionPane.showMessageDialog(null, n + " no existe");
            return false;
        }

        ventas[pos] = nuevasVentas;
        JOptionPane.showMessageDialog(null, "Ventas de " + nombre[pos] + " actualizadas.");
        return true;
    }

    // Imprimir el registro de un vendedor determinado
    public String imprimirVendedor(String n) {
        if (indice < 0)
            return "No hay vendedores registrados.";

        int pos = BUSCA(nombre, indice, n);
        if (!(pos >= 0 && n.equals(nombre[pos])))
            return "No se encontro el vendedor " + n;

        return String.format("Nombre: %s | Total de ventas: %s", nombre[pos], ventas[pos]);
    }
}
