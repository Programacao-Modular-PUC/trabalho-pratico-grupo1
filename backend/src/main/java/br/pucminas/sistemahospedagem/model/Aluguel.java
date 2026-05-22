package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.enums.StatusAluguel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
@Table(name = "alugueis")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "residencia_id", nullable = false)
    private Residencia residencia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
    private Pagamento pagamento;

    private LocalDateTime dataPrevistaEntrada;
    private LocalDateTime dataPrevistaSaida;
    private LocalDateTime dataRealEntrada;
    private LocalDateTime dataRealSaida;

    private int quantidadeDiarias;
    private double valorFinal;
    private int numeroHospedes;
    private boolean bercoSolicitado;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    public double calcularValorFinal() {
        if (quarto == null) return 0.0;

        if (quarto instanceof QuartoCasal quartoCasal) {
            quartoCasal.validarSolicitacaoBerco(bercoSolicitado);
        }

        double valorDiaria = quarto.calcularValorDiaria(numeroHospedes);
        this.quantidadeDiarias = calcularDiarias();
        this.valorFinal = valorDiaria * quantidadeDiarias;
        return this.valorFinal;
    }

    public int calcularDiarias() {
        if (dataPrevistaEntrada == null || dataPrevistaSaida == null) return 0;

        int diarias = (int) ChronoUnit.DAYS.between(
                dataPrevistaEntrada.toLocalDate(),
                dataPrevistaSaida.toLocalDate()
        );

        if (dataPrevistaSaida.getHour() >= 12) {
            diarias++;
        }

        return diarias;
    }

    public void cancelarReserva() {
        this.status = StatusAluguel.CANCELADO;
    }

    public void realizarCheckIn() {
        this.dataRealEntrada = LocalDateTime.now();
        this.status = StatusAluguel.EM_ANDAMENTO;
    }

    public void realizarCheckOut() {
        this.dataRealSaida = LocalDateTime.now();
        this.status = StatusAluguel.CONCLUIDO;
    }

    public String gerarRecibo() {
        return "Recibo - Cliente: " + cliente.getNome() +
                " | Quarto: " + quarto.getNumero() +
                " | Diárias: " + quantidadeDiarias +
                " | Valor Total: R$ " + String.format("%.2f", valorFinal) +
                " | Status: " + status;
    }
}