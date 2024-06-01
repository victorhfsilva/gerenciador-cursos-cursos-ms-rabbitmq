package com.example.cursosms.service.impl;

import com.example.cursosms.mapper.AlunoAlunoRequestMapper;
import com.example.cursosms.mapper.AlunoAlunoResourceMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.dto.AlunoRequest;
import com.example.cursosms.model.resource.AlunoResource;
import com.example.cursosms.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AlunoService {

    private AlunoRepository alunoRepository;
    private AlunoAlunoRequestMapper alunoAlunoRequestMapper;
    private AlunoAlunoResourceMapper alunoAlunoResourceMapper;

    @Transactional
    public AlunoResource save(AlunoRequest alunoDto) {
        //Verificar se o UUID é válido

        Aluno aluno = alunoAlunoRequestMapper.alunoRequestToAluno(alunoDto);
        Aluno alunoSalvo = alunoRepository.save(aluno);

        AlunoResource alunoResource = alunoAlunoResourceMapper.alunoToAlunoResource(alunoSalvo);

        //Link to Self
        //Link to Cursos do Aluno

        return alunoResource;
    }

    public AlunoResource findByUsuarioId(UUID usuarioId) {
        Aluno aluno = alunoRepository.findByUsuarioId(usuarioId).orElseThrow();

        AlunoResource alunoResource = alunoAlunoResourceMapper.alunoToAlunoResource(aluno);

        //Link to Self
        //Link to Cursos do Aluno

        return alunoResource;
    }

    public Page<AlunoResource> findAlunosByCursoId(Long cursoId, Pageable pageable) {
        Page<Aluno> alunos = alunoRepository.findAlunosByCursoId(cursoId, pageable);

        Page<AlunoResource> alunoResources = alunos
                .map(aluno ->
                        alunoAlunoResourceMapper
                                .alunoToAlunoResource(aluno));

        //Link to Self
        //Link to Cursos do Aluno

        return alunoResources;
    }

    public Page<AlunoResource> findAll(Pageable pageable) {
        Page<Aluno> alunos = alunoRepository.findAll(pageable);

        Page<AlunoResource> alunoResources = alunos
                .map(aluno ->
                        alunoAlunoResourceMapper
                                .alunoToAlunoResource(aluno));

        //Link to Self
        //Link to Cursos do Aluno

        return alunoResources;
    }

    @Transactional
    public AlunoResource delete(UUID id){
        Aluno aluno = alunoRepository.findByUsuarioId(id).orElseThrow();

        alunoRepository.delete(aluno);

        AlunoResource alunoResource = alunoAlunoResourceMapper.alunoToAlunoResource(aluno);

        //Link to Self

        return alunoResource;
    }
}
