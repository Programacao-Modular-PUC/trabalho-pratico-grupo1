package br.pucminas.sistemahospedagem.notification.event;

import br.pucminas.sistemahospedagem.model.Aluguel;

public class PagamentoConfirmadoEvent extends AluguelEvent {

    public PagamentoConfirmadoEvent(Aluguel aluguel) {
        super(aluguel);
    }

    @Override
    public String getTipoEvento() {
        return "PAGAMENTO_CONFIRMADO";
    }

    @Override
    public String getMensagem() {
        Aluguel a = getAluguel();
        return String.format(
                "Pagamento confirmado! %s, recebemos seu pagamento de R$ %.2f " +
                        "referente à reserva do quarto %d.",
                a.getCliente().getNome(),
                a.getValorFinal(),
                a.getQuarto().getNumero()
        );
    }
}