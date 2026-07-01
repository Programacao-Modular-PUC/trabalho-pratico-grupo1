package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.exception.RecursoNaoPermitidoException;
import br.pucminas.sistemahospedagem.model.enums.StatusAluguel;
import br.pucminas.sistemahospedagem.model.enums.TipoCamaCasal;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AluguelTest {

    @Test
    void deveCalcularDiariasCorretamente() {
        Aluguel aluguel = new Aluguel();

        aluguel.setDataPrevistaEntrada(LocalDateTime.of(2026, 6, 10, 10, 0));
        aluguel.setDataPrevistaSaida(LocalDateTime.of(2026, 6, 12, 14, 0));

        int diarias = aluguel.calcularDiarias();

        assertEquals(3, diarias); // 2 dias + 1 por saída 12h
    }

    @Test
    void deveRetornarZeroSeDatasInvalidas() {
        Aluguel aluguel = new Aluguel();
        assertEquals(0, aluguel.calcularDiarias());
    }

    @Test
    void deveCancelarReserva() {
        Aluguel aluguel = new Aluguel();

        aluguel.cancelarReserva();

        assertEquals(StatusAluguel.CANCELADO, aluguel.getStatus());
    }

    @Test
    void deveRealizarCheckIn() {
        Aluguel aluguel = new Aluguel();

        aluguel.realizarCheckIn();

        assertEquals(StatusAluguel.EM_ANDAMENTO, aluguel.getStatus());
        assertNotNull(aluguel.getDataRealEntrada());
    }

    @Test
    void deveRealizarCheckOut() {
        Aluguel aluguel = new Aluguel();

        aluguel.realizarCheckOut();

        assertEquals(StatusAluguel.CONCLUIDO, aluguel.getStatus());
        assertNotNull(aluguel.getDataRealSaida());
    }

    @Test
    void deveCalcularValorComBercoEmQuartoCasal() {
        QuartoCasal quarto = new QuartoCasal();
        quarto.setValorBase(100.0);
        quarto.setTipoCamaCasal(TipoCamaCasal.QUEEN);
        quarto.setBercoInstalado(true);

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setNumeroHospedes(2);
        aluguel.setBercoSolicitado(true);
        aluguel.setDataPrevistaEntrada(LocalDateTime.now().plusDays(1));
        aluguel.setDataPrevistaSaida(LocalDateTime.now().plusDays(2));

        assertDoesNotThrow(aluguel::calcularValorFinal);
    }

    @Test
    void deveFalharAoSolicitarBercoEmQuartoIndividual() {
        QuartoIndividual quarto = new QuartoIndividual();
        quarto.setValorBase(100.0);

        Aluguel aluguel = new Aluguel();
        aluguel.setQuarto(quarto);
        aluguel.setNumeroHospedes(1);
        aluguel.setBercoSolicitado(true);
        aluguel.setDataPrevistaEntrada(LocalDateTime.now().plusDays(1));
        aluguel.setDataPrevistaSaida(LocalDateTime.now().plusDays(2));

        assertThrows(RecursoNaoPermitidoException.class,
                aluguel::calcularValorFinal);
    }
}
