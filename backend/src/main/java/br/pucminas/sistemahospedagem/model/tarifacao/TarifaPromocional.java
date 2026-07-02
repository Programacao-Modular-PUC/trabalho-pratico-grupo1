package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Quarto;
import org.springframework.stereotype.Component;

@Component
public class TarifaPromocional implements RegraTarifacao {

    @Override
    public double calcular(Quarto quarto, int numHospedes) { return 0.0; }

    @Override
    public double aplicarAoAluguel(Aluguel aluguel, double valorAtual) {
        if (aluguel.calcularDiarias() >= 5) {
            return valorAtual * 0.90; // 10% de desconto
        }
        return valorAtual;
    }
}