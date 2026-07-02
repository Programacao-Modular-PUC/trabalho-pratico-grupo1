package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;

public interface RegraTarifacao {

    double calcular(Quarto quarto, int numHospedes);
}