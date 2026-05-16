package br.pucminas.sistemahospedagem.dto.residencia;

import br.pucminas.sistemahospedagem.model.Residencia;

public record ResidenciaResponseDTO(
        Long id,
        String telefone,
        String email,
        String cidade,
        String estado
) {
    public static ResidenciaResponseDTO fromEntity(Residencia r) {
        return new ResidenciaResponseDTO(
                r.getId(),
                r.getTelefone(),
                r.getEmail(),
                r.getEndereco().getCidade(),
                r.getEndereco().getEstado()
        );
    }
}