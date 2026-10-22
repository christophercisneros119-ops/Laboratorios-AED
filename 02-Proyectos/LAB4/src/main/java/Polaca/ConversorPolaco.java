package Polaca;

import Estructuras.DesbordamientoException;
import Estructuras.Pila;
import Estructuras.SubdesbordamientoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Conversion de expresiones infijas a notacion polaca usando la clase Pila,
 * igual que los pseudocodigos del curso. La postfija lee de izquierda a
 * derecha y vacia la pila por prioridad menor o igual; la prefija lee de
 * derecha a izquierda, compara con prioridad estrictamente menor y escribe
 * su salida invertida.
 *
 * <p>Cada paso se registra en la traza con el simbolo leido, el contenido de
 * la pila y la salida acumulada, que es lo que la guia pide ir imprimiendo.
 */
public final class ConversorPolaco {

    private ConversorPolaco() {}

    /** Convierte la expresion infija a notacion postfija, con su traza. */
    public static ResultadoPolaco aPostfija(String infija) throws ExpresionInvalidaException {
        String expresion = validar(infija);
        List<PasoPolaco> pasos = new ArrayList<>();
        Pila pila = new Pila(expresion.length());
        StringBuilder salida = new StringBuilder();
        int numero = 1;

        for (int i = 0; i < expresion.length(); i++) {
            String simbolo = expresion.substring(i, i + 1);
            String nota;
            if (simbolo.equals("(")) {
                apilar(pila, simbolo);
                nota = "apila el parentesis";
            } else if (simbolo.equals(")")) {
                while (!pila.estaVacia() && !pila.cima().equals("(")) {
                    salida.append(sacar(pila));
                }
                sacar(pila);
                nota = "desapila hasta el parentesis";
            } else if (esOperando(simbolo)) {
                salida.append(simbolo);
                nota = "pasa directo a la salida";
            } else {
                boolean saco = false;
                while (!pila.estaVacia()
                        && prioridad(simbolo) <= prioridad(pila.cima())) {
                    salida.append(sacar(pila));
                    saco = true;
                }
                apilar(pila, simbolo);
                nota = saco ? "desapila por prioridad" : "apila el operador";
            }
            pasos.add(new PasoPolaco(numero++, simbolo, pila.contenido(),
                    salida.toString(), nota));
        }

        while (!pila.estaVacia()) {
            salida.append(sacar(pila));
            pasos.add(new PasoPolaco(numero++, " ", pila.contenido(),
                    salida.toString(), "vacia la pila al final"));
        }
        return new ResultadoPolaco(expresion, salida.toString(), pasos);
    }

    /** Convierte la expresion infija a notacion prefija, con su traza. */
    public static ResultadoPolaco aPrefija(String infija) throws ExpresionInvalidaException {
        String expresion = validar(infija);
        List<PasoPolaco> pasos = new ArrayList<>();
        Pila pila = new Pila(expresion.length());
        StringBuilder salida = new StringBuilder();
        int numero = 1;

        for (int i = expresion.length() - 1; i >= 0; i--) {
            String simbolo = expresion.substring(i, i + 1);
            String nota;
            if (simbolo.equals(")")) {
                apilar(pila, simbolo);
                nota = "apila el parentesis";
            } else if (simbolo.equals("(")) {
                while (!pila.estaVacia() && !pila.cima().equals(")")) {
                    salida.append(sacar(pila));
                }
                sacar(pila);
                nota = "desapila hasta el parentesis";
            } else if (esOperando(simbolo)) {
                salida.append(simbolo);
                nota = "pasa directo a la salida";
            } else {
                boolean saco = false;
                while (!pila.estaVacia()
                        && prioridad(simbolo) < prioridad(pila.cima())) {
                    salida.append(sacar(pila));
                    saco = true;
                }
                apilar(pila, simbolo);
                nota = saco ? "desapila por prioridad" : "apila el operador";
            }
            pasos.add(new PasoPolaco(numero++, simbolo, pila.contenido(),
                    salida.toString(), nota));
        }

        while (!pila.estaVacia()) {
            salida.append(sacar(pila));
            pasos.add(new PasoPolaco(numero++, " ", pila.contenido(),
                    salida.toString(), "vacia la pila al final"));
        }

        // La prefija se construye al reves: la salida se invierte al final.
        String resultado = salida.reverse().toString();
        pasos.add(new PasoPolaco(numero, " ", List.of(), resultado,
                "invierte la salida"));
        return new ResultadoPolaco(expresion, resultado, pasos);
    }

    /** Push del conversor: no puede desbordar porque la pila nace con espacio de sobra. */
    private static void apilar(Pila pila, String simbolo) {
        try {
            pila.push(simbolo);
        } catch (DesbordamientoException imposible) {
            throw new IllegalStateException(imposible);
        }
    }

    /** Pop del conversor: no puede subdesbordar porque todo sacado revisa estaVacia antes. */
    private static String sacar(Pila pila) {
        try {
            return pila.pop();
        } catch (SubdesbordamientoException imposible) {
            throw new IllegalStateException(imposible);
        }
    }

    /** Prioridad de los operadores, igual que el pseudocodigo del curso. */
    private static int prioridad(String operador) {
        return switch (operador) {
            case "^" -> 4;
            case "*", "/" -> 3;
            case "+", "-" -> 2;
            default -> 1;
        };
    }

    private static boolean esOperando(String simbolo) {
        return simbolo.length() == 1 && simbolo.charAt(0) >= 'A' && simbolo.charAt(0) <= 'Z';
    }

    private static boolean esOperando(char simbolo) {
        return simbolo >= 'A' && simbolo <= 'Z';
    }

    private static boolean esOperador(char simbolo) {
        return simbolo == '^' || simbolo == '*' || simbolo == '/' || simbolo == '+'
                || simbolo == '-';
    }

    /**
     * Quita los espacios y revisa que la expresion sea una infija bien
     * formada: operandos A-Z, operadores validos, parentesis balanceados y
     * alternancia correcta entre operandos y operadores. Sin esto, los
     * algoritmos de conversion se saldrian del arreglo.
     */
    private static String validar(String infija) throws ExpresionInvalidaException {
        if (infija == null || infija.isBlank()) {
            throw new ExpresionInvalidaException("escriba una expresion infija");
        }
        String expresion = infija.replace(" ", "");
        int parentesis = 0;
        char anterior = 0;
        for (int i = 0; i < expresion.length(); i++) {
            char simbolo = expresion.charAt(i);
            String error = null;
            if (esOperando(simbolo)) {
                if (anterior != 0 && (esOperando(anterior) || anterior == ')')) {
                    error = "falta operador entre dos operandos";
                }
            } else if (esOperador(simbolo)) {
                if (anterior == 0) {
                    error = "no puede empezar con operador";
                } else if (anterior == '(' || esOperador(anterior)) {
                    error = "operador sin operando a su izquierda";
                }
            } else if (simbolo == '(') {
                if (anterior != 0 && (esOperando(anterior) || anterior == ')')) {
                    error = "falta operador antes del parentesis";
                }
                parentesis++;
            } else if (simbolo == ')') {
                if (anterior == 0) {
                    error = "no puede empezar con parentesis de cierre";
                } else if (anterior == '(') {
                    error = "parentesis vacio";
                } else if (esOperador(anterior)) {
                    error = "operador sin operando a su derecha";
                } else {
                    parentesis--;
                    if (parentesis < 0) {
                        error = "sobra un parentesis de cierre";
                    }
                }
            } else {
                error = "caracter invalido: " + simbolo + " (solo A-Z y operadores)";
            }
            if (error != null) {
                throw new ExpresionInvalidaException(
                        error + " en la posicion " + (i + 1));
            }
            anterior = simbolo;
        }
        if (parentesis != 0) {
            throw new ExpresionInvalidaException("hay parentesis sin cerrar");
        }
        if (esOperador(anterior)) {
            throw new ExpresionInvalidaException("termina con operador");
        }
        if (anterior == '(') {
            throw new ExpresionInvalidaException("hay parentesis sin cerrar");
        }
        return expresion;
    }
}
