package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

public class ReservaCanceladaEvent extends AluguelEvent {

    public ReservaCanceladaEvent(Aluguel aluguel) {
        super(aluguel);
    }

    @Override
    public String getTipoEvento() {
        return "RESERVA_CANCELADA";
    }

    @Override
    public String getMensagem() {
        Aluguel a = getAluguel();
        return String.format(
                "Olá, %s. Sua reserva do quarto %d foi cancelada. " +
                        "Se tiver dúvidas, entre em contato conosco.",
                a.getCliente().getNome(),
                a.getQuarto().getNumero()
        );
    }
}