package br.pucminas.sistemahospedagem.dto.quarto;

import br.pucminas.sistemahospedagem.model.enums.TipoCamaCasal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record QuartoRequestDTO(
        @NotNull @Min(1) Integer numero,
        @NotNull @Positive Double valorBase,
        @NotNull @Min(1) Integer capacidadeMaxima,
        @NotNull Long residenciaId,
        @NotNull String tipo,

        // individual
        Integer numeroCamasSolteiro,
        Double valorAdicionalPorCama,

        // casal
        TipoCamaCasal tipoCamaCasal,
        Boolean bercoInstalado,
        Double valorAdicionalBerco,
        Double valorAdicionalConforto
) {
}
