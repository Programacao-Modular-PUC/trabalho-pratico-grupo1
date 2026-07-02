package br.pucminas.sistemahospedagem.notification.observer;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(EmailChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {
        log.info("[EMAIL] {}", evento.getMensagem());
    }

    @Override
    public String getNomeCanal() {
        return "EMAIL";
    }
}