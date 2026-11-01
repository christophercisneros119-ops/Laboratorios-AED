package Arboles;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class ArbolBinario {

    public static final int MAX_NIVEL = 5;

    private Nodo raiz;

    public ArbolBinario() {
        this(null);
    }

    public ArbolBinario(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void limpiar() {
        raiz = null;
    }

    public int altura() {
        return altura(raiz);
    }

    public int altura(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(altura(nodo.getIzq()), altura(nodo.getDer()));
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    public int contarNodos(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.getIzq()) + contarNodos(nodo.getDer());
    }

    public int nivelDe(Nodo objetivo) {
        return nivelDe(raiz, objetivo, 1);
    }

    private int nivelDe(Nodo actual, Nodo objetivo, int nivel) {
        if (actual == null || objetivo == null) return 0;
        if (actual == objetivo) return nivel;
        int encontrado = nivelDe(actual.getIzq(), objetivo, nivel + 1);
        if (encontrado != 0) return encontrado;
        return nivelDe(actual.getDer(), objetivo, nivel + 1);
    }

    public boolean puedeTenerHijos(Nodo nodo) {
        if (nodo == null) return false;
        int nivel = nivelDe(nodo);
        return nivel > 0 && nivel < MAX_NIVEL;
    }

    public List<Object> preOrden() {
        List<Object> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return Collections.unmodifiableList(resultado);
    }

    private void preOrden(Nodo nodo, List<Object> resultado) {
        if (nodo == null) return;
        resultado.add(nodo.getDato());
        preOrden(nodo.getIzq(), resultado);
        preOrden(nodo.getDer(), resultado);
    }

    public List<Object> inOrden() {
        List<Object> resultado = new ArrayList<>();
        inOrden(raiz, resultado);
        return Collections.unmodifiableList(resultado);
    }

    private void inOrden(Nodo nodo, List<Object> resultado) {
        if (nodo == null) return;
        inOrden(nodo.getIzq(), resultado);
        resultado.add(nodo.getDato());
        inOrden(nodo.getDer(), resultado);
    }

    public List<Object> postOrden() {
        List<Object> resultado = new ArrayList<>();
        postOrden(raiz, resultado);
        return Collections.unmodifiableList(resultado);
    }

    private void postOrden(Nodo nodo, List<Object> resultado) {
        if (nodo == null) return;
        postOrden(nodo.getIzq(), resultado);
        postOrden(nodo.getDer(), resultado);
        resultado.add(nodo.getDato());
    }

    public List<Object> porNiveles() {
        if (raiz == null) return Collections.emptyList();
        List<Object> resultado = new ArrayList<>();
        Queue<Nodo> cola = new ArrayDeque<>();
        cola.offer(raiz);
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            resultado.add(actual.getDato());
            if (actual.getIzq() != null) cola.offer(actual.getIzq());
            if (actual.getDer() != null) cola.offer(actual.getDer());
        }
        return Collections.unmodifiableList(resultado);
    }
}
