package com.example.cursosms.mapper;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.resources.AlunoResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoAlunoResourceMapper {
    AlunoResource alunoToAlunoResource(Aluno aluno);
}
