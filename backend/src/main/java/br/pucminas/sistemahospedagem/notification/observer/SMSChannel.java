package br.pucminas.sistemahospedagem.notification.observer;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SMSChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(SMSChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {
        log.info("[SMS] {}", evento.getMensagem());
    }

    @Override
    public String getNomeCanal() {
        return "SMS";
    }
}