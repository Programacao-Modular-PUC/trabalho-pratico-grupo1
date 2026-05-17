package br.pucminas.sistemahospedagem.model;
import br.pucminas.sistemahospedagem.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @Entity @Table(name = "pagamentos")
public class Pagamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataPagamento;
    private double valor;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
}