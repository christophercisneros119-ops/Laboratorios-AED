package Polaca;

import Estructuras.DesbordamientoException;
import Estructuras.Pila;
import Estructuras.SubdesbordamientoException;

import java.util.ArrayList;
import java.util.List;

/** Conversion infija a prefija y postfija con una Pila, como el pseudocodigo del curso. */
public final class ConversorPolaco {

    private ConversorPolaco() {}

    // Postfija: lee izquierda a derecha y vacia la pila por prioridad <=.
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

    // Prefija: lee derecha a izquierda con prioridad < y salida invertida.
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

        String resultado = salida.reverse().toString();
        pasos.add(new PasoPolaco(numero, " ", List.of(), resultado,
                "invierte la salida"));
        return new ResultadoPolaco(expresion, resultado, pasos);
    }

    // Push que no puede desbordar: la pila nace con espacio de sobra.
    private static void apilar(Pila pila, String simbolo) {
        try {
            pila.push(simbolo);
        } catch (DesbordamientoException imposible) {
            throw new IllegalStateException(imposible);
        }
    }

    // Pop que no puede subdesbordar: todo sacado revisa estaVacia antes.
    private static String sacar(Pila pila) {
        try {
            return pila.pop();
        } catch (SubdesbordamientoException imposible) {
            throw new IllegalStateException(imposible);
        }
    }

    // Prioridades del pseudocodigo del curso.
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

    // Quita espacios y revisa formato infija: caracteres, parentesis y alternancia.
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
