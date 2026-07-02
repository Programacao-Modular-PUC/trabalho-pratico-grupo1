package br.pucminas.sistemahospedagem.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhatsAppChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        WhatsAppChannel whatsapp = new WhatsAppChannel();

        assertEquals("WHATSAPP", whatsapp.getNomeCanal());
    }
}