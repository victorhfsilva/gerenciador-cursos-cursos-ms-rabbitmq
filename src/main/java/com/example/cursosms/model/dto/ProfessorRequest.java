package com.example.cursosms.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfessorRequest (
        UUID usuarioId
){

}
