package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.dto.quarto.QuartoRequestDTO;
import br.pucminas.sistemahospedagem.dto.quarto.QuartoResponseDTO;
import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.service.QuartoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService service;

    @GetMapping
    public ResponseEntity<List<QuartoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos().stream().map(QuartoResponseDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            Quarto q = service.buscarPorId(id);
            return ResponseEntity.ok(QuartoResponseDTO.fromEntity(q));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<QuartoResponseDTO> criar(@Valid @RequestBody QuartoRequestDTO dto) {
        Quarto criado = service.criar(dto);
        return ResponseEntity.status(201).body(QuartoResponseDTO.fromEntity(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody QuartoRequestDTO dto) {
        try {
            Quarto atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(QuartoResponseDTO.fromEntity(atualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
