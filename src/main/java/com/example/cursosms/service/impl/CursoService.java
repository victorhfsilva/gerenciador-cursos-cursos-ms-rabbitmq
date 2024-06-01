package com.example.cursosms.service.impl;

import com.example.cursosms.mapper.CursoCursoRequestMapper;
import com.example.cursosms.mapper.CursoCursoResourceMapper;
import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.Curso;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.dto.CursoRequest;
import com.example.cursosms.model.resource.CursoResource;
import com.example.cursosms.repository.AlunoRepository;
import com.example.cursosms.repository.CursoRepository;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.ICursoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CursoService implements ICursoService {

    private CursoRepository cursoRepository;
    private CursoCursoRequestMapper cursoCursoRequestMapper;
    private CursoCursoResourceMapper cursoCursoResourceMapper;
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    @Transactional
    public CursoResource save(CursoRequest cursoDto) {
        Curso curso = cursoCursoRequestMapper.cursoRequestToCurso(cursoDto);
        Professor professor = professorRepository.findByUsuarioId(cursoDto.professor()).orElseThrow();
        curso.setProfessor(professor);

        Curso cursoSalvo = cursoRepository.save(curso);

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(cursoSalvo);

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResource;
    }

    public CursoResource findById(Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow();

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(curso);

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResource;
    }

    public Page<CursoResource> findByProfessorId(UUID professorId, Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findCursosByProfessorId(professorId, pageable);

        Page<CursoResource> cursoResources = cursos
                .map(curso ->
                        cursoCursoResourceMapper
                                .cursoToCursoResource(curso));

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResources;
    }

    public Page<CursoResource> findByAlunoId(UUID alunoId, Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findCursosByAlunoId(alunoId, pageable);

        Page<CursoResource> cursoResources = cursos
                .map(curso ->
                        cursoCursoResourceMapper
                                .cursoToCursoResource(curso));

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResources;
    }

    public Page<CursoResource> findAll(Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findAll(pageable);

        Page<CursoResource> cursoResources = cursos
                .map(curso ->
                        cursoCursoResourceMapper
                                .cursoToCursoResource(curso));

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResources;
    }

    public CursoResource update(Long id, CursoRequest cursoDto) {
        cursoRepository.findById(id).orElseThrow();

        Curso curso = cursoCursoRequestMapper.cursoRequestToCurso(cursoDto);
        Professor professor = professorRepository.findByUsuarioId(cursoDto.professor()).orElseThrow();

        curso.setProfessor(professor);
        curso.setId(id);

        Curso cursoAtualizado = cursoRepository.save(curso);

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(cursoAtualizado);

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResource;
    }

    public CursoResource addAluno(Long id, UUID alunoId) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        Aluno aluno = alunoRepository.findByUsuarioId(alunoId).orElseThrow();

        List<Aluno> alunos = curso.getAlunos();
        alunos.add(aluno);

        curso.setAlunos(alunos);

        Curso cursoAtualizado = cursoRepository.save(curso);

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(cursoAtualizado);

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResource;
    }

    public CursoResource removeAluno(Long id, UUID alunoId) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        Aluno aluno = alunoRepository.findByUsuarioId(alunoId).orElseThrow();

        List<Aluno> alunos = curso.getAlunos();
        alunos.remove(aluno);

        curso.setAlunos(alunos);

        Curso cursoAtualizado = cursoRepository.save(curso);

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(cursoAtualizado);

        //Link to Self
        //Link to Professor
        //Link to Alunos do Curso

        return cursoResource;
    }

    @Transactional
    public CursoResource delete(Long id){
        Curso curso = cursoRepository.findById(id).orElseThrow();

        //Configurar o Cascade Type
        cursoRepository.delete(curso);

        CursoResource cursoResource = cursoCursoResourceMapper.cursoToCursoResource(curso);

        //Link to Self

        return cursoResource;
    }
}
