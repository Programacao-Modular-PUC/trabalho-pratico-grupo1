package br.pucminas.sistemahospedagem.exception;

public class QuartoIndisponivelException extends RuntimeException {
    public QuartoIndisponivelException(Long quartoId) {
        super("Quarto " + quartoId + " não está disponível para o período solicitado.");
    }
}