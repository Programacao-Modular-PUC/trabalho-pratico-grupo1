package br.pucminas.sistemahospedagem.notification;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificacaoInternaChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        NotificacaoInternaChannel interna = new NotificacaoInternaChannel();

        assertEquals("INTERNA", interna.getNomeCanal());
    }
}