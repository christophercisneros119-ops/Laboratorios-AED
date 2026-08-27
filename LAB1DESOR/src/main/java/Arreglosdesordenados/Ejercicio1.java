package Arreglosdesordenados;

import javax.swing.JOptionPane;

/*
*   ENUNCIADO:
*   En una escuela se tienen que almacenar en arreglos los siguientes datos para cada alumno:
*
*   1. Nombre completo
*   2. Numero de semestres cursados
*   3. Calificacion promedio total
*
*   Escriba un programa que, dada la informacion realice las siguientes operaciones:
*
*   1. Dar de alta a un alumno
*   2. Dar de baja a un alumno
*   3. Modificar numero de semestres cursados y promedio total
*   4. Listar nombre, numero de semestre cursado y promedio de un alumno determinado
*   5. Listar todos los registros
*/

public class Ejercicio1 {

    private final String[] nombre;
    private final String[] semestre;
    private final String[] promedio;
    private int indice = -1; // Indice del ultimo elemento (-1 si esta vacio)
    private final int tam;   // Capacidad maxima de los arreglos

    public Ejercicio1(int capacidad) {
        this.tam = capacidad;
        this.nombre = new String[capacidad];
        this.semestre = new String[capacidad];
        this.promedio = new String[capacidad];
    }

    // Dar de alta a un alumno (InsertaDesordenado)
    public boolean darDeAlta(String n, String sem, String prom) {
        if (indice < (tam - 1)) {
            indice++;
            nombre[indice] = n;
            semestre[indice] = sem;
            promedio[indice] = prom;
            JOptionPane.showMessageDialog(null, "Alumno registrado exitosamente.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No hay espacio en el array");
            return false;
        }
    }

    // Dar de baja a un alumno (EliminaDesordenado)
    public boolean darDeBaja(String nombreEliminar) {
        int i = 0;

        while ((i <= indice) && (!nombreEliminar.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice) {
            JOptionPane.showMessageDialog(null, nombreEliminar + " no esta en el array.");
            return false;
        }

        for (int k = i; k < indice; k++) {
            nombre[k] = nombre[k + 1];
            semestre[k] = semestre[k + 1];
            promedio[k] = promedio[k + 1];
        }
        nombre[indice] = null;
        semestre[indice] = null;
        promedio[indice] = null;
        indice--;
        JOptionPane.showMessageDialog(null, "Alumno dado de baja exitosamente.");
        return true;
    }

    // Modificar numero de semestres cursados y promedio total (ModificaDesordenado)
    public boolean modificarAlumno(String n, String nuevoSem, String nuevoProm) {
        int i = 0;

        while ((i <= indice) && (!n.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice) {
            JOptionPane.showMessageDialog(null, n + " no esta en el array.");
            return false;
        }

        semestre[i] = nuevoSem;
        promedio[i] = nuevoProm;
        JOptionPane.showMessageDialog(null, "Datos de " + nombre[i] + " actualizados.");
        return true;
    }

    // Listar nombre, numero de semestre cursado y promedio de un alumno determinado
    public String listarAlumno(String alumno) {
        int i = 0;

        while ((i <= indice) && (!alumno.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice)
            return "No se encontro el alumno " + alumno;

        return String.format("Nombre: %s | Semestres cursados: %s | Promedio total: %s",
                nombre[i], semestre[i], promedio[i]);
    }

    // Listar todos los registros
    public String listarTodos() {
        if (indice == -1)
            return "No hay alumnos registrados.";

        String formatoCadena = "\t|%-22s|%-17s|%-16s|%n";
        String separador     = "\t+----------------------+-----------------+----------------+%n";

        StringBuilder sb = new StringBuilder("\t\t--LISTA DE ALUMNOS--\n\n");
        sb.append(String.format(separador));
        sb.append(String.format(formatoCadena, "Nombre", "Semestres curs.", "Promedio"));
        sb.append(String.format(separador));
        for (int i = 0; i <= indice; i++)
            sb.append(String.format(formatoCadena, nombre[i], semestre[i], promedio[i]));
        sb.append(String.format(separador));

        return sb.toString();
    }
}
