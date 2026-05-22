package br.pucminas.sistemahospedagem.dto.quarto;

import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.model.QuartoCasal;
import br.pucminas.sistemahospedagem.model.QuartoIndividual;
import br.pucminas.sistemahospedagem.model.enums.TipoCamaCasal;

public record QuartoResponseDTO(
        Long id,
        Integer numero,
        Double valorBase,
        Integer capacidadeMaxima,
        Long residenciaId,
        String tipo,

        // individual
        Integer numeroCamasSolteiro,
        Double valorAdicionalPorCama,

        // casal
        TipoCamaCasal tipoCamaCasal,
        Boolean bercoInstalado,
        Double valorAdicionalBerco,
        Double valorAdicionalConforto
) {
    public static QuartoResponseDTO fromEntity(Quarto q) {
        Long residenciaId = q.getResidencia() != null ? q.getResidencia().getId() : null;
        if (q instanceof QuartoIndividual ind) {
            return new QuartoResponseDTO(
                    ind.getId(),
                    ind.getNumero(),
                    ind.getValorBase(),
                    ind.getCapacidadeMaxima(),
                    residenciaId,
                    "INDIVIDUAL",
                    ind.getNumeroCamasSolteiro(),
                    ind.getValorAdicionalPorCama(),
                    null,
                    null,
                    null,
                    null
            );
        } else if (q instanceof QuartoCasal casal) {
            return new QuartoResponseDTO(
                    casal.getId(),
                    casal.getNumero(),
                    casal.getValorBase(),
                    casal.getCapacidadeMaxima(),
                    residenciaId,
                    "CASAL",
                    null,
                    null,
                    casal.getTipoCamaCasal(),
                    casal.isBercoInstalado(),
                    casal.getValorAdicionalBerco(),
                    casal.getValorAdicionalConforto()
            );
        } else {
            return new QuartoResponseDTO(
                    q.getId(), q.getNumero(), q.getValorBase(), q.getCapacidadeMaxima(), residenciaId,
                    "UNKNOWN", null, null, null, null, null, null
            );
        }
    }
}
