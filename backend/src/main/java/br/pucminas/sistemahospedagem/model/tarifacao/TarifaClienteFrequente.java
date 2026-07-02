package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TarifaClienteFrequente implements RegraTarifacao {

    private final AluguelRepository aluguelRepository;

    @Override
    public double calcular(Quarto quarto, int numHospedes) { return 0.0; }

    @Override
    public double aplicarAoAluguel(Aluguel aluguel, double valorAtual) {
        if (aluguel.getCliente() == null || aluguel.getCliente().getId() == null) {
            return valorAtual;
        }
        int historico = aluguelRepository.findByClienteId(aluguel.getCliente().getId()).size();
        if (historico >= 3) {
            return valorAtual * 0.85; // 15% de desconto de fidelidade
        }
        return valorAtual;
    }
}