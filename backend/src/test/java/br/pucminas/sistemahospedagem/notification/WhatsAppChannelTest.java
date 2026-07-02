package br.pucminas.sistemahospedagem.notification;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import br.pucminas.sistemahospedagem.notification.observer.WhatsAppChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WhatsAppChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        WhatsAppChannel whatsapp = new WhatsAppChannel();

        assertEquals("WHATSAPP", whatsapp.getNomeCanal());
    }

    @Test
    void deveNotificarSemLancarExcecao() {
        WhatsAppChannel whatsapp = new WhatsAppChannel();

        AluguelEvent evento = mock(AluguelEvent.class);
        when(evento.getMensagem()).thenReturn("Mensagem de teste");

        assertDoesNotThrow(() -> whatsapp.notificar(evento));
    }
}