package br.pucminas.sistemahospedagem.dto;

import jakarta.validation.constraints.NotBlank;

public record AplicarTarifaDTO(
    @NotBlank(message = "O nome da regra é obrigatório")
    String nomeRegra
) {}