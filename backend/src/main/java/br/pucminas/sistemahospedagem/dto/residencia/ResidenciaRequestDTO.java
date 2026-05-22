package br.pucminas.sistemahospedagem.dto.residencia;

import br.pucminas.sistemahospedagem.model.embedded.Endereco;

public record ResidenciaRequestDTO(
        String telefone,
        String email,
        Endereco endereco,
        Long anfitriaoId
) {
}