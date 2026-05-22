package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.dto.AnfitriaoDTO;
import br.pucminas.sistemahospedagem.model.Anfitriao;
import br.pucminas.sistemahospedagem.service.AnfitriaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anfitrioes")
public class AnfitriaoController {

    @Autowired
    private AnfitriaoService anfitriaoService;

    @PostMapping
    public ResponseEntity<Anfitriao> criar(@RequestBody AnfitriaoDTO dto) {
        return ResponseEntity.ok(anfitriaoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Anfitriao>> listarTodos() {
        return ResponseEntity.ok(anfitriaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anfitriao> buscarPorId(@PathVariable Long id) {
        return anfitriaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anfitriao> atualizar(@PathVariable Long id,
                                               @RequestBody AnfitriaoDTO dto) {
        return ResponseEntity.ok(anfitriaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        anfitriaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}