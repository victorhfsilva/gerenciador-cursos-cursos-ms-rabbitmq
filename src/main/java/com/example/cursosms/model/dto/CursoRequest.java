package com.example.cursosms.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CursoRequest (
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(max = 100)
        String descricao,

        @NotNull
        Integer cargaHoraria,

        @NotNull
        UUID professorUsuarioId
){

}
