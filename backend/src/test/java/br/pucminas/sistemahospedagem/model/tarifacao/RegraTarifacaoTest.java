package br.pucminas.sistemahospedagem.model.tarifacao;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.Cliente;
import br.pucminas.sistemahospedagem.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RegraTarifacaoTest {

    private Aluguel aluguel;
    private AluguelRepository aluguelRepository;

    @BeforeEach
    void setUp() {
        aluguelRepository = Mockito.mock(AluguelRepository.class);
        aluguel = new Aluguel();
        aluguel.setDataPrevistaEntrada(LocalDateTime.of(2026, 6, 1, 14, 0));
        aluguel.setDataPrevistaSaida(LocalDateTime.of(2026, 6, 4, 12, 0)); // 3 diárias
        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        aluguel.setCliente(cliente);
    }

    @Test
    void deveAplicarAcrescimoDeFeriadoEmDezembro() {
        aluguel.setDataPrevistaEntrada(LocalDateTime.of(2026, 12, 25, 14, 0));
        TarifaFeriado tarifa = new TarifaFeriado();
        
        double resultado = tarifa.aplicarAoAluguel(aluguel, 100.0);
        assertEquals(125.0, resultado, "Deve acrescentar 25% em Dezembro");
    }

    @Test
    void deveAplicarDescontoPromocionalParaEstadiasLongas() {
        aluguel.setDataPrevistaSaida(LocalDateTime.of(2026, 6, 6, 12, 0)); // 5 diárias
        TarifaPromocional tarifa = new TarifaPromocional();
        
        double resultado = tarifa.aplicarAoAluguel(aluguel, 100.0);
        assertEquals(90.0, resultado, "Deve conceder 10% de desconto para 5 ou mais diárias");
    }

    @Test
    void deveAplicarDescontoDeFidelidadeParaClienteFrequente() {
        when(aluguelRepository.findByClienteId(1L))
                .thenReturn(List.of(new Aluguel(), new Aluguel(), new Aluguel())); // 3 aluguéis anteriores
                
        TarifaClienteFrequente tarifa = new TarifaClienteFrequente(aluguelRepository);
        
        double resultado = tarifa.aplicarAoAluguel(aluguel, 100.0);
        assertEquals(85.0, resultado, "Deve conceder 15% de desconto para clientes assíduos");
    }
}