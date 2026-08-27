package Arreglosordenados;

import javax.swing.*;

/*
*   5. Una inmobiliaria necesita almacenar la siguiente información 
*   sobre los  departamentos rentados que se encuentran 
*   ordenados ascendentemente por la extensión del departamento:   
*   • Ubicación del departamento (dirección)
*   • Extensión del departamento (superficie en metros cuadrados de cada departamento)
*   • Precio
*   • Número de apartamento
*   • Nombre de la persona que rentó el departamento
*
*   Escriba un programa que pueda llevar a cabo las siguientes operaciones.  
*   1. Dar de alta a un departamento (Se renta y se pide la información)
*   2. Dar de baja al departamento (Se libera el departamento)
*   3. Modificar el precio de un departamento por medio de su número.
*   4. Listar los datos de un departamento determinado.
*   5. Listar los datos de todos los registros.
*/

public class Ejercicio5 {

    private final String[] ubicacion;
    private final String[] area;
    private final String[] precio;
    private final String[] num_apartamento;
    private final String[] arrendatario;
    private int indice = -1; // Indice del ultimo elemento (-1 si esta vacio)
    private final int tam;   // Capacidad maxima de los arreglos

    public Ejercicio5(int capacidad) {
        this.tam = capacidad;
        this.ubicacion = new String[capacidad];
        this.area = new String[capacidad];
        this.precio = new String[capacidad];
        this.num_apartamento = new String[capacidad];
        this.arrendatario = new String[capacidad];
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

    // Dar de alta a un departamento (InsertaOrdenado)
    public boolean darAltaOrdenado(String u, String a, String p, String numApt, String arr) {
        if (indice >= (tam - 1)) {
            JOptionPane.showMessageDialog(null, "No hay espacio en el array");
            return false;
        }

        int pos = BUSCA(area, indice, a);
        if (pos >= 0 && a.equals(area[pos])) {
            JOptionPane.showMessageDialog(null, "Area ya existe");
            return false;
        }

        indice++;
        pos = Math.abs(pos);
        for (int i = indice; i >= pos + 1; i--) {
            ubicacion[i] = ubicacion[i - 1];
            area[i] = area[i - 1];
            precio[i] = precio[i - 1];
            num_apartamento[i] = num_apartamento[i - 1];
            arrendatario[i] = arrendatario[i - 1];
        }
        ubicacion[pos] = u;
        area[pos] = a;
        precio[pos] = p;
        num_apartamento[pos] = numApt;
        arrendatario[pos] = arr;
        JOptionPane.showMessageDialog(null, "Registro agregado exitosamente.");
        return true;
    }

    // Dar de baja al departamento (EliminaOrdenado)
    public boolean darBajaOrdenado(String areaABuscar) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return false;
        }

        int pos = BUSCA(area, indice, areaABuscar);
        if (!(pos >= 0 && areaABuscar.equals(area[pos]))) {
            JOptionPane.showMessageDialog(null, "Area " + areaABuscar + " no existe");
            return false;
        }

        for (int i = pos; i < indice; i++) {
            ubicacion[i] = ubicacion[i + 1];
            area[i] = area[i + 1];
            precio[i] = precio[i + 1];
            num_apartamento[i] = num_apartamento[i + 1];
            arrendatario[i] = arrendatario[i + 1];
        }
        ubicacion[indice] = null;
        area[indice] = null;
        precio[indice] = null;
        num_apartamento[indice] = null;
        arrendatario[indice] = null;
        indice--;
        JOptionPane.showMessageDialog(null, "Departamento liberado exitosamente.");
        return true;
    }

    // Modificar el precio de un departamento por medio de su numero
    public boolean modificarPrecioDepartamentoOrdenado(String numApt, String nuevoPrecio) {
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "No hay elementos en el array");
            return false;
        }

        int i = 0;
        while ((i <= indice) && (!numApt.equals(num_apartamento[i])))
            i++;

        if (i > indice) {
            JOptionPane.showMessageDialog(null, "No se encontro el apartamento " + numApt);
            return false;
        }

        precio[i] = nuevoPrecio;
        JOptionPane.showMessageDialog(null, "Precio actualizado.");
        return true;
    }

    // Listar los datos de un departamento determinado
    public String listarDepartamentoOrdenado(String numApt) {
        int i = 0;

        while ((i <= indice) && (!numApt.equals(num_apartamento[i])))
            i++;

        if (i > indice)
            return "No se encontro el apartamento " + numApt;

        String formatoCadena = "\t|%-18s|%-8s|%-10s|%-8s|%-20s|%n";
        String separador     = "\t+------------------+--------+----------+--------+--------------------+%n";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(separador));
        sb.append(String.format(formatoCadena, "Ubicacion", "Area", "Precio", "Numero", "Arrendatario"));
        sb.append(String.format(separador));
        sb.append(String.format(formatoCadena, ubicacion[i], area[i], precio[i], num_apartamento[i], arrendatario[i]));
        sb.append(String.format(separador));

        return sb.toString();
    }

    // Listar los datos de todos los registros
    public String listarDepartamentosOrdenados() {
        if (indice == -1)
            return "No hay departamentos registrados.";
        
        String formatoCadena = "\t|%-18s|%-8s|%-10s|%-8s|%-20s|%n";
        String separador     = "\t+------------------+--------+----------+--------+--------------------+%n";

        StringBuilder sb = new StringBuilder("\t\t--LISTA DE DEPARTAMENTOS--\n\n");
        sb.append(String.format(separador));
        sb.append(String.format(formatoCadena, "Ubicacion", "Area", "Precio", "Numero", "Arrendatario"));
        sb.append(String.format(separador));
        for (int i = 0; i <= indice; i++)
            sb.append(String.format(formatoCadena,
                    ubicacion[i], area[i], precio[i], num_apartamento[i], arrendatario[i]));
        sb.append(String.format(separador));

        return sb.toString();
    }
}
