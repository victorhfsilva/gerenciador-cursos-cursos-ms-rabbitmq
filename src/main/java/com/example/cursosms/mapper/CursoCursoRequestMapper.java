package com.example.cursosms.mapper;

import com.example.cursosms.model.Curso;
import com.example.cursosms.model.dto.CursoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoCursoRequestMapper {
    Curso cursoRequestToCurso(CursoRequest cursoRequest);
}
