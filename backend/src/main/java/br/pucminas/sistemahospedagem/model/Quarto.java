package br.pucminas.sistemahospedagem.model;

import jakarta.persistence.*;
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
}