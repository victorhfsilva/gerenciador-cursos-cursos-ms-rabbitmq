package com.example.cursosms.model.resource;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoResource extends RepresentationModel<CursoResource> {
    private Long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
}
