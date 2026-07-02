package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.tarifacao.TarifacaoContext;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_quarto", discriminatorType = DiscriminatorType.STRING)
@Table(name = "quartos")
public abstract class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;
    private double valorBase;
    private boolean possuiArCondicionado;
    private boolean possuiHidromassagem;
    private int capacidadeMaxima;
    private double valorAdicionalAr;
    private double valorAdicionalHidro;

    @Transient
    private TarifacaoContext tarifacaoContext = new TarifacaoContext();

    @ManyToOne(optional = false)
    @JoinColumn(name = "residencia_id", nullable = false)
    private Residencia residencia;

    public abstract double calcularValorDiaria(int numHospedes);
    public abstract boolean validarCapacidade(int numHospedes);

    public boolean verificarDisponibilidade(java.time.LocalDateTime dataInicio,
                                            java.time.LocalDateTime dataFim) {
        return true;
    }

    public double calcularTaxasAdicionais() {
        double adicional = 0.0;
        if (isPossuiArCondicionado()) adicional += valorAdicionalAr;
        if (isPossuiHidromassagem()) adicional += valorAdicionalHidro;
        return adicional;
    }

    public TarifacaoContext getTarifacaoContext() {
        if (tarifacaoContext == null) {
            tarifacaoContext = new TarifacaoContext();
        }
        return tarifacaoContext;
    }

    public void setTarifacaoContext(TarifacaoContext tarifacaoContext) {
        this.tarifacaoContext = tarifacaoContext;
    }
}