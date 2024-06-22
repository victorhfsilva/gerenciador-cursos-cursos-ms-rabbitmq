package com.example.cursosms.mapper;

import com.example.cursosms.model.Curso;
import com.example.cursosms.model.requests.CursoRequest;
import com.example.cursosms.model.resources.CursoResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CursoMapper {

    @Mapping(target = "professor", ignore = true)
    Curso map(CursoRequest cursoRequest);

    CursoResource map(Curso curso);

}
