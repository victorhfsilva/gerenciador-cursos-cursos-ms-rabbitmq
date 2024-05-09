package com.example.cursosms.model.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoResource extends RepresentationModel<AlunoResource> {
    private UUID usuarioId;
}
