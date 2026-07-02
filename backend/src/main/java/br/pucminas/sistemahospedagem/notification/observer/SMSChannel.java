package br.pucminas.sistemahospedagem.notification.observer;

@Component
public class SMSChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(SMSChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {

        log.info(
            "[SMS] {}",
            evento.getMensagem()
        );

    }

    @Override
    public String getNomeCanal() {
        return "SMS";
    }
}