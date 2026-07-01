package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.exception.*;
import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Pagamento;
import br.pucminas.sistemahospedagem.model.enums.StatusAluguel;
import br.pucminas.sistemahospedagem.model.enums.StatusPagamento;
import br.pucminas.sistemahospedagem.notification.dispatcher.NotificacaoDispatcher;
import br.pucminas.sistemahospedagem.notification.event.*;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AluguelService {

    private final AluguelRepository repository;
    private final NotificacaoDispatcher dispatcher;

    public AluguelService(AluguelRepository repository, NotificacaoDispatcher dispatcher) {
        this.repository = repository;
        this.dispatcher = dispatcher;
    }

    public Aluguel criar(Aluguel aluguel) {
        validarDatas(aluguel.getDataPrevistaEntrada(), aluguel.getDataPrevistaSaida());
        validarDisponibilidade(aluguel);
        validarCapacidade(aluguel);

        aluguel.calcularValorFinal();
        aluguel.setStatus(StatusAluguel.RESERVADO);

        Pagamento pag = new Pagamento();
        pag.setValor(aluguel.getValorFinal());
        pag.setStatus(StatusPagamento.PENDENTE);
        aluguel.setPagamento(pag);

        Aluguel salvo = repository.save(aluguel);
        dispatcher.despachar(new ReservaCriadaEvent(salvo));
        return salvo;
    }

    public List<Aluguel> listar() {
        return repository.findAll();
    }

    public Aluguel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluguel", id));
    }

    public Aluguel confirmarPagamento(Long id) {
        Aluguel a = buscarPorId(id);
        if (a.getPagamento() != null) {
            a.getPagamento().setStatus(StatusPagamento.PAGO);
            a.getPagamento().setDataPagamento(java.time.LocalDate.now());
        }
        Aluguel salvo = repository.save(a);
        dispatcher.despachar(new PagamentoConfirmadoEvent(salvo));
        return salvo;
    }

    public Aluguel checkIn(Long id) {
        Aluguel a = buscarPorId(id);
        if (a.getStatus() != StatusAluguel.RESERVADO) {
            throw new DataInvalidaException(
                    "Check-in não permitido: status atual é " + a.getStatus() + ". Esperado: RESERVADO."
            );
        }
        a.realizarCheckIn();
        Aluguel salvo = repository.save(a);
        dispatcher.despachar(new CheckInEvent(salvo));
        return salvo;
    }

    public Aluguel checkOut(Long id) {
        Aluguel a = buscarPorId(id);
        if (a.getStatus() != StatusAluguel.EM_ANDAMENTO) {
            throw new DataInvalidaException(
                    "Check-out não permitido: status atual é " + a.getStatus() + ". Esperado: EM_ANDAMENTO."
            );
        }
        a.realizarCheckOut();
        Aluguel salvo = repository.save(a);
        dispatcher.despachar(new CheckOutEvent(salvo));
        return salvo;
    }

    public Aluguel cancelar(Long id) {
        Aluguel a = buscarPorId(id);
        if (a.getStatus() == StatusAluguel.CONCLUIDO || a.getStatus() == StatusAluguel.CANCELADO) {
            throw new DataInvalidaException(
                    "Não é possível cancelar um aluguel com status: " + a.getStatus() + "."
            );
        }
        a.cancelarReserva();
        Aluguel salvo = repository.save(a);
        dispatcher.despachar(new ReservaCanceladaEvent(salvo));
        return salvo;
    }

    public List<Aluguel> buscarHistoricoCliente(Long clienteId) {
        List<Aluguel> historico = repository.findByClienteId(clienteId);
        if (historico.isEmpty()) {
            throw new RecursoNaoEncontradoException("Aluguel do cliente", clienteId);
        }
        return historico;
    }

    public String emitirRecibo(Long id) {
        return buscarPorId(id).gerarRecibo();
    }

    // --- Validações privadas ---

    private void validarDatas(LocalDateTime entrada, LocalDateTime saida) {
        if (entrada == null || saida == null) {
            throw new DataInvalidaException("As datas de entrada e saída são obrigatórias.");
        }
        if (!saida.isAfter(entrada)) {
            throw new DataInvalidaException("A data de saída deve ser posterior à data de entrada.");
        }
        if (entrada.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("A data de entrada não pode ser no passado.");
        }
    }

    private void validarDisponibilidade(Aluguel aluguel) {
        boolean conflito = repository.existeConflitoDeDatas(
                aluguel.getQuarto().getId(),
                aluguel.getDataPrevistaEntrada(),
                aluguel.getDataPrevistaSaida()
        );
        if (conflito) {
            throw new QuartoIndisponivelException(aluguel.getQuarto().getId());
        }
    }

    private void validarCapacidade(Aluguel aluguel) {
        int capacidade = aluguel.getQuarto().getCapacidadeMaxima();
        if (aluguel.getNumeroHospedes() > capacidade) {
            throw new CapacidadeExcedidaException(aluguel.getNumeroHospedes(), capacidade);
        }
    }
}