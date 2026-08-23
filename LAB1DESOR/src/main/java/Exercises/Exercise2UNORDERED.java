package Exercises;

import javax.swing.*;

import UI.Opciones_panel2;

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
*   6. Salir
*/

public class Exercise2UNORDERED {
  public static void main(String[] args) {
    int tam = 10, indice = -1; // Numero de clientes

    String[] nombre = new String[tam], telf = new String[tam], saldo = new String[tam];
    boolean[] moroso = new boolean[tam];

    int opc;

    String menu = """
        Elija una Opcion:

        1. Dar de alta (Cliente)
        2. Modificar estado cliente (Moroso)
        3. Dar de baja a un cliente
        4. Listar la informacion de un cliente
        5. Listar la informacion de todos los clientes
        6. Salir
        """;

    do {
      opc = Integer.parseInt(JOptionPane.showInputDialog(menu));
      switch (opc) {
        case 1 -> {
            indice = darDeAlta(nombre, telf, saldo, moroso, indice, tam);
            break;
        }
        case 2 -> {
            modificarEstado(nombre, moroso, tam);
            break;
        }
        case 3 -> {
            String nombreEliminar = JOptionPane.showInputDialog("Ingrese nombre de la persona a dar de baja");
            indice = darDeBaja(nombre, telf, saldo, moroso, indice, nombreEliminar);
            break;
        }
        case 4 -> {
            String cliente = JOptionPane.showInputDialog("Nombre");
            JOptionPane.showMessageDialog(null, listarCliente(nombre, telf, saldo, moroso, indice, cliente));
            break;
        }
        case 5 -> {
            JOptionPane.showMessageDialog(null, listarClientes(nombre, telf, saldo, moroso, indice));
            break;
        }
        case 6 -> {}
           
      }
    } while (opc != 6);

  }

  // metodos
  public static int darDeAlta(String[] n, String[] t, String[] s, boolean[] moroso, int indice, int tam) {
    if(indice < (tam - 1)) {
        indice++;
        n[indice] = JOptionPane.showInputDialog("Nombre");
        t[indice] = JOptionPane.showInputDialog("Telefono");
        s[indice] = JOptionPane.showInputDialog("Saldo");
        moroso [indice] = false;
    }
    else {
        JOptionPane.showMessageDialog(null, "Base de datos llena");
    }
    return indice;
  }
  
  public static void modificarEstado(String[] n, boolean[] moroso, int tam){
      
      String nombre = JOptionPane.showInputDialog("Ingrese nombre del cliente");
      
      for(int i = 0; i < tam; i++) {
          if(nombre.equalsIgnoreCase(n[i])) {
              int isMoroso = JOptionPane.showConfirmDialog(null, n[i] + "es cliente Moroso");           
              moroso[i] = (isMoroso == JOptionPane.YES_OPTION) ? true : false;
          }
      }
  }
  
  public static int darDeBaja(String[] n, String[] t, String[] s, boolean[] moroso, int indice, String nombreEliminar) {
      int i = 0;
      
      while((i <= indice) && (!nombreEliminar.equalsIgnoreCase(n[i])))
          i++;
      
      if(i > indice)
          JOptionPane.showMessageDialog(null, "No se encontro");
      else {
          for(int k = i; k < indice; k++){
              n[k] = n[k+1];
              t[k] = t[k+1];
              s[k] = s[k+1];
              moroso[k] = moroso[k+1];
          }
      }
      return --indice;
  }
  
  public static String listarCliente(String[] n, String[] t, String[] s, boolean[] moroso, int indice, String cliente){
      String clienteInfo = "";
      int i = 0;
      
      while((i <= indice) && (!cliente.equalsIgnoreCase(n[i])))
          i++;
      
      if(i > indice) 
          return "No se encontro";
      
      return clienteInfo = String.format("%s - %s - %s - %b", n[i], t[i], s[i], moroso[i]);
  }
  
  public static String listarClientes(String[] n, String[] t, String[] s, boolean[] moroso, int indice){
      String clienteInfo = "";
      
      for (int i = 0; i <= indice; i++)
          clienteInfo += String.format("%s - %s - %s - %b\n", n[i], t[i], s[i], moroso[i]);
      
      return clienteInfo;
  }
}
