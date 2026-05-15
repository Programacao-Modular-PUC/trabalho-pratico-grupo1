package br.pucminas.sistemahospedagem.dto;

import br.pucminas.sistemahospedagem.model.embedded.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnfitriaoDTO {
    private Long id;
    private String nome;
    private String CPF;
    private String email;
    private String telefone;
    private Endereco endereco;
}