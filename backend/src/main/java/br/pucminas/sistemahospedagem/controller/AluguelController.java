package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.service.AluguelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
public class AluguelController {

    private final AluguelService service;

    public AluguelController(AluguelService service) { 
        this.service = service; 
    }

    @PostMapping
    public ResponseEntity<Aluguel> criar(@RequestBody Aluguel aluguel) { 
        Aluguel novoAluguel = service.criar(aluguel); 
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluguel);
    }

    @GetMapping
    public ResponseEntity<List<Aluguel>> listar() { 
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Aluguel>> historicoPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.buscarHistoricoCliente(clienteId));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Aluguel> pagar(@PathVariable Long id) { 
        return ResponseEntity.ok(service.confirmarPagamento(id));
    }

    @PutMapping("/{id}/checkin")
    public ResponseEntity<Aluguel> realizarCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkIn(id));
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<Aluguel> realizarCheckOut(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkOut(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Aluguel> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

    @GetMapping("/{id}/recibo")
    public ResponseEntity<String> obterRecibo(@PathVariable Long id) {
        return ResponseEntity.ok(service.emitirRecibo(id));
    }
}