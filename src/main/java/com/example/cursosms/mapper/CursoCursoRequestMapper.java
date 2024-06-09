package com.example.cursosms.mapper;

import com.example.cursosms.model.Curso;
import com.example.cursosms.model.requests.CursoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoCursoRequestMapper {
    @Mapping(target = "professor", ignore = true)
    Curso cursoRequestToCurso(CursoRequest cursoRequest);
}
