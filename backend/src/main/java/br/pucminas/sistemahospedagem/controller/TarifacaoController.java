package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.dto.AplicarTarifaDTO;
import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.tarifacao.TarifacaoContext;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
public class TarifacaoController {

    private final TarifacaoContext tarifacaoContext;
    private final AluguelRepository aluguelRepository;

    @GetMapping("/disponiveis")
    public ResponseEntity<List<String>> listarRegras() {
        return ResponseEntity.ok(tarifacaoContext.listarRegrasDisponiveis());
    }

    @PostMapping("/alugueis/{aluguelId}/aplicar")
    public ResponseEntity<Aluguel> aplicarRegra(
            @PathVariable Long aluguelId,
            @Valid @RequestBody AplicarTarifaDTO dto) {
            
        Aluguel aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));

        // Calcula o valor base usando o método existente do Aluguel
        double valorBase = aluguel.calcularValorFinal();
        
        // Aplica a estratégia de negócio selecionada via DTO
        double valorCalculado = tarifacaoContext.calcularValorComRegraEspecifica(dto.nomeRegra(), aluguel, valorBase);
        
        aluguel.setValorFinal(valorCalculado);
        return ResponseEntity.ok(aluguelRepository.save(aluguel));
    }
}