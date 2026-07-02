package br.pucminas.sistemahospedagem.notification;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import br.pucminas.sistemahospedagem.notification.observer.EmailChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailChannelTest {
    
    @Test
    void deveRetornarNomeDoCanal() {
        EmailChannel email = new EmailChannel();

        assertEquals("EMAIL", email.getNomeCanal());
    }

    @Test
    void deveNotificarSemLancarExcecao() {
        EmailChannel email = new EmailChannel();

        AluguelEvent evento = mock(AluguelEvent.class);
        when(evento.getMensagem()).thenReturn("Mensagem de teste");

        assertDoesNotThrow(() -> email.notificar(evento));
    }
}