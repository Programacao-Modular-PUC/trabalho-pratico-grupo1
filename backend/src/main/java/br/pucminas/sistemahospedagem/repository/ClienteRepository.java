package br.pucminas.sistemahospedagem.repository;

import br.pucminas.sistemahospedagem.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCPF(String CPF);
    Optional<Cliente> findByEmail(String email);
}