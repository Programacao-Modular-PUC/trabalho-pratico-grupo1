package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.embedded.Endereco;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "residencias")
public class Residencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefone;
    private String email;

    @Embedded
    private Endereco endereco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "anfitriao_id", nullable = false)
    private Anfitriao anfitriao;

    @OneToMany(mappedBy = "residencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> listaQuartos = new ArrayList<>();

    @OneToMany(mappedBy = "residencia", cascade = CascadeType.ALL)
    private List<Aluguel> historicoAlugueis = new ArrayList<>();

    public void adicionarQuarto(Quarto quarto) {
        quarto.setResidencia(this);
        listaQuartos.add(quarto);
    }

    public void removerQuarto(Quarto quarto) {
        listaQuartos.remove(quarto);
        quarto.setResidencia(null);
    }

    public void adicionarAoHistorico(Aluguel aluguel) {
        historicoAlugueis.add(aluguel);
    }

    public List<Quarto> buscarQuartosLivres(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return listaQuartos.stream()
                .filter(q -> q.verificarDisponibilidade(dataInicio, dataFim))
                .toList();
    }

    public String gerarRelatorioOcupacao(int mes) {
        long ocupados = historicoAlugueis.stream()
                .filter(a -> a.getDataPrevistaEntrada().getMonthValue() == mes)
                .count();
        return "Mês " + mes + ": " + ocupados + " aluguéis registrados.";
    }
}