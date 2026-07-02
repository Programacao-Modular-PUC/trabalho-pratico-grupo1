package br.pucminas.sistemahospedagem.notification;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import br.pucminas.sistemahospedagem.notification.observer.SMSChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SMSChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        SMSChannel sms = new SMSChannel();

        assertEquals("SMS", sms.getNomeCanal());
    }

    @Test
    void deveNotificarSemLancarExcecao() {
        SMSChannel sms = new SMSChannel();

        AluguelEvent evento = mock(AluguelEvent.class);
        when(evento.getMensagem()).thenReturn("Mensagem de teste");

        assertDoesNotThrow(() -> sms.notificar(evento));
    }
}