package com.example.cursosms.model.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfessorRequest (
        UUID usuarioId
){

}
