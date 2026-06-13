package br.pucminas.sistemahospedagem.exception;

public class RecursoNaoPermitidoException extends RuntimeException {
    public RecursoNaoPermitidoException(String message) {
        super(message);
    }
}