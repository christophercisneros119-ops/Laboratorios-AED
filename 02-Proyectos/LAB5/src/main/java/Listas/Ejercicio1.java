package Listas;

import javax.swing.*;
import java.util.ArrayList;

// Principal
public class Ejercicio1 {
    public static void main(String[] args) {
        Listado miListado = new Listado();
        String opcion = "";

        do {
            opcion = JOptionPane.showInputDialog(
                "--- MENÚ DE OPCIONES ---\n" +
                "1. Insertar un alumno\n" +
                "2. Buscar un alumno por carrera\n" +
                "3. Eliminar un alumno\n" +
                "4. Listar los registros de todos los alumnos\n" +
                "5. Salir\n\n" +
                "Seleccione una opción (1-5):"
            );

            if (opcion == null || opcion.equals("5")) {
                break;
            }

            switch (opcion) {
                case "1":
                    String nom = JOptionPane.showInputDialog("Ingrese el Nombre Completo:");
                    String car = JOptionPane.showInputDialog("Ingrese la Carrera:");
                    String cnt = JOptionPane.showInputDialog("Ingrese el Carnet:");
                    
                    Alumno nuevo = new Alumno(nom, car, cnt);
                    miListado.Agregar(nuevo);
                    JOptionPane.showMessageDialog(null, "¡Alumno registrado con éxito!");
                    break;

                case "2":
                    String carreraBuscar = JOptionPane.showInputDialog("Ingrese la carrera a buscar:");
                    miListado.BuscarPorCarrera(carreraBuscar);
                    break;

                case "3":
                    String carnetEliminar = JOptionPane.showInputDialog("Ingrese el carnet del alumno a eliminar:");
                    miListado.Eliminar(carnetEliminar);
                    break;

                case "4":
                    miListado.Imprimir();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }

        } while (!opcion.equals("5"));
    }
}

class Listado {
    ArrayList<Alumno> lista = new ArrayList<Alumno>();

    public void Agregar(Alumno a) {
        lista.add(a);
    }

    public void Eliminar(String carnet) {
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).carnet.equalsIgnoreCase(carnet)) {
                lista.remove(i);
                encontrado = true;
                JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.");
                break;
            }
        }
        if (!encontrado) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún alumno con ese carnet.");
        }
    }

    public void BuscarPorCarrera(String carrera) {
        String resultado = "Alumnos encontrados en " + carrera + ":\n\n";
        resultado += String.format("%-20s %-20s %-15s\n", "Nombre", "Carrera", "Carnet");
        resultado += "---------------------------------------------------------\n";
        
        boolean deLaCarrera = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).carrera.equalsIgnoreCase(carrera)) {
                resultado += String.format("%-20s %-20s %-15s\n", 
                        lista.get(i).nombre, lista.get(i).carrera, lista.get(i).carnet);
                deLaCarrera = true;
            }
        }

        if (deLaCarrera) {
            JTextArea areaTexto = new JTextArea(resultado);
            JOptionPane.showMessageDialog(null, areaTexto, "Búsqueda por Carrera", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay alumnos registrados en esa carrera.");
        }
    }

    public void Imprimir() {
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La lista está vacía.");
            return;
        }

        // Formato estructurado para la tabla final
        String tabla = "Lista de Alumnos\n\n";
        tabla += String.format("%-5s %-25s %-20s %-15s\n", "Id", "Nombre", "Carrera", "Carnet");
        tabla += "-----------------------------------------------------------------------\n";

        for (int i = 0; i < lista.size(); i++) {
            tabla += String.format("%-5d %-25s %-20s %-15s\n", 
                    i, lista.get(i).nombre, lista.get(i).carrera, lista.get(i).carnet);
        }

        // Uso de JTextArea para mantener el alineamiento de columnas intacto
        JTextArea areaTexto = new JTextArea(tabla);
        JOptionPane.showMessageDialog(null, areaTexto, "Registros de Alumnos", JOptionPane.INFORMATION_MESSAGE);
    }
}

// 3. CLASE ALUMNO (Molde de datos requerido)
class Alumno {
    String nombre;
    String carrera;
    String carnet;
    String materia;

    public Alumno(String nombre, String carrera, String carnet) {
        this.nombre = nombre;
        this.carrera = carrera;
        this.carnet = carnet;
        this.materia = "Matemática Básica";
    }
}
