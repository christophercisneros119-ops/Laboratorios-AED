package Arreglosdesordenados;

import javax.swing.*;

/*
ENUNCIADO:

El departamento de personal de una empresa necesita almacenar en arreglos
desordenados el nombre, sexo y edad de cada uno de los empleados adscritos al mismo.
Escriba un programa que realice las siguientes operaciones:

1. Dar de alta a un empleado.
2. Dar de baja a un empleado.
3. Actualizar la edad de un empleado determinado. (Modificar)
4. Imprimir todos los registros de los empleados varones.
5. Imprimir un registro determinado.
6. Salir.

*/

public class Ejercicio3 {
  private final String[] nombres;
    private final char[] sexos;
    private final int[] edades;
    private int N; //Indice del ultimo elemento(-1 si esta vacio)
    private final int Tam; //Capacidad maxima del arreglo

    public Ejercicio3(int capacidad) {
        this.Tam = capacidad;
        this.nombres = new String[capacidad];
        this.sexos = new char[capacidad];
        this.edades = new int[capacidad];
        this.N = -1; //Inicialmente
    }

    //Inserta Desordenado(V, N, Y, Tam)
    public boolean insertaDesordenado(String nombre, char sexo, int edad) {
        if (N < (Tam - 1)) {
            N = N + 1;
            nombres[N] = nombre;
            sexos[N] = Character.toUpperCase(sexo);
            edades[N] = edad;
            return true;
        } else {
            JOptionPane.showMessageDialog(null, nombre + " no puede insertarse. No hay espacio en el array.");
            return false;
        }
    }

    //EliminaDesordenado(V, N, X)
    public boolean eliminaDesordenado(String nombre) {
        int I = 0;
        
        while (I <= N && !nombres[I].equalsIgnoreCase(nombre)) {
            I = I + 1;
        }

        // Si (I > N) No se encontro el valor buscado
        if (I > N) {
            JOptionPane.showMessageDialog(null, nombre + " no está en el array.");
            return false;
        } else {
            //Repetir con K desde I hasta N para desplazar elementos a la izquierda
            for (int K = I; K < N; K++) {
                nombres[K] = nombres[K + 1];
                sexos[K] = sexos[K + 1];
                edades[K] = edades[K + 1];
            }
            
            //Limpiar la ultima celda sobrante
            nombres[N] = null;
            sexos[N] = ' ';
            edades[N] = 0;

            N = N - 1;
            return true;
        }
    }

    //Modifica Desordenado(V, N, X, Y)
    public boolean modificaEdadDesordenado(String nombre, int nuevaEdad) {
        int I = 0;
        
        while (I <= N && !nombres[I].equalsIgnoreCase(nombre)) {
            I = I + 1;
        }

        // Si (I > N) No se encontró
        if (I > N) {
            JOptionPane.showMessageDialog(null, nombre + " no está en el array.");
            return false;
        } else {
            // Hacer V[I] <- Y (actualizar la edad)
            edades[I] = nuevaEdad;
            return true;
        }
    }

    //Imprimir empleados
    public String imprimirVarones() {
        String resultado = "--- EMPLEADOS VARONES ---\n";
        boolean encontrado = false;

        for (int i = 0; i <= N; i++) {
            if (sexos[i] == 'M' || sexos[i] == 'V') {
                resultado += "Nombre: " + nombres[i] + " | Edad: " + edades[i] + "\n";
                encontrado = true;
            }
        }

        return encontrado ? resultado : "No se encontraron empleados varones.";
    }

    //Imprimir registro
    public String imprimirRegistro(String nombre) {
        int I = 0;
        
        while (I <= N && !nombres[I].equalsIgnoreCase(nombre)) {
            I = I + 1;
        }

        if (I > N) {
            return "El empleado no está en el array.";
        } else {
            return """
                   ---DATOS DEL EMPLEADO---
                   Nombre: """ + nombres[I] + "\n" +
                   "Sexo: " + sexos[I] + "\n" +
                   "Edad: " + edades[I];
        }
    }
}