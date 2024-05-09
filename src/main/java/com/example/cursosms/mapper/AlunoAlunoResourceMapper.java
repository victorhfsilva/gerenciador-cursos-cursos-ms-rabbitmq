package com.example.cursosms.mapper;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.resource.AlunoResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoAlunoResourceMapper {
    AlunoResource alunoToAlunoResource(Aluno aluno);
}
