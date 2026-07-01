package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

public class CheckOutEvent extends AluguelEvent {

    public CheckOutEvent(Aluguel aluguel) {
        super(aluguel);
    }

    @Override
    public String getTipoEvento() {
        return "CHECK_OUT";
    }

    @Override
    public String getMensagem() {
        Aluguel a = getAluguel();
        String nomeCliente = a.getCliente() != null ? a.getCliente().getNome() : "Cliente";
        return String.format(
                "Check-out realizado. Obrigado pela sua estadia, %s! " +
                        "Valor cobrado: R$ %.2f. Esperamos vê-lo(a) novamente.",
                nomeCliente,
                a.getValorFinal()
        );
    }
}