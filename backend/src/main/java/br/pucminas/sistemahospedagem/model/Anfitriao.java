package br.pucminas.sistemahospedagem.model;

import br.pucminas.sistemahospedagem.model.embedded.Endereco;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "anfitrioes")
public class Anfitriao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String CPF;
    private String email;
    private String telefone;

    @Embedded
    private Endereco endereco;

    public boolean validarCPF() {
        if (CPF == null || CPF.length() != 11) return false;
        return CPF.chars().distinct().count() > 1;
    }

    public void definirValorBaseQuarto(Quarto quarto, double valor) {
        quarto.setValorBase(valor);
    }
}