package br.pucminas.sistemahospedagem.notification.observer;

@Component
public class NotificacaoInternaChannel implements NotificacaoObserver {

    private static final Logger log =
            LoggerFactory.getLogger(NotificacaoInternaChannel.class);

    @Override
    public void notificar(AluguelEvent evento) {

        log.info(
            "[INTERNA] {}",
            evento.getMensagem()
        );

    }

    @Override
    public String getNomeCanal() {
        return "INTERNA";
    }
}