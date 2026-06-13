package br.pucminas.sistemahospedagem.exception;

public class CapacidadeExcedidaException extends RuntimeException {
    public CapacidadeExcedidaException(int solicitado, int maximo) {
        super("Número de hóspedes solicitado (" + solicitado + ") excede a capacidade máxima do quarto (" + maximo + ").");
    }
}