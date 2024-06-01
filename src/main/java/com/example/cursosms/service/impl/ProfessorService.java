package com.example.cursosms.service.impl;

import com.example.cursosms.mapper.ProfessorProfessorRequestMapper;
import com.example.cursosms.mapper.ProfessorProfessorResourceMapper;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessorService {

    private ProfessorRepository professorRepository;
    private ProfessorProfessorRequestMapper professorProfessorRequestMapper;
    private ProfessorProfessorResourceMapper professorProfessorResourceMapper;

    public ProfessorResource save(ProfessorRequest professorDto) {
        //Verificar se o UUID é válido

        Professor professor = professorProfessorRequestMapper.professorRequestToProfessor(professorDto);
        Professor professorSalvo = professorRepository.save(professor);

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professorSalvo);

        //Link to Self
        //Link to Cursos do Professor

        return professorResource;
    }

    public ProfessorResource findByUsuarioId(UUID usuarioId) {
        Professor professor = professorRepository.findByUsuarioId(usuarioId).orElseThrow();

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professor);

        //Link to Self
        //Link to Cursos do Professor

        return professorResource;
    }

    public Page<ProfessorResource> findAll(Pageable pageable) {
        Page<Professor> professors = professorRepository.findAll(pageable);

        Page<ProfessorResource> professorResources = professors
                .map(professor ->
                        professorProfessorResourceMapper
                                .professorToProfessorResource(professor));

        //Link to Self
        //Link to Cursos do Professor

        return professorResources;
    }

    @Transactional
    public ProfessorResource delete(UUID id){
        Professor professor = professorRepository.findByUsuarioId(id).orElseThrow();

        professorRepository.delete(professor);

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professor);

        //Link to Self

        return professorResource;
    }
    
}
