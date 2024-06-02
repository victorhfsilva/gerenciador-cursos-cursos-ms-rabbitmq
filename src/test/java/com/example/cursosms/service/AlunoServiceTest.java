package com.example.cursosms.service;

import com.example.cursosms.fixture.AlunoFixture;
import com.example.cursosms.fixture.AlunoRequestFixture;
import com.example.cursosms.fixture.AlunoResourceFixture;
import com.example.cursosms.mapper.AlunoAlunoRequestMapper;
import com.example.cursosms.mapper.AlunoAlunoResourceMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.dto.AlunoRequest;
import com.example.cursosms.model.resource.AlunoResource;
import com.example.cursosms.repository.AlunoRepository;
import com.example.cursosms.service.impl.AlunoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AlunoAlunoResourceMapper alunoAlunoResourceMapper;

    @Mock
    private AlunoAlunoRequestMapper alunoAlunoRequestMapper;

    @Test
    void saveTest(){
        AlunoRequest alunoRequest = AlunoRequestFixture.buildValido();
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoAlunoRequestMapper.alunoRequestToAluno(alunoRequest)).thenReturn(aluno);
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
        when(alunoAlunoResourceMapper.alunoToAlunoResource(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoService.save(alunoRequest);

        assertEquals(alunoResourceSalvo.getUsuarioId(), alunoRequest.usuarioId());
    }

    @Test
    void findByUsuarioIdTest(){
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(alunoAlunoResourceMapper.alunoToAlunoResource(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoService.findByUsuarioId(aluno.getUsuarioId());

        assertEquals(alunoResourceSalvo.getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void findAlunosByCursoIdTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Aluno aluno = AlunoFixture.buildComCursos();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findAlunosByCursoId(aluno.getCursos().get(0).getId(), pageable)).thenReturn(new PageImpl<>(List.of(aluno)));
        when(alunoAlunoResourceMapper.alunoToAlunoResource(aluno)).thenReturn(alunoResource);

        Page<AlunoResource> alunoResources = alunoService.findAlunosByCursoId(aluno.getCursos().get(0).getId(), pageable);

        assertEquals(alunoResources.getContent().get(0).getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void findAllTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(aluno)));
        when(alunoAlunoResourceMapper.alunoToAlunoResource(aluno)).thenReturn(alunoResource);

        Page<AlunoResource> alunoResources = alunoService.findAll(pageable);

        assertEquals(alunoResources.getContent().get(0).getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void deleteTest(){
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(alunoAlunoResourceMapper.alunoToAlunoResource(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoService.delete(aluno.getUsuarioId());

        assertEquals(alunoResourceSalvo.getUsuarioId(), aluno.getUsuarioId());
    }
    
}
