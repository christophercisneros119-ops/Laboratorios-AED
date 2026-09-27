package Recursivos;

public record PasoRana(int casillaOrigen, int casillaDestino) {

    public String descripcion() {
        return "Rana: casilla " + casillaOrigen + " -> casilla " + casillaDestino;
    }
}
