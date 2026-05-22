package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Pagamento;
import br.pucminas.sistemahospedagem.model.enums.StatusPagamento;
import br.pucminas.sistemahospedagem.model.enums.StatusAluguel;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AluguelService {
    private final AluguelRepository repository;

    public AluguelService(AluguelRepository repository) {
        this.repository = repository;
    }

    public Aluguel criar(Aluguel aluguel) {
        // 1. Calcula os valores e diárias usando a regra da classe Aluguel
        aluguel.calcularValorFinal(); 
        aluguel.setStatus(StatusAluguel.RESERVADO);
        
        // 2. Cria o pagamento pendente atrelado
        Pagamento pag = new Pagamento();
        pag.setValor(aluguel.getValorFinal());
        pag.setStatus(StatusPagamento.PENDENTE);
        aluguel.setPagamento(pag);
        
        // O CascadeType.ALL na entidade Aluguel vai salvar o pagamento automaticamente
        return repository.save(aluguel);
    }

    public List<Aluguel> listar() { 
        return repository.findAll(); 
    }

    public Aluguel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado com o ID: " + id));
    }

    public Aluguel confirmarPagamento(Long id) {
        Aluguel a = buscarPorId(id);
        if (a.getPagamento() != null) {
            a.getPagamento().setStatus(StatusPagamento.PAGO);
            a.getPagamento().setDataPagamento(java.time.LocalDate.now());
        }
        return repository.save(a);
    }

    public Aluguel checkIn(Long id) {
        Aluguel a = buscarPorId(id);
        a.realizarCheckIn(); // Usa o método que você me mostrou
        return repository.save(a);
    }

    public Aluguel checkOut(Long id) {
        Aluguel a = buscarPorId(id);
        a.realizarCheckOut(); // Usa o método que você me mostrou
        return repository.save(a);
    }

    public Aluguel cancelar(Long id) {
        Aluguel a = buscarPorId(id);
        a.cancelarReserva(); // Usa o método que você me mostrou
        return repository.save(a);
    }

    public String emitirRecibo(Long id) {
        Aluguel a = buscarPorId(id);
        return a.gerarRecibo(); // Usa o método que você me mostrou
    }
}