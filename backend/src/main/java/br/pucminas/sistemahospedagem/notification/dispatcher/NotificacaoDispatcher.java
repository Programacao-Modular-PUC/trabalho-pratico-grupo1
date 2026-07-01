package br.pucminas.sistemahospedagem.notification.dispatcher;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;
import br.pucminas.sistemahospedagem.notification.observer.NotificacaoObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Central de notificações — implementa o papel de Subject no padrão Observer.
 *
 * O Spring injeta automaticamente todos os beans que implementam
 * NotificacaoObserver na lista de observers. Isso significa que a
 * Pessoa 4 só precisa criar canais anotados com @Component; eles
 * entram aqui sem nenhuma alteração nesta classe.
 */
@Component
public class NotificacaoDispatcher {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoDispatcher.class);

    private final List<NotificacaoObserver> observers;

    public NotificacaoDispatcher(List<NotificacaoObserver> observers) {
        this.observers = observers;
    }

    /**
     * Despacha o evento para todos os canais registrados.
     * Erros em um canal não interrompem os demais.
     */
    public void despachar(AluguelEvent evento) {
        log.info("[Dispatcher] Evento: {} | Aluguel ID: {} | Canais ativos: {}",
                evento.getTipoEvento(),
                evento.getAluguel().getId(),
                observers.size());

        for (NotificacaoObserver observer : observers) {
            try {
                observer.notificar(evento);
            } catch (Exception e) {
                log.error("[Dispatcher] Falha no canal '{}' para evento '{}': {}",
                        observer.getNomeCanal(), evento.getTipoEvento(), e.getMessage());
            }
        }
    }
}