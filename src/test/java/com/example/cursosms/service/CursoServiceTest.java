package com.example.cursosms.service;

import com.example.cursosms.fixture.*;
import com.example.cursosms.mapper.CursoCursoRequestMapper;
import com.example.cursosms.mapper.CursoCursoResourceMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.Curso;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.CursoRequest;
import com.example.cursosms.model.resources.CursoResource;
import com.example.cursosms.repository.AlunoRepository;
import com.example.cursosms.repository.CursoRepository;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.impl.CursoService;
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
public class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private CursoCursoResourceMapper cursoCursoResourceMapper;

    @Mock
    private CursoCursoRequestMapper cursoCursoRequestMapper;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Test
    public void saveTest(){
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        Curso curso = CursoFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();

        when(cursoCursoRequestMapper.cursoRequestToCurso(cursoRequest)).thenReturn(curso);
        when(professorRepository.findByUsuarioId(cursoRequest.professor())).thenReturn(Optional.of(professor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(CursoResourceFixture.buildValido());

        CursoResource cursoSalvo = cursoService.save(cursoRequest);

        assertEquals(cursoSalvo.getNome(), cursoRequest.nome());
    }

    @Test
    public void findByIdTest(){
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceBuscado = cursoService.findById(curso.getId());
        assertEquals(cursoResourceBuscado.getNome(), curso.getNome());
    }

    @Test
    public void findByProfessorIdTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findCursosByProfessorId(curso.getProfessor().getUsuarioId(), pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoService.findByProfessorId(curso.getProfessor().getUsuarioId(), pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    public void findByAlunoId() {
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findCursosByAlunoId(curso.getAlunos().get(0).getUsuarioId(), pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoService.findByAlunoId(curso.getAlunos().get(0).getUsuarioId(), pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    public void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(curso)));
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        Page<CursoResource> cursoResources = cursoService.findAll(pageable);
        assertEquals(cursoResources.getContent().get(0).getNome(), curso.getNome());
    }

    @Test
    public void update(){
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        Curso curso = CursoFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoCursoRequestMapper.cursoRequestToCurso(cursoRequest)).thenReturn(curso);
        when(professorRepository.findByUsuarioId(cursoRequest.professor())).thenReturn(Optional.of(professor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceAtualizado = cursoService.update(curso.getId(), cursoRequest);
        assertEquals(cursoResourceAtualizado.getNome(), cursoRequest.nome());
    }

    @Test
    public void addAluno() {
        Curso cursoSemAlunos = CursoFixture.buildSemAlunos();
        Aluno aluno = AlunoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();
        Curso curso = CursoFixture.buildValido();

        when(cursoRepository.findById(cursoSemAlunos.getId())).thenReturn(Optional.of(cursoSemAlunos));
        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceAtualizado = cursoService.addAluno(cursoSemAlunos.getId(), aluno.getUsuarioId());
        assertEquals(cursoResourceAtualizado.getNome(), cursoSemAlunos.getNome());
    }

    @Test
    public void removeAluno(){
        Curso curso = CursoFixture.buildValido();
        Aluno aluno = AlunoFixture.buildValido();
        Curso cursoSemAlunos = CursoFixture.buildSemAlunos();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(alunoRepository.findByUsuarioId(aluno.getUsuarioId())).thenReturn(Optional.of(aluno));
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoSemAlunos);
        when(cursoCursoResourceMapper.cursoToCursoResource(cursoSemAlunos)).thenReturn(CursoResourceFixture.buildValido());

        CursoResource cursoResource = cursoService.removeAluno(curso.getId(), aluno.getUsuarioId());
        assertEquals(cursoResource.getNome(), curso.getNome());
    }

    @Test
    public void delete(){
        Curso curso = CursoFixture.buildValido();
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
        when(cursoCursoResourceMapper.cursoToCursoResource(curso)).thenReturn(cursoResource);

        CursoResource cursoResourceDeletado = cursoService.delete(curso.getId());
        assertEquals(cursoResourceDeletado.getNome(), curso.getNome());
    }

}
