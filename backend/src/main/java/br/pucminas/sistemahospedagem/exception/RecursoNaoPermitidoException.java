package br.pucminas.sistemahospedagem.exception;

public class RecursoNaoPermitidoException extends RuntimeException {
    public RecursoNaoPermitidoException(String recurso, String tipoQuarto) {
        super("O recurso '" + recurso + "' não é permitido para o tipo de quarto: " + tipoQuarto + ".");
    }
}