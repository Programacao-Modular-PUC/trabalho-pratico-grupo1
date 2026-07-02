package br.pucminas.sistemahospedagem.notification.observer;

import br.pucminas.sistemahospedagem.notification.event.AluguelEvent;

/**
 * Interface do padrão Observer.
 *
 * Cada canal de notificação (Email, SMS, WhatsApp, etc.)
 * implementa esta interface e é registrado automaticamente
 * no NotificacaoDispatcher via injeção de dependência do Spring.
 *
 * Para adicionar um novo canal, basta criar uma classe anotada
 * com @Component que implemente esta interface, sem modificar
 * nenhum código existente (princípio Open/Closed).
 */
public interface NotificacaoObserver {

    /**
     * Recebe e processa o evento de aluguel.
     *
     * @param evento o evento ocorrido no ciclo de vida da reserva
     */
    void notificar(AluguelEvent evento);

    /**
     * Nome do canal para fins de log e rastreabilidade.
     * Exemplo: "EMAIL", "SMS", "WHATSAPP", "INTERNA"
     */
    String getNomeCanal();
}