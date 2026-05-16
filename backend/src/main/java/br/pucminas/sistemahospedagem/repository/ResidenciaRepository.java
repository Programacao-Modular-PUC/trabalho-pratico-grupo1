package br.pucminas.sistemahospedagem.repository;

import br.pucminas.sistemahospedagem.model.Residencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidenciaRepository extends JpaRepository<Residencia, Long> {
}