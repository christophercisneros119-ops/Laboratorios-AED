package Recursivos;

public record PasoHanoi(int numeroDisco, int torreOrigen, int torreDestino) {

    public String descripcion() {
        return "Disco " + numeroDisco + ": torre " + torreOrigen + " -> torre " + torreDestino;
    }
}
