package br.weg.net.security.sec.repository;

import br.weg.net.security.sec.model.Entity.UsuarioDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDetailsRepository extends JpaRepository<UsuarioDetails, Long> {

    Optional<UsuarioDetails> findByUsername(String username);
}
