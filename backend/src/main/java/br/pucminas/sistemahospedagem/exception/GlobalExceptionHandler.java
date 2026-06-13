package br.pucminas.sistemahospedagem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler { //nova classe que vai "pegar" todos os erros disparados pelo sistema automaticamente

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({RecursoNaoPermitidoException.class, DataInvalidaException.class})
    public ResponseEntity<String> handleRegrasDeNegocio(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}