package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Quarto;
import org.springframework.stereotype.Component;

@Component
public class TarifaFeriado implements RegraTarifacao {
    
    @Override
    public double calcular(Quarto quarto, int numHospedes) { return 0.0; }

    @Override
    public double aplicarAoAluguel(Aluguel aluguel, double valorAtual) {
        if (aluguel.getDataPrevistaEntrada() == null) return valorAtual;
        int mes = aluguel.getDataPrevistaEntrada().getMonthValue();
        if (mes == 1 || mes == 7 || mes == 12) {
            return valorAtual * 1.25; // +25% de acréscimo
        }
        return valorAtual;
    }
}