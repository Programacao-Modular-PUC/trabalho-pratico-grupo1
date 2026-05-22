package br.pucminas.sistemahospedagem.repository;

import br.pucminas.sistemahospedagem.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailLogin(String emailLogin);
}