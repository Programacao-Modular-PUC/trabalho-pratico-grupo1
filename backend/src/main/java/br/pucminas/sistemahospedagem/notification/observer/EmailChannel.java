package br.pucminas.sistemahospedagem.notification.observer;

@Component
public class EmailChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(EmailChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {

        log.info(
            "[EMAIL] {}",
            evento.getMensagem()
        );

    }

    @Override
    public String getNomeCanal() {
        return "EMAIL";
    }
}