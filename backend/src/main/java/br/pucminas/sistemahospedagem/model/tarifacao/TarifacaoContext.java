package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Quarto;

public class TarifacaoContext {

    private RegraTarifacao regraTarifacao;

    public TarifacaoContext() {
        this.regraTarifacao = new TarifaPadrao();
    }

    public double calcular(Quarto quarto, int numHospedes) {
        return regraTarifacao.calcular(quarto, numHospedes);
    }

    public RegraTarifacao getRegraTarifacao() {
        return regraTarifacao;
    }

    public void setRegraTarifacao(RegraTarifacao regraTarifacao) {
        this.regraTarifacao = regraTarifacao != null ? regraTarifacao : new TarifaPadrao();
    }

    public void usarTarifaPadrao() {
        this.regraTarifacao = new TarifaPadrao();
    }

    public void usarTarifaAltaTemporada() {
        this.regraTarifacao = new TarifaAltaTemporada();
    }

    public void usarTarifaBaixaTemporada() {
        this.regraTarifacao = new TarifaBaixaTemporada();
    }
}