package Exercises;

import javax.swing.*;

/*
*   ENUNCIADO:
*   Una empresa registra para cada uno de sus clientes los siguientes datos:
*
*   1. Nombre
*   2. Telefono
*   3. Saldo
*   4. Moroso (BOOLEAN)
*
*   Escriba un programa que pueda proporcionar la siguiente informacion:
*
*   1. Dar de alta a un cliente
*   2. Modificar el estado de "Moroso" del cliente
*   3. Dar de baja a un cliente
*   4. Listar la informacion de un cliente determinado
*   5. Listar la informacion de todos los clientes
*/

public class Exercise2UNORDERED {

    private final String[] nombre;
    private final String[] telf;
    private final String[] saldo;
    private final boolean[] moroso;
    private int indice = -1; // Indice del ultimo elemento (-1 si esta vacio)
    private final int tam;   // Capacidad maxima de los arreglos

    public Exercise2UNORDERED(int capacidad) {
        this.tam = capacidad;
        this.nombre = new String[capacidad];
        this.telf = new String[capacidad];
        this.saldo = new String[capacidad];
        this.moroso = new boolean[capacidad];
    }

    // Dar de alta a un cliente (InsertaDesordenado)
    public boolean darDeAlta(String n, String t, String s) {
        if (indice < (tam - 1)) {
            indice++;
            nombre[indice] = n;
            telf[indice] = t;
            saldo[indice] = s;
            moroso[indice] = false;
            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Base de datos llena");
            return false;
        }
    }

    // Modificar el estado de "Moroso" del cliente (ModificaDesordenado)
    public boolean modificarEstado(String n, boolean esMoroso) {
        int i = 0;

        while ((i <= indice) && (!n.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice) {
            JOptionPane.showMessageDialog(null, n + " no existe.");
            return false;
        }

        moroso[i] = esMoroso;
        JOptionPane.showMessageDialog(null, "Estado de " + nombre[i] + " actualizado.");
        return true;
    }

    // Dar de baja a un cliente (EliminaDesordenado)
    public boolean darDeBaja(String nombreEliminar) {
        int i = 0;

        while ((i <= indice) && (!nombreEliminar.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice) {
            JOptionPane.showMessageDialog(null, nombreEliminar + " no existe.");
            return false;
        }

        for (int k = i; k < indice; k++) {
            nombre[k] = nombre[k + 1];
            telf[k] = telf[k + 1];
            saldo[k] = saldo[k + 1];
            moroso[k] = moroso[k + 1];
        }
        indice--;
        JOptionPane.showMessageDialog(null, "Cliente dado de baja exitosamente.");
        return true;
    }

    // Listar la informacion de un cliente determinado
    public String listarCliente(String cliente) {
        int i = 0;

        while ((i <= indice) && (!cliente.equalsIgnoreCase(nombre[i])))
            i++;

        if (i > indice)
            return "No se encontro";

        return String.format("%s - %s - %s - %b", nombre[i], telf[i], saldo[i], moroso[i]);
    }

    // Listar la informacion de todos los clientes
    public String listarClientes() {
        if (indice == -1)
            return "No hay clientes registrados.";

        StringBuilder sb = new StringBuilder("--LISTA DE CLIENTES--\n\n");
        for (int i = 0; i <= indice; i++)
            sb.append(String.format("%s - %s - %s - %b%n", nombre[i], telf[i], saldo[i], moroso[i]));

        return sb.toString();
    }
}
