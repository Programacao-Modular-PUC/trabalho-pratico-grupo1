package br.pucminas.sistemahospedagem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String emailLogin;
    private String senha;
}
