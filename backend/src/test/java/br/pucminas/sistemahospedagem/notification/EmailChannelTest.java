package br.pucminas.sistemahospedagem.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        EmailChannel email = new EmailChannel();

        assertEquals("EMAIL", email.getNomeCanal());
    }
}