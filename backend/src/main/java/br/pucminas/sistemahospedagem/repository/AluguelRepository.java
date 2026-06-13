package br.pucminas.sistemahospedagem.repository;

import br.pucminas.sistemahospedagem.model.Aluguel;
import br.pucminas.sistemahospedagem.model.enums.StatusAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByClienteId(Long clienteId);

    @Query("""
            SELECT COUNT(a) > 0 FROM Aluguel a
            WHERE a.quarto.id = :quartoId
              AND a.status NOT IN ('CANCELADO', 'CONCLUIDO')
              AND a.dataPrevistaEntrada < :saida
              AND a.dataPrevistaSaida > :entrada
            """)
    boolean existeConflitoDeDatas(
            @Param("quartoId") Long quartoId,
            @Param("entrada") LocalDateTime entrada,
            @Param("saida") LocalDateTime saida
    );

    List<Aluguel> findByStatus(StatusAluguel status);
}