package com.example.cursosms.mapper;

import com.example.cursosms.model.Curso;
import com.example.cursosms.model.resource.CursoResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoCursoResourceMapper {
    CursoResource cursoToCursoResource(Curso curso);
}
