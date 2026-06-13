package br.pucminas.sistemahospedagem.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + " não encontrado(a) com o ID: " + id);
    }
}