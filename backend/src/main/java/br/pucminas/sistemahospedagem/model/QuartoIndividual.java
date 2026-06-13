package br.pucminas.sistemahospedagem.model;
import br.pucminas.sistemahospedagem.exception.RecursoNaoPermitidoException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("INDIVIDUAL")
public class QuartoIndividual extends br.pucminas.sistemahospedagem.model.Quarto {

    private int numeroCamasSolteiro;
    private double valorAdicionalPorCama;

    @Override
    public double calcularValorDiaria(int numHospedes) {
        double valor;
        if (numeroCamasSolteiro <= 1) {
            valor = getValorBase();
        } else {
            valor = getValorBase() + (numeroCamasSolteiro - 1) * valorAdicionalPorCama;
        }
        return valor + calcularTaxasAdicionais();
    }

    @Override
    public boolean validarCapacidade(int numHospedes) {
        return numHospedes <= numeroCamasSolteiro;
    }

    public void validarSolicitacaoBerco(boolean bercoSolicitado) {
        if (bercoSolicitado) {
            throw new RecursoNaoPermitidoException("berço", "Quarto Individual");
        }
    }
}