package Ejercicio3;

import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Locale;
 
/**
 * Clase que contiene los campos de cada registro del ArrayList.
 */
class Empleado {
 
    private final String nombre;
    private final int numeroEmpleado;
    private final double salario;
 
    public Empleado(String nombre, int numeroEmpleado, double salario) {
        this.nombre = nombre;
        this.numeroEmpleado = numeroEmpleado;
        this.salario = salario;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }
 
    public double getSalario() {
        return salario;
    }
}
 
/**
 * Clase que contiene el ArrayList y los métodos: insertar, buscar,
 * eliminar, imprimir y calcular la nomina total.
 */
class Nomina {
 
    private final ArrayList<Empleado> empleados = new ArrayList<>();
 
    // Formatea un monto
    private String moneda(double monto) {
        return String.format(Locale.US, "C$ %,.2f",monto);
    }
 
    public boolean estaVacia() {
        return empleados.isEmpty();
    }
 
    // Devuelve la posición del empleado en el ArrayList o -1 si no existe
    public int buscarIndice(int numeroEmpleado) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getNumeroEmpleado() == numeroEmpleado) {
                return i;
            }
        }
        return -1;
    }
 
    // Insertar un registro(no permite numeros de empleado repetidos)
    public boolean insertar(Empleado e) {
        if (buscarIndice(e.getNumeroEmpleado()) != -1) {
            return false;
        }
        empleados.add(e);
        return true;
    }
 
    // Buscar un registro 
    public String buscar(int numeroEmpleado) {
        int pos = buscarIndice(numeroEmpleado);
        if (pos == -1) {
            return null;
        }
        Empleado e = empleados.get(pos);
        return "Registro encontrado en la posición " + pos + "\n\n"
                + String.format("%-12s %s\n", "Nombre:", e.getNombre())
                + String.format("%-12s %d\n", "N° Empleado:", e.getNumeroEmpleado())
                + String.format("%-12s %s\n", "Salario:", moneda(e.getSalario()));
    }
 
    // Eliminar un registro
    public boolean eliminar(int numeroEmpleado) {
        int pos = buscarIndice(numeroEmpleado);
        if (pos == -1) {
            return false;
        }
        empleados.remove(pos);
        return true;
    }
 
    // Lista los registros de todos los empleados
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s %-22s %-12s %15s\n", "", "Nombre", "N° Empleado", "Salario"));
        sb.append("--------------------------------------------------------\n");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            sb.append(String.format("%-4d %-22s %-12d %15s\n",
                    i, e.getNombre(), e.getNumeroEmpleado(), moneda(e.getSalario())));
        }
        return sb.toString();
    }
 
    // Calcular nomina total(suma de todos los salarios)
    public double calcularNominaTotal() {
        double total = 0;
        for (Empleado e : empleados) {
            total += e.getSalario();
        }
        return total;
    }
 
    // Imprimir los salarios y la nomina total
    public String imprimirSalariosYNomina() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-22s %15s\n", "Nombre", "Salario"));
        sb.append("---------------------------------------\n");
        for (Empleado e : empleados) {
            sb.append(String.format("%-22s %15s\n", e.getNombre(), moneda(e.getSalario())));
        }
        sb.append("---------------------------------------\n");
        sb.append(String.format("%-22s %15s\n", "Nomina total:", moneda(calcularNominaTotal())));
        return sb.toString();
    }
}
 
public class Ejercicio3 {
 
    // Muestra un texto dentro de un JTextArea
    private static void mostrarEnTextArea(String titulo, String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new java.awt.Dimension(520, 260));
        JOptionPane.showMessageDialog(null, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
 
    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    // Pide un numero de empleado
    private static Integer pedirNumeroEmpleado(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje);
        if (texto == null) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException ex) {
            mostrarError("Debe ingresar un numero de empleado valido");
            return null;
        }
    }
 
    // Insertar un registro
    private static void insertarRegistro(Nomina nomina) {
        JTextField txtNombre = new JTextField(15);
        JTextField txtNumero = new JTextField(15);
        JTextField txtSalario = new JTextField(15);
 
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("N° Empleado:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Salario (C$):"));
        panel.add(txtSalario);
 
        int respuesta = JOptionPane.showConfirmDialog(null, panel, "Insertar registro",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }
 
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("El nombre no puede estar vacio");
            return;
        }
 
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            double salario = Double.parseDouble(txtSalario.getText().trim());
 
            if (numero <= 0 || salario < 0) {
                mostrarError("El numero de empleado debe ser positivo y el salario no puede ser negativo");
                return;
            }
 
            if (nomina.insertar(new Empleado(nombre, numero, salario))) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
            } else {
                mostrarError("Ya existe un empleado con el numero"+ numero);
            }
        } catch (NumberFormatException ex) {
            mostrarError("N° Empleado y Salario deben ser valores numericos");
        }
    }
 
    // Buscar un registro especifico
    private static void buscarRegistro(Nomina nomina) {
        Integer numero = pedirNumeroEmpleado("Ingrese el N° de empleado a buscar:");
        if (numero == null) {
            return;
        }
        String resultado = nomina.buscar(numero);
        if (resultado == null) {
            mostrarError("No existe un empleado con el número" + numero);
        } else {
            mostrarEnTextArea("Buscar registro", resultado);
        }
    }
 
    // Eliminar un registro especifico
    private static void eliminarRegistro(Nomina nomina) {
        Integer numero = pedirNumeroEmpleado("Ingrese el N° de empleado a eliminar:");
        if (numero == null) {
            return;
        }
        if (nomina.eliminar(numero)) {
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
        } else {
            mostrarError("No existe un empleado con el número"+ numero);
        }
    }
 
    public static void main(String[] args) {
        Nomina nomina = new Nomina();
 
        String menu = """
                      MENU DE EMPLEADO
                      
                      Elija una opcion:
                      
                      1. Insertar un registro
                      2. Buscar un registro especifico
                      3. Eliminar un registro especifico
                      4. Listar los registros de todos los empleados
                      5. Imprimir los salarios y la nomina total
                      6. Salir
                      """;
 
        int opcion = 0;
 
        do {
            String entrada = JOptionPane.showInputDialog(null, menu, "Ejercicio 3-Nomina",
                    JOptionPane.QUESTION_MESSAGE);
 
            // Si cierra o cancela el cuadro,se sale del programa
            if (entrada == null) {
                break;
            }
 
            try {
                opcion = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException ex) {
                mostrarError("Debe ingresar un número del 1 al 6");
                opcion = 0;
                continue;
            }
 
            switch (opcion) {
                case 1 -> insertarRegistro(nomina);
                case 2 -> {
                    if (nomina.estaVacia()) {
                        mostrarError("No hay registros ingresados");
                    } else {
                        buscarRegistro(nomina);
                    }
                }
                case 3 -> {
                    if (nomina.estaVacia()) {
                        mostrarError("No hay registros ingresados");
                    } else {
                        eliminarRegistro(nomina);
                    }
                }
                case 4 -> {
                    if (nomina.estaVacia()) {
                        mostrarError("No hay registros ingresados");
                    } else {
                        mostrarEnTextArea("Lista de empleados",nomina.imprimir());
                    }
                }
                case 5 -> {
                    if (nomina.estaVacia()) {
                        mostrarError("No hay registros ingresados");
                    } else {
                        mostrarEnTextArea("Salarios y nómina total", nomina.imprimirSalariosYNomina());
                    }
                }
                case 6 -> JOptionPane.showMessageDialog(null, "¡Nos vemos luego!");
                default -> mostrarError("Opción invalida. Elija un numero del 1 al 6");
            }
        } while (opcion != 6);
    }
}
