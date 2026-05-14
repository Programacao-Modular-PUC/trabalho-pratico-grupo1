package br.pucminas.sistemahospedagem.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Endereco {

    private String rua;
    private String numero;
    private String bairro;
    private String CEP;
    private String cidade;
    private String estado;

    public String formatarEnderecoCompleto() {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + ", CEP: " + CEP;
    }
}