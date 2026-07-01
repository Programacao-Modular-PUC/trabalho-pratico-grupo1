package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;

public class TarifaBaixaTemporada extends TarifaPadrao {

    private static final double MULTIPLICADOR = 0.90;

    @Override
    public double calcular(Quarto quarto, int numHospedes) {
        return super.calcular(quarto, numHospedes) * MULTIPLICADOR;
    }
}