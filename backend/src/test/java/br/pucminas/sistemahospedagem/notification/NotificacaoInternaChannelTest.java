package br.pucminas.sistemahospedagem.notification;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import br.pucminas.sistemahospedagem.notification.observer.NotificacaoInternaChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificacaoInternaChannelTest {

    @Test
    void deveRetornarNomeDoCanal() {
        NotificacaoInternaChannel interna = new NotificacaoInternaChannel();

        assertEquals("INTERNA", interna.getNomeCanal());
    }

    @Test
    void deveNotificarSemLancarExcecao() {
        NotificacaoInternaChannel interna = new NotificacaoInternaChannel();

        AluguelEvent evento = mock(AluguelEvent.class);
        when(evento.getMensagem()).thenReturn("Mensagem de teste");

        assertDoesNotThrow(() -> interna.notificar(evento));
    }
}