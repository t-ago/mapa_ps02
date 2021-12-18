package Excecao;

public class CapacidadeDesrespeitadaException extends Exception {

    public int qtdOfensiva;

    public CapacidadeDesrespeitadaException(String msg, int qtdOfensiva) {
        super(msg);
        this.qtdOfensiva = qtdOfensiva;
    }
}
