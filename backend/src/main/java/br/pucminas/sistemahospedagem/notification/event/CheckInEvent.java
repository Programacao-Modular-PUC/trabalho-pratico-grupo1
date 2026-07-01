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
        return String.format(
                "Check-in realizado! Bem-vindo(a), %s. " +
                        "Quarto %d | Saída prevista: %s",
                a.getCliente().getNome(),
                a.getQuarto().getNumero(),
                a.getDataPrevistaSaida()
        );
    }
}