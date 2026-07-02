package br.pucminas.sistemahospedagem.notification.observer;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(WhatsAppChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {
        log.info("[WHATSAPP] {}", evento.getMensagem());
    }

    @Override
    public String getNomeCanal() {
        return "WHATSAPP";
    }
}