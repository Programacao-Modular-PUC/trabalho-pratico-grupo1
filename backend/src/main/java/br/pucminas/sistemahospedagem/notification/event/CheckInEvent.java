package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

public class CheckInEvent extends AluguelEvent {

    public CheckInEvent(Aluguel aluguel) {
        super(aluguel);
    }

    @Override
    public String getTipoEvento() {
        return "CHECK_IN";
    }

    @Override
    public String getMensagem() {
        Aluguel a = getAluguel();
        String nomeCliente = a.getCliente() != null ? a.getCliente().getNome() : "Cliente";
        int numQuarto = a.getQuarto() != null ? a.getQuarto().getNumero() : 0;
        return String.format(
                "Check-in realizado! Bem-vindo(a), %s. " +
                        "Quarto %d | Saída prevista: %s",
                nomeCliente,
                numQuarto,
                a.getDataPrevistaSaida()
        );
    }
}