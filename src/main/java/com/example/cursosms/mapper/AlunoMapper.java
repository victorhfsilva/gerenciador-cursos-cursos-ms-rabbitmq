package com.example.cursosms.mapper;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlunoMapper {
    AlunoResource map(Aluno aluno);
    Aluno map(AlunoRequest alunoRequest);

}
