package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.model.QuartoCasal;
import br.pucminas.sistemahospedagem.model.QuartoIndividual;
import br.pucminas.sistemahospedagem.model.enums.TipoCamaCasal;

public class TarifaPadrao implements RegraTarifacao {

    @Override
    public double calcular(Quarto quarto, int numHospedes) {
        if (quarto instanceof QuartoCasal quartoCasal) {
            return calcularQuartoCasal(quartoCasal) + quartoCasal.calcularTaxasAdicionais();
        }

        if (quarto instanceof QuartoIndividual quartoIndividual) {
            return calcularQuartoIndividual(quartoIndividual) + quartoIndividual.calcularTaxasAdicionais();
        }

        return quarto.getValorBase() + quarto.calcularTaxasAdicionais();
    }

    protected double calcularQuartoCasal(QuartoCasal quartoCasal) {
        double valor = quartoCasal.getValorBase();

        valor += switch (quartoCasal.getTipoCamaCasal()) {
            case QUEEN -> quartoCasal.getValorAdicionalConforto() * 0.5;
            case KING -> quartoCasal.getValorAdicionalConforto();
            default -> 0.0;
        };

        if (quartoCasal.isBercoInstalado()) {
            valor += quartoCasal.getValorAdicionalBerco();
        }

        return valor;
    }

    protected double calcularQuartoIndividual(QuartoIndividual quartoIndividual) {
        if (quartoIndividual.getNumeroCamasSolteiro() <= 1) {
            return quartoIndividual.getValorBase();
        }

        return quartoIndividual.getValorBase()
                + (quartoIndividual.getNumeroCamasSolteiro() - 1) * quartoIndividual.getValorAdicionalPorCama();
    }
}