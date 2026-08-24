package Arraysordenados;

import javax.swing.*;

public class Ejercicio4 {
   private final String[] nombres;
    private final String[] direcciones;
    private final int[] edades;
    private final char[] sexos;
    private final int[] antiguedad;
    private int N; //Indice del ultimo elemento(-1 si esta vacio)
    private final int Tam; //Capacidad maxima del arreglo

    public Ejercicio4(int capacidad) {
        this.Tam = capacidad;
        this.nombres = new String[capacidad];
        this.direcciones = new String[capacidad];
        this.edades = new int[capacidad];
        this.sexos = new char[capacidad];
        this.antiguedad = new int[capacidad];
        this.N = -1; //Inicialmente vacio
    }

    //Busca(V, N, X)
    public int busca(String X) {
    int I = 0;
    while (I <= N && nombres[I].compareToIgnoreCase(X) < 0) {
        I = I + 1;
    }

    if (I > N || nombres[I].compareToIgnoreCase(X) > 0) {
        return -(I + 1); // Si no está, retorna un número menor a cero incluso para la posición 0 (retorna -1)
    } else {
        return I; // Si existe, retorna 0 o mayor
    }
}

    //Dar de alta(Inserta Ordenado)
    public boolean darDeAlta(String nombre, String direccion, int edad, char sexo, int anosAntiguedad) {
    if (N < (Tam - 1)) {
        int pos = busca(nombre);

        if (pos >= 0) {
            JOptionPane.showMessageDialog(null, nombre + " ya existe en el sistema.");
            return false;
        } else {
            N = N + 1;
            pos = (-pos) - 1; // Convierte de vuelta al índice correcto (ej. -1 se convierte en 0)

            for (int I = N; I >= pos + 1; I--) {
                nombres[I] = nombres[I - 1];
                direcciones[I] = direcciones[I - 1];
                edades[I] = edades[I - 1];
                sexos[I] = sexos[I - 1];
                antiguedad[I] = antiguedad[I - 1];
            }

            nombres[pos] = nombre;
            direcciones[pos] = direccion;
            edades[pos] = edad;
            sexos[pos] = Character.toUpperCase(sexo);
            antiguedad[pos] = anosAntiguedad;
            
            JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.");
            return true; // Ahora sí retorna true y refresca el jTextArea1
        }
    } else {
        JOptionPane.showMessageDialog(null, "No hay espacio en el array.");
        return false;
    }
}

    //Dar de baja(Elimina Ordenado)
    public boolean darDeBaja(String nombre) {
        if (N > -1) {
            int pos = busca(nombre);

            if (pos < 0) {
                JOptionPane.showMessageDialog(null, nombre + " no existe.");
                return false;
            } else {
                //Desplazamiento de elementos hacia la izquierda
                for (int I = pos; I < N; I++) {
                    nombres[I] = nombres[I + 1];
                    direcciones[I] = direcciones[I + 1];
                    edades[I] = edades[I + 1];
                    sexos[I] = sexos[I + 1];
                    antiguedad[I] = antiguedad[I + 1];
                }
                
                //Limpiar ultima celda sobrante
                nombres[N] = null;
                direcciones[N] = null;
                edades[N] = 0;
                sexos[N] = ' ';
                antiguedad[N] = 0;
                
                N = N - 1;
                JOptionPane.showMessageDialog(null, "Empleado dado de baja exitosamente.");
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array.");
            return false;
        }
    }

    //Modificar años de antiguedad de un empleado
    public boolean modificarAntiguedad(String nombre, int nuevaAntiguedad) {
        if (N > -1) {
            int pos = busca(nombre);
            
            if (pos < 0) {
                JOptionPane.showMessageDialog(null, nombre + " no existe.");
                return false;
            } else {
                antiguedad[pos] = nuevaAntiguedad;
                JOptionPane.showMessageDialog(null, "Años de antigüedad actualizados.");
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array.");
            return false;
        }
    }

    //Listar los datos de todos los empleados
    public String listarTodos() {
        if (N == -1) {
            return "No hay empleados registrados.";
        }

        StringBuilder sb = new StringBuilder("--LISTA DE EMPLEADOS--\n\n");
        for (int i = 0; i <= N; i++) {
            sb.append("Nombre: ").append(nombres[i]).append("\n")
              .append("Dirección: ").append(direcciones[i]).append("\n")
              .append("Edad: ").append(edades[i]).append("\n")
              .append("Sexo: ").append(sexos[i]).append("\n")
              .append("Antigüedad: ").append(antiguedad[i]).append(" años\n");
        }
        return sb.toString();
    }

    //Listar los datos de un empleado determinado
    public String listarDeterminado(String nombre) {
        if (N > -1) {
            int pos = busca(nombre);
            if (pos >= 0) {
                return """
                       ---DATOS DEL EMPLEADO---
                       Nombre: """ + nombres[pos] + "\n" +
                       "Dirección: " + direcciones[pos] + "\n" +
                       "Edad: " + edades[pos] + "\n" +
                       "Sexo: " + sexos[pos] + "\n" +
                       "Antigüedad: " + antiguedad[pos] + " años";
            }
        }
        return "El empleado no existe en el sistema.";
    }
}