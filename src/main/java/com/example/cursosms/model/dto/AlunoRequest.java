package com.example.cursosms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AlunoRequest (
        UUID usuarioId
){

}
