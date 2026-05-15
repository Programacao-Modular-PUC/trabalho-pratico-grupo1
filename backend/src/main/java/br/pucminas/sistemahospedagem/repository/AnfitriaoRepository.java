package br.pucminas.sistemahospedagem.repository;

import br.pucminas.sistemahospedagem.model.Anfitriao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnfitriaoRepository extends JpaRepository<Anfitriao, Long> {
    Optional<Anfitriao> findByCPF(String CPF);
    Optional<Anfitriao> findByEmail(String email);
}