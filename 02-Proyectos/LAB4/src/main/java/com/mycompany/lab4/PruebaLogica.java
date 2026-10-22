package com.mycompany.lab4;

import Estructuras.Cola;
import Estructuras.ColaCircular;
import Estructuras.DesbordamientoException;
import Estructuras.Pila;
import Estructuras.SubdesbordamientoException;
import Polaca.ConversorPolaco;
import Polaca.ExpresionInvalidaException;
import Polaca.ResultadoPolaco;

/**
 * Bateria de pruebas del backend. Se ejecuta desde NetBeans (o con
 * java -cp target/classes com.mycompany.lab4.PruebaLogica) y sirve tambien
 * para demostrar en la defensa que los casos limite estan cubiertos.
 */
public class PruebaLogica {

    private interface Operacion {
        void ejecutar() throws Exception;
    }

    private static int pruebas;
    private static int fallos;

    public static void main(String[] args) {
        probarPila();
        probarCola();
        probarColaCircular();
        probarPolaca();
        System.out.println();
        System.out.println(fallos == 0
                ? "Todas las pruebas pasaron: " + pruebas
                : "FALLARON " + fallos + " de " + pruebas + " pruebas");
        if (fallos > 0) {
            System.exit(1);
        }
    }

    private static void probarPila() {
        System.out.println("== Pila ==");
        Pila pila = new Pila(3);
        verificar(pila.estaVacia(), "nace vacia");
        verificarIgual(-1, pila.tope(), "tope en -1 al nacer");

        try {
            pila.push("A");
            pila.push("B");
            pila.push("C");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   push de tres elementos no desborda");
        }
        verificarIgual(2, pila.tope(), "tope en 2 con tres elementos");
        verificar(pila.estaLlena(), "queda llena con tres elementos");
        verificarIgual("C", pila.cima(), "la cima es el ultimo insertado");
        verificarIgual(java.util.List.of("A", "B", "C"), pila.contenido(),
                "contenido del fondo a la cima");

        verificarExcepcion(DesbordamientoException.class,
                () -> pila.push("D"), "push en pila llena desborda");

        try {
            verificarIgual("C", pila.pop(), "pop devuelve C");
            verificarIgual("B", pila.pop(), "pop devuelve B (LIFO)");
            verificarIgual("A", pila.pop(), "pop devuelve A (LIFO)");
        } catch (SubdesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   pop de tres elementos no subdesborda");
        }
        verificar(pila.estaVacia(), "vuelve a quedar vacia");

        verificarExcepcion(SubdesbordamientoException.class,
                pila::pop, "pop en pila vacia subdesborda");
    }

    private static void probarCola() {
        System.out.println("== Cola ==");
        Cola cola = new Cola(3);
        verificar(cola.estaVacia(), "nace vacia");

        try {
            cola.encolar("A");
            cola.encolar("B");
            cola.encolar("C");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   encolar tres elementos no desborda");
        }
        verificarIgual(0, cola.frente(), "frente en 0");
        verificarIgual(2, cola.fin(), "final en 2");
        verificar(cola.estaLlena(), "queda llena con tres elementos");

        verificarExcepcion(DesbordamientoException.class,
                () -> cola.encolar("D"), "encolar en cola llena desborda");

        try {
            verificarIgual("A", cola.desencolar(), "desencolar devuelve A (FIFO)");
        } catch (SubdesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   desencolar con datos no subdesborda");
        }
        // La cola lineal no reutiliza la celda 0 que libero el frente.
        verificarExcepcion(DesbordamientoException.class,
                () -> cola.encolar("D"), "la cola lineal no reutiliza celdas");

        try {
            verificarIgual("B", cola.desencolar(), "desencolar devuelve B (FIFO)");
            verificarIgual("C", cola.desencolar(), "desencolar devuelve C (FIFO)");
        } catch (SubdesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   desencolar el resto no subdesborda");
        }
        verificarIgual(-1, cola.frente(), "frente en -1 al vaciarse");
        verificarIgual(-1, cola.fin(), "final en -1 al vaciarse");

        verificarExcepcion(SubdesbordamientoException.class,
                cola::desencolar, "desencolar cola vacia subdesborda");

        try {
            cola.encolar("X");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   encolar despues de vaciarse");
        }
        verificarIgual(0, cola.frente(), "reencola desde la celda 0");
    }

    private static void probarColaCircular() {
        System.out.println("== Cola circular ==");
        ColaCircular cola = new ColaCircular(5);
        verificar(cola.estaVacia(), "nace vacia");

        try {
            cola.encolar("A");
            cola.encolar("B");
            cola.encolar("C");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   encolar tres elementos no desborda");
        }
        verificarIgual(0, cola.frente(), "frente en 0");
        verificarIgual(2, cola.fin(), "final en 2");

        try {
            verificarIgual("A", cola.desencolar(), "desencolar devuelve A");
        } catch (SubdesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   desencolar con datos no subdesborda");
        }

        try {
            cola.encolar("D");
            cola.encolar("E");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   encolar hasta el tope no desborda");
        }
        verificarIgual(4, cola.fin(), "final en la ultima celda");

        try {
            cola.encolar("F");
        } catch (DesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   el envoltorio del final a 0");
        }
        verificarIgual(0, cola.fin(), "el final envuelve a la celda 0");
        verificarIgual(5, cola.tamano(), "cuenta los elementos envueltos");
        verificar(cola.estaLlena(), "llena con envoltorio");
        verificar(cola.esActiva(0), "la celda 0 envuelta esta activa");
        verificar(cola.esActiva(1), "la celda del frente esta activa");

        verificarExcepcion(DesbordamientoException.class,
                () -> cola.encolar("G"), "encolar en circular llena desborda");

        try {
            verificarIgual("B", cola.desencolar(), "desencolar B tras el envoltorio");
            verificar(!cola.esActiva(1), "la celda liberada deja de ser activa");
            verificarIgual("B", cola.obtener(1), "la celda liberada guarda basura");
            verificarIgual("C", cola.desencolar(), "desencolar C tras el envoltorio");
            verificarIgual("D", cola.desencolar(), "desencolar D");
            verificarIgual("E", cola.desencolar(), "desencolar E");
            verificarIgual("F", cola.desencolar(), "desencolar F desde la celda 0");
        } catch (SubdesbordamientoException excepcion) {
            fallos++;
            System.out.println("FALLO   vaciar la circular tras el envoltorio");
        }
        verificar(cola.estaVacia(), "circular vacia tras vaciarla");

        verificarExcepcion(SubdesbordamientoException.class,
                cola::desencolar, "desencolar circular vacia subdesborda");
    }

    private static void probarPolaca() {
        System.out.println("== Notacion polaca ==");
        verificarPostfija("A+B*C", "ABC*+");
        verificarPostfija("A+B*C-D", "ABC*+D-");
        verificarPostfija("(A+B)*C", "AB+C*");
        verificarPostfija("A^B^C", "AB^C^");
        // La ^ con el <= del pseudocodigo sale asociativa por la izquierda.
        verificarPostfija("A + B", "AB+");
        verificarPrefija("A+B", "+AB");
        verificarPrefija("A+B*C", "+A*BC");
        verificarPrefija("(A+B)*C", "*+ABC");
        verificarPrefija("A+B*C-D", "-+A*BCD");

        try {
            ResultadoPolaco resultado = ConversorPolaco.aPostfija("A+B");
            verificarIgual(4, resultado.pasos().size(), "la traza de A+B tiene 4 pasos");
            verificarIgual("AB+", resultado.pasos().get(3).salida(),
                    "el ultimo paso ya tiene la salida completa");
            verificarIgual("A+B", resultado.infija(), "guarda la infija sin espacios");
        } catch (ExpresionInvalidaException excepcion) {
            fallos++;
            System.out.println("FALLO   la traza de una postfija valida");
        }

        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPostfija("a+b"), "rechaza minusculas");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPostfija(""), "rechaza vacia");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPostfija("A+"), "rechaza fin en operador");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPostfija("(A+B"), "rechaza parentesis sin cerrar");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPrefija("A+B)"), "rechaza parentesis sobrante");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPrefija("()"), "rechaza parentesis vacio");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPrefija("AB"), "rechaza operandos pegados");
        verificarExcepcion(ExpresionInvalidaException.class,
                () -> ConversorPolaco.aPrefija("A++B"), "rechaza operadores pegados");
    }

    private static void verificarPostfija(String infija, String esperada) {
        try {
            verificarIgual(esperada, ConversorPolaco.aPostfija(infija).resultado(),
                    "postfija de " + infija);
        } catch (ExpresionInvalidaException excepcion) {
            fallos++;
            System.out.println("FALLO   postfija de " + infija + ": " + excepcion.getMessage());
        }
    }

    private static void verificarPrefija(String infija, String esperada) {
        try {
            verificarIgual(esperada, ConversorPolaco.aPrefija(infija).resultado(),
                    "prefija de " + infija);
        } catch (ExpresionInvalidaException excepcion) {
            fallos++;
            System.out.println("FALLO   prefija de " + infija + ": " + excepcion.getMessage());
        }
    }

    private static void verificar(boolean condicion, String caso) {
        pruebas++;
        if (condicion) {
            System.out.println("OK      " + caso);
        } else {
            fallos++;
            System.out.println("FALLO   " + caso);
        }
    }

    private static void verificarIgual(Object esperado, Object obtenido, String caso) {
        pruebas++;
        if (esperado.equals(obtenido)) {
            System.out.println("OK      " + caso);
        } else {
            fallos++;
            System.out.println("FALLO   " + caso + ": esperado " + esperado
                    + ", obtenido " + obtenido);
        }
    }

    private static void verificarExcepcion(Class<?> tipo, Operacion operacion, String caso) {
        pruebas++;
        try {
            operacion.ejecutar();
            fallos++;
            System.out.println("FALLO   " + caso + " (no lanzo " + tipo.getSimpleName() + ")");
        } catch (Exception excepcion) {
            if (tipo.isInstance(excepcion)) {
                System.out.println("OK      " + caso + ": " + excepcion.getMessage());
            } else {
                fallos++;
                System.out.println("FALLO   " + caso + " (lanzo "
                        + excepcion.getClass().getSimpleName() + ")");
            }
        }
    }
}
