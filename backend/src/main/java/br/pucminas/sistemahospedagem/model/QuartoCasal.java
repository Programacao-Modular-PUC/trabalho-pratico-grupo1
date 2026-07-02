package br.pucminas.sistemahospedagem.model;
import br.pucminas.sistemahospedagem.exception.RecursoNaoPermitidoException;
import br.pucminas.sistemahospedagem.model.enums.TipoCamaCasal;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CASAL")
public class QuartoCasal extends br.pucminas.sistemahospedagem.model.Quarto {

    @Enumerated(EnumType.STRING)
    private TipoCamaCasal tipoCamaCasal;

    private boolean bercoInstalado;
    private double valorAdicionalBerco;
    private double valorAdicionalConforto;

    @Override
    public double calcularValorDiaria(int numHospedes) {
        return getTarifacaoContext().calcular(this, numHospedes);
    }

    @Override
    public boolean validarCapacidade(int numHospedes) {
        return numHospedes <= 2;
    }

    public void solicitarBerco(boolean b) {
        this.bercoInstalado = b;
    }

    public boolean validarSolicitacaoBerco(boolean bercoSolicitado) {
        if (bercoSolicitado && !bercoInstalado) {
            throw new RecursoNaoPermitidoException("berço", "Quarto Casal sem berço instalado");
        }
        return true;
    }
}