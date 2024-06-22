package com.example.cursosms.service;

import com.example.cursosms.fixture.AlunoFixture;
import com.example.cursosms.fixture.AlunoRequestFixture;
import com.example.cursosms.fixture.AlunoResourceFixture;
import com.example.cursosms.mapper.AlunoMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import com.example.cursosms.repository.AlunoRepository;
import com.example.cursosms.service.impl.AlunoServiceImpl;
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
class AlunoServiceImplTest {

    @InjectMocks
    private AlunoServiceImpl alunoServiceImpl;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AlunoMapper alunoMapper;

    @Test
    void saveTest(){
        AlunoRequest alunoRequest = AlunoRequestFixture.buildValido();
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoMapper.map(alunoRequest)).thenReturn(aluno);
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
        when(alunoMapper.map(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoServiceImpl.save(alunoRequest);

        assertEquals(alunoResourceSalvo.getUsuarioId(), alunoRequest.usuarioId());
    }

    @Test
    void findByUsuarioIdTest(){
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(alunoMapper.map(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoServiceImpl.findByUsuarioId(aluno.getUsuarioId());

        assertEquals(alunoResourceSalvo.getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void findAlunosByCursoIdTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Aluno aluno = AlunoFixture.buildComCursos();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findAlunosByCursoId(aluno.getCursos().get(0).getId(), pageable)).thenReturn(new PageImpl<>(List.of(aluno)));
        when(alunoMapper.map(aluno)).thenReturn(alunoResource);

        Page<AlunoResource> alunoResources = alunoServiceImpl.findAlunosByCursoId(aluno.getCursos().get(0).getId(), pageable);

        assertEquals(alunoResources.getContent().get(0).getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void findAllTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(aluno)));
        when(alunoMapper.map(aluno)).thenReturn(alunoResource);

        Page<AlunoResource> alunoResources = alunoServiceImpl.findAll(pageable);

        assertEquals(alunoResources.getContent().get(0).getUsuarioId(), aluno.getUsuarioId());
    }

    @Test
    void deleteTest(){
        Aluno aluno = AlunoFixture.buildValido();
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(alunoMapper.map(aluno)).thenReturn(alunoResource);

        AlunoResource alunoResourceSalvo = alunoServiceImpl.delete(aluno.getUsuarioId());

        assertEquals(alunoResourceSalvo.getUsuarioId(), aluno.getUsuarioId());
    }
    
}
