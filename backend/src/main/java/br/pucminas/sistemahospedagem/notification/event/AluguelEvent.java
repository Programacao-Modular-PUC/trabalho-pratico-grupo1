package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

import java.time.LocalDateTime;

public abstract class AluguelEvent {

    private final Aluguel aluguel;
    private final LocalDateTime ocorridoEm;

    protected AluguelEvent(Aluguel aluguel) {
        this.aluguel = aluguel;
        this.ocorridoEm = LocalDateTime.now();
    }

    public Aluguel getAluguel() {
        return aluguel;
    }

    public LocalDateTime getOcorridoEm() {
        return ocorridoEm;
    }

    /**
     * Retorna o nome do evento para uso nos canais de notificação.
     * Exemplos: "RESERVA_CRIADA", "CHECK_IN", etc.
     */
    public abstract String getTipoEvento();

    /**
     * Mensagem padrão do evento, usada pelos canais como fallback.
     */
    public abstract String getMensagem();
}