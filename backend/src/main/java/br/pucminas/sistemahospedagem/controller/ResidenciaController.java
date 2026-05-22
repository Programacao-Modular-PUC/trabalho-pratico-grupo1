package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.dto.residencia.ResidenciaRequestDTO;
import br.pucminas.sistemahospedagem.dto.residencia.ResidenciaResponseDTO;
import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.service.ResidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/residencias")
@RequiredArgsConstructor
public class ResidenciaController {

    private final ResidenciaService service;

    // GET /residencias
    @GetMapping
    public List<ResidenciaResponseDTO> listar() {
        return service.listarTodas()
                .stream()
                .map(ResidenciaResponseDTO::fromEntity)
                .toList();
    }

    // GET /residencias/{id}
    @GetMapping("/{id}")
    public ResidenciaResponseDTO buscarPorId(@PathVariable Long id) {
        return ResidenciaResponseDTO.fromEntity(service.buscarPorId(id));
    }

    // POST /residencias
    @PostMapping
    public ResidenciaResponseDTO criar(@RequestBody ResidenciaRequestDTO dto) {
        return ResidenciaResponseDTO.fromEntity(service.criar(dto));
    }

    // PUT /residencias/{id}
    @PutMapping("/{id}")
    public ResidenciaResponseDTO atualizar(@PathVariable Long id,
                                           @RequestBody ResidenciaRequestDTO dto) {
        return ResidenciaResponseDTO.fromEntity(service.atualizar(id, dto));
    }

    // DELETE /residencias/{id}
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    // GET /residencias/{id}/relatorio?mes=5
    @GetMapping("/{id}/relatorio")
    public String relatorio(@PathVariable Long id,
                            @RequestParam int mes) {
        return service.gerarRelatorio(id, mes);
    }

    // GET /residencias/{id}/quartos-livres
    @GetMapping("/{id}/quartos-livres")
    public List<Quarto> quartosLivres(
            @PathVariable Long id,
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim
    ) {
        return service.buscarQuartosLivres(id, inicio, fim);
    }
}