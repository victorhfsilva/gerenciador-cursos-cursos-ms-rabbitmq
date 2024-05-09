package com.example.cursosms.mapper;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.dto.AlunoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoAlunoRequestMapper {
    Aluno alunoRequestToAluno(AlunoRequest alunoRequest);
}
