package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.exception.*;
import br.pucminas.sistemahospedagem.model.*;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AluguelServiceTest {

    private AluguelRepository repository;
    private AluguelService service;

    @BeforeEach
    void setup() {
        repository = mock(AluguelRepository.class);
        service = new AluguelService(repository);
    }

    @Test
    void deveLancarErroQuandoExcedeCapacidade() {
        Quarto quarto = mock(Quarto.class);
        when(quarto.getCapacidadeMaxima()).thenReturn(2);

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setNumeroHospedes(5);
        aluguel.setDataPrevistaEntrada(LocalDateTime.now().plusDays(1));
        aluguel.setDataPrevistaSaida(LocalDateTime.now().plusDays(2));

        when(repository.existeConflitoDeDatas(any(), any(), any())).thenReturn(false);

        assertThrows(CapacidadeExcedidaException.class, () -> service.criar(aluguel));
    }

    @Test
    void deveLancarErroQuandoQuartoIndisponivel() {
        Quarto quarto = mock(Quarto.class);
        when(quarto.getId()).thenReturn(1L);
        when(quarto.getCapacidadeMaxima()).thenReturn(2);

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setNumeroHospedes(2);
        aluguel.setDataPrevistaEntrada(LocalDateTime.now().plusDays(1));
        aluguel.setDataPrevistaSaida(LocalDateTime.now().plusDays(2));

        when(repository.existeConflitoDeDatas(any(), any(), any())).thenReturn(true);

        assertThrows(QuartoIndisponivelException.class, () -> service.criar(aluguel));
    }

    @Test
    void deveLancarErroQuandoDatasInvalidas() {
        Aluguel aluguel = new Aluguel();

        aluguel.setDataPrevistaEntrada(LocalDateTime.now().plusDays(2));
        aluguel.setDataPrevistaSaida(LocalDateTime.now().plusDays(1)); // saída antes

        assertThrows(DataInvalidaException.class, () -> service.criar(aluguel));
    }

    @Test
    void deveCriarAluguelComSucesso() {
        Quarto quarto = mock(Quarto.class);
        when(quarto.getCapacidadeMaxima()).thenReturn(2);
        when(quarto.calcularValorDiaria(anyInt())).thenReturn(100.0);
        when(quarto.getId()).thenReturn(1L);

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setNumeroHospedes(2);
        aluguel.setDataPrevistaEntrada(LocalDateTime.of(2030, 6, 10, 10, 0));
        aluguel.setDataPrevistaSaida(LocalDateTime.of(2030, 6, 12, 10, 0));

        when(repository.existeConflitoDeDatas(any(), any(), any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Aluguel resultado = service.criar(aluguel);

        assertNotNull(resultado.getPagamento());
        assertEquals(200.0, resultado.getValorFinal());
    }
}
