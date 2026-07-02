package br.pucminas.sistemahospedagem.notification.observer;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoInternaChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(NotificacaoInternaChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {
        log.info("[INTERNA] {}", evento.getMensagem());
    }

    @Override
    public String getNomeCanal() {
        return "INTERNA";
    }
}