package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;

public class TarifaAltaTemporada extends TarifaPadrao {

    private static final double MULTIPLICADOR = 1.20;

    @Override
    public double calcular(Quarto quarto, int numHospedes) {
        return super.calcular(quarto, numHospedes) * MULTIPLICADOR;
    }
}