package com.example.cursosms.model.resources;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CursoResource extends RepresentationModel<CursoResource> {
    private Long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
}
