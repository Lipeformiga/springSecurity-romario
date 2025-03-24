package br.weg.net.security.model.entity;

import br.weg.net.security.sec.model.Entity.UsuarioDetails;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private UsuarioDetails usuarioDetails;
}