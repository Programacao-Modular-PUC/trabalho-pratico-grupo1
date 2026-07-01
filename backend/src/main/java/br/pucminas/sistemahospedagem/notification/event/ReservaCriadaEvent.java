package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

public class ReservaCriadaEvent extends AluguelEvent {

    public ReservaCriadaEvent(Aluguel aluguel) {
        super(aluguel);
    }

    @Override
    public String getTipoEvento() {
        return "RESERVA_CRIADA";
    }

    @Override
    public String getMensagem() {
        Aluguel a = getAluguel();
        return String.format(
                "Olá, %s! Sua reserva foi criada com sucesso. " +
                        "Quarto %d | Entrada: %s | Saída: %s | Valor: R$ %.2f",
                a.getCliente().getNome(),
                a.getQuarto().getNumero(),
                a.getDataPrevistaEntrada(),
                a.getDataPrevistaSaida(),
                a.getValorFinal()
        );
    }
}