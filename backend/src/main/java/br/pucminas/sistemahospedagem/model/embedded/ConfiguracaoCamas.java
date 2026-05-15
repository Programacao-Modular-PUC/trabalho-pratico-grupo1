package br.pucminas.sistemahospedagem.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ConfiguracaoCamas {

    private int camasSolteiro;
    private int camasCasal;
    private int camasQueen;
    private int camasKing;

    public int calculaCapacidadeTotal() {
        return (camasSolteiro * 1) +
                (camasCasal * 2) +
                (camasQueen * 2) +
                (camasKing * 2);
    }
}