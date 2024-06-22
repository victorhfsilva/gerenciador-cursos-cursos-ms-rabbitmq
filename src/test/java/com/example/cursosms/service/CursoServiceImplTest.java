package com.example.cursosms.service;

import com.example.cursosms.fixture.*;
import com.example.cursosms.mapper.CursoMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.Curso;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.CursoRequest;
import com.example.cursosms.model.resources.CursoResource;
import com.example.cursosms.repository.AlunoRepository;
import com.example.cursosms.repository.CursoRepository;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.impl.CursoServiceImpl;
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
class CursoServiceImplTest {

    @InjectMocks
    private CursoServiceImpl cursoServiceImpl;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private CursoMapper cursoMapper;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Test
    void saveTest(){
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        Curso curso = CursoFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();

        when(cursoMapper.map(cursoRequest)).thenReturn(curso);
        when(professorRepository.findByUsuarioId(cursoRequest.professor())).thenReturn(Optional.of(professor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoMapper.map(curso)).thenReturn(CursoResourceFixture.buildValido());

        CursoResource cursoSalvo = cursoServiceImpl.save(cursoRequest);

        assertEquals(cursoSalvo.getNome(), cursoRequest.nome());
    }

    @Test
    void findByIdTest(){
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceBuscado = cursoServiceImpl.findById(curso.getId());
        assertEquals(cursoResourceBuscado.getNome(), curso.getNome());
    }

    @Test
    void findByProfessorIdTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findCursosByProfessorId(curso.getProfessor().getUsuarioId(), pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoServiceImpl.findByProfessorId(curso.getProfessor().getUsuarioId(), pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    void findByAlunoId() {
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findCursosByAlunoId(curso.getAlunos().get(0).getUsuarioId(), pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoServiceImpl.findByAlunoId(curso.getAlunos().get(0).getUsuarioId(), pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoServiceImpl.findAll(pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    void update(){
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        Curso curso = CursoFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoMapper.map(cursoRequest)).thenReturn(curso);
        when(professorRepository.findByUsuarioId(cursoRequest.professor())).thenReturn(Optional.of(professor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceAtualizado = cursoServiceImpl.update(curso.getId(), cursoRequest);
        assertEquals(cursoResourceAtualizado.getNome(), cursoRequest.nome());
    }

    @Test
    void addAluno() {
        Curso cursoSemAlunos = CursoFixture.buildSemAlunos();
        Aluno aluno = AlunoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();
        Curso curso = CursoFixture.buildValido();

        when(cursoRepository.findById(cursoSemAlunos.getId())).thenReturn(Optional.of(cursoSemAlunos));
        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceAtualizado = cursoServiceImpl.addAluno(cursoSemAlunos.getId(), aluno.getUsuarioId());
        assertEquals(cursoResourceAtualizado.getNome(), cursoSemAlunos.getNome());
    }

    @Test
    void removeAluno(){
        Curso curso = CursoFixture.buildValido();
        Aluno aluno = AlunoFixture.buildValido();
        Curso cursoSemAlunos = CursoFixture.buildSemAlunos();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoSemAlunos);
        when(cursoMapper.map(cursoSemAlunos)).thenReturn(CursoResourceFixture.buildValido());

        CursoResource cursoResource = cursoServiceImpl.removeAluno(curso.getId(), aluno.getUsuarioId());
        assertEquals(cursoResource.getNome(), curso.getNome());
    }

    @Test
    void delete(){
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoMapper.map(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceDeletado = cursoServiceImpl.delete(curso.getId());
        assertEquals(cursoResourceDeletado.getNome(), curso.getNome());
    }

}
