package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.embedded.ConfiguracaoCamas;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FAMILIA")
public class QuartoFamilia extends br.pucminas.sistemahospedagem.model.Quarto {

    @Embedded
    private ConfiguracaoCamas configuracaoCamas;

    private int quantidadeAmbientes;
    private double percentualAdicionalPorHospede;
    private int limiteDescontoGrupo;
    private double percentualDescontoGrupo;

    @Override
    public double calcularValorDiaria(int numHospedes) {
        double valor = getValorBase();

        valor += valor * (percentualAdicionalPorHospede / 100) * numHospedes;
        valor = aplicarDescontoGrupo(valor, numHospedes);

        return valor + calcularTaxasAdicionais();
    }

    @Override
    public boolean validarCapacidade(int numHospedes) {
        return numHospedes <= getCapacidadeMaxima();
    }

    public int getCapacidadeMaxima() {
        if (configuracaoCamas == null) return 0;
        return configuracaoCamas.calculaCapacidadeTotal();
    }

    public double aplicarDescontoGrupo(double valor, int numHospedes) {
        if (numHospedes >= limiteDescontoGrupo) {
            valor -= valor * (percentualDescontoGrupo / 100);
        }
        return valor;
    }
}