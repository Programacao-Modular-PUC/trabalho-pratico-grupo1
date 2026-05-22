package br.pucminas.sistemahospedagem.dto;

import br.pucminas.sistemahospedagem.model.enums.TipoUsuario;
import br.pucminas.sistemahospedagem.model.embedded.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroDTO {
    private String emailLogin;
    private String senha;
    private TipoUsuario papel;
    private String nome;
    private String CPF;
    private String telefone;
    private Endereco endereco;
}