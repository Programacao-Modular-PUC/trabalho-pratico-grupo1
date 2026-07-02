package br.pucminas.sistemahospedagem.notification.observer;

@Component
public class WhatsAppChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(WhatsAppChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {

        log.info(
            "[WHATSAPP] {}",
            evento.getMensagem()
        );

    }

    @Override
    public String getNomeCanal() {
        return "WHATSAPP";
    }
}