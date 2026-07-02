package br.pucminas.sistemahospedagem.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SMSChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        SMSChannel sms = new SMSChannel();

        assertEquals("SMS", sms.getNomeCanal());
    }
}