package com.example.cursosms.mapper;

import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.ProfessorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorProfessorRequestMapper {
    Professor professorRequestToProfessor(ProfessorRequest professorRequest);
}
