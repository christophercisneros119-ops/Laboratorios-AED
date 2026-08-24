package Exercises;
import javax.swing.*;

public class Exercise5ORDERED {
    public static void main(String[] args) {
        int tam = 10, indice = -1, opc;
        String[] ubicacion = new String[tam], area = new String[tam], precio = new String[tam], num_apartamento = new String[tam], arrendatario = new String[tam];
        String menu = """
            Elija una opcion:
            *   1. Dar de alta a un departamento (Se renta y se pide la información)
            *   2. Dar de baja al departamento (Se libera el departamento)
            *   3. Modificar el precio de un departamento por medio de su número.
            *   4. Listar los datos de un departamento determinado.
            *   5. Listar los datos de todos los registros.
            *   6. Salir 
            """;

        do {
            opc = Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (opc) {
                case 1 -> {
                    String areaAIngresar = JOptionPane.showInputDialog("Ingrese el área del departamento");
                    String precioAIngresar = JOptionPane.showInputDialog("Ingrese el precio del departamento");
                    String numAPIngresar = JOptionPane.showInputDialog("Ingrese el número de apartamento");
                    String arrendatarioI = JOptionPane.showInputDialog("Ingrese el nombre del arrendatario");
                    indice = darAltaOrdenado(area, precio, num_apartamento, arrendatario, indice, tam, areaAIngresar, precioAIngresar, numAPIngresar, arrendatarioI);
                }
                case 2 -> {
                    String areaABuscar = JOptionPane.showInputDialog("Ingrese el área del departamento a eliminar");
                    indice = darBajaOrdenado(area, precio, num_apartamento, arrendatario, indice, tam, areaABuscar);
                }
                case 3 -> {
                    String numAPModificar = JOptionPane.showInputDialog("Ingrese el número de apartamento a modificar");
                    String nuevoPrecio = JOptionPane.showInputDialog("Ingrese el nuevo precio");
                    modificarPrecioDepartamentoOrdenado(precio, num_apartamento, indice, tam, numAPModificar, nuevoPrecio);
                }
                case 4 -> {
                    String numAPListar = JOptionPane.showInputDialog("Ingrese el número de apartamento a listar");
                    listarDepartamentoOrdenado(area, precio, num_apartamento, arrendatario, indice, tam, numAPListar);
                }
                case 5 -> {
                    listarDepartamentosOrdenados(area, precio, num_apartamento, arrendatario, indice, tam);
                }
                case 6 -> {}
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Ingrese una opción correcta");
            }
        } while (opc != 6);
    }

    public static int BUSCA(String[] arr, int indice, String aBuscar) {
        int i = 0;
        while ((i <= indice) && (arr[i].compareTo(aBuscar) < 0)) i++;
        if ((i > indice) || (arr[i].compareTo(aBuscar) > 0)) return -i; else return i;
    }

    public static int darAltaOrdenado(String[] area, String[] precio, String[] num_apartamento, 
            String[] arrendatario, int indice, int tam, String newArea, String newPrecio, 
            String newNumAP, String newArrendatario) {
        if (indice >= tam - 1) {
            JOptionPane.showMessageDialog(null, "No hay espacio en el array");
            return indice;
        }
        int pos = BUSCA(area, indice, newArea);
        if (pos >= 0 && newArea.equals(area[pos])) {
            JOptionPane.showMessageDialog(null, "Área ya existe");
            return indice;
        }
        indice++;
        pos = -pos;
        for (int i = indice; i >= pos + 1; i--) {
            area[i] = area[i - 1];
            precio[i] = precio[i - 1];
            num_apartamento[i] = num_apartamento[i - 1];
            arrendatario[i] = arrendatario[i - 1];
        }
        area[pos] = newArea;
        precio[pos] = newPrecio;
        num_apartamento[pos] = newNumAP;
        arrendatario[pos] = newArrendatario;
        return indice;
    }

    public static int darBajaOrdenado(String[] area, String[] precio, String[] num_apartamento, 
            String[] arrendatario, int indice, int tam, String areaABuscar) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return indice;
        }
        int pos = BUSCA(area, indice, areaABuscar);
        if (!(pos >= 0 && areaABuscar.equals(area[pos]))) {
            JOptionPane.showMessageDialog(null, "Área " + areaABuscar + " no existe");
            return indice;
        }
        for (int i = pos; i < indice; i++) {
            area[i] = area[i + 1];
            precio[i] = precio[i + 1];
            num_apartamento[i] = num_apartamento[i + 1];
            arrendatario[i] = arrendatario[i + 1];
        }
        return indice - 1;
    }

    public static void modificarPrecioDepartamentoOrdenado(String[] precio, String[] num_apartamento, 
            int indice, int tam, String numAPBuscar, String nuevoPrecio) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return;
        }
        int i = 0;
        while (i <= indice && !numAPBuscar.equals(num_apartamento[i])) i++;
        if (i > indice) JOptionPane.showMessageDialog(null, "Número no encontrado");
        else precio[i] = nuevoPrecio;
    }

    public static void listarDepartamentoOrdenado(String[] area, String[] precio, String[] num_apartamento, 
            String[] arrendatario, int indice, int tam, String numAPLista) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return;
        }
        int i = 0;
        while (i <= indice && !numAPLista.equals(num_apartamento[i])) i++;
        if (i > indice) {
            JOptionPane.showMessageDialog(null, "Número no encontrado");
            return;
        }
        String datos = "Área: " + area[i] + "\nPrecio: " + precio[i] + "\nNúmero: " + num_apartamento[i] + "\nArrendatario: " + arrendatario[i];
        JOptionPane.showMessageDialog(null, datos);
    }

    public static void listarDepartamentosOrdenados(String[] area, String[] precio, String[] num_apartamento, 
            String[] arrendatario, int indice, int tam) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= indice; i++) {
            sb.append("Área: ").append(area[i])
              .append(" | Precio: ").append(precio[i])
              .append(" | Número: ").append(num_apartamento[i])
              .append(" | Arrendatario: ").append(arrendatario[i])
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
