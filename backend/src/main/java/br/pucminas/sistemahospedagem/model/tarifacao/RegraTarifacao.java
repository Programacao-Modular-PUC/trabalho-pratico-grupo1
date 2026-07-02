package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.model.Aluguel;

public interface RegraTarifacao {
    double calcular(Quarto quarto, int numHospedes);

    default double aplicarAoAluguel(Aluguel aluguel, double valorAtual) {
        return valorAtual; // Por padrão, não altera o valor
    }

    default String getNome() {
        return this.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }
}