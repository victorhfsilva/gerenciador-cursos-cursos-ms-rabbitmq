package com.example.cursosms.model.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorResource extends RepresentationModel<ProfessorResource> {
    private UUID usuarioId;
}
