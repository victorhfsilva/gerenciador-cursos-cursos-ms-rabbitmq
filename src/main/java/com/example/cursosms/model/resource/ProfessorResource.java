package com.example.cursosms.model.resource;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorResource {
    private UUID usuarioId;
}
