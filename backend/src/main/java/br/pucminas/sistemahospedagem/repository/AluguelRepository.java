package br.pucminas.sistemahospedagem.repository;
import br.pucminas.sistemahospedagem.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {}