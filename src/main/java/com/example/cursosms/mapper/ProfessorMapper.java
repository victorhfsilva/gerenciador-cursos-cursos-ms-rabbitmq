package com.example.cursosms.mapper;

import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.model.resources.ProfessorResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessorMapper {
    Professor map(ProfessorRequest professorRequest);
    ProfessorResource map(Professor professor);
}
