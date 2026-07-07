package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.config.ConfiguracaoGlobalSistema;
import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Quarto;
import org.springframework.stereotype.Component;

@Component
public class TarifaPromocional implements RegraTarifacao {

    private ConfiguracaoGlobalSistema config;

    @Override
    public double calcular(Quarto quarto, int numHospedes) { return 0.0; }

    @Override
    public double aplicarAoAluguel(Aluguel aluguel, double valorAtual) {
        if (aluguel.calcularDiarias(config.getCheckoutHora()) >= config.getDiariasPromocao()) {
            return valorAtual * config.getDiariasDescontoPromocao(); // 10% de desconto
        }
        return valorAtual;
    }
}