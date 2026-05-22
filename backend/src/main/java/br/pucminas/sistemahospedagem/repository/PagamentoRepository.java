package br.pucminas.sistemahospedagem.repository;
import br.pucminas.sistemahospedagem.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {}