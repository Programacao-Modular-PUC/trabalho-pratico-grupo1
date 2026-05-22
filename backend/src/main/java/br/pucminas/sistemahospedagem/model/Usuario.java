package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailLogin;
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario papel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "anfitriao_id")
    private Anfitriao anfitriao;

    public void autenticar() {
        // sera implementado com Spring Security futuramente
    }
}