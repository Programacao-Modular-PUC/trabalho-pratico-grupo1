package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Quarto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TarifacaoContext {

    private RegraTarifacao regraTarifacao;
    private Map<String, RegraTarifacao> todasAsRegras;

    // Novo construtor: O Spring injeta todas as regras que usarem @Component
    public TarifacaoContext(List<RegraTarifacao> regrasInjetadas) {
        this.regraTarifacao = new TarifaPadrao();
        this.todasAsRegras = regrasInjetadas.stream()
                .collect(Collectors.toMap(RegraTarifacao::getNome, Function.identity(), (r1, r2) -> r1));
    }

    // Mantém o construtor padrão antigo para evitar quebras em testes antigos da Pessoa 1
    public TarifacaoContext() {
        this.regraTarifacao = new TarifaPadrao();
    }

    // SEUS NOVOS MÉTODOS PARA A SPRINT ACTUAL
    public List<String> listarRegrasDisponiveis() {
        if (todasAsRegras == null) return List.of();
        return List.copyOf(todasAsRegras.keySet());
    }

    public double calcularValorComRegraEspecifica(String nomeRegra, Aluguel aluguel, double valorBase) {
        if (todasAsRegras == null || !todasAsRegras.containsKey(nomeRegra.toUpperCase())) {
            throw new IllegalArgumentException("Regra de tarifação não encontrada: " + nomeRegra);
        }
        return todasAsRegras.get(nomeRegra.toUpperCase()).aplicarAoAluguel(aluguel, valorBase);
    }

    // MÉTODOS ORIGINAIS DA PESSOA 1 (Mantidos 100% iguais)
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