package com.example.cursosms.mapper;

import com.example.cursosms.model.Professor;
import com.example.cursosms.model.resources.ProfessorResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorProfessorResourceMapper {
    ProfessorResource professorToProfessorResource(Professor professor);
}
