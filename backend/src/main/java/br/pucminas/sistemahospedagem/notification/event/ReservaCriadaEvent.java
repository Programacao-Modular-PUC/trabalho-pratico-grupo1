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
        String nomeCliente = a.getCliente() != null ? a.getCliente().getNome() : "Cliente";
        int numQuarto = a.getQuarto() != null ? a.getQuarto().getNumero() : 0;
        return String.format(
                "Olá, %s! Sua reserva foi criada com sucesso. " +
                        "Quarto %d | Entrada: %s | Saída: %s | Valor: R$ %.2f",
                nomeCliente,
                numQuarto,
                a.getDataPrevistaEntrada(),
                a.getDataPrevistaSaida(),
                a.getValorFinal()
        );
    }
}