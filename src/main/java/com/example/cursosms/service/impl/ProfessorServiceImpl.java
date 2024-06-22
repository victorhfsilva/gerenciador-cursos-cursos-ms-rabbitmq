package com.example.cursosms.service.impl;

import com.example.cursosms.controller.CursoController;
import com.example.cursosms.controller.ProfessorController;
import com.example.cursosms.mapper.ProfessorMapper;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.model.resources.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.ProfessorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private ProfessorRepository professorRepository;
    private ProfessorMapper professorMapper;

    public ProfessorResource save(ProfessorRequest professorDto) {
        Professor professor = professorMapper.map(professorDto);
        Professor professorSalvo = professorRepository.save(professor);

        ProfessorResource professorResource = professorMapper
                .map(professorSalvo);

        professorResource.add(linkTo(methodOn(ProfessorController.class).registrarProfessor(professorDto)).withSelfRel());
        professorResource.add(linkTo(methodOn(CursoController.class).buscarCursosPorProfessorId(professorDto.usuarioId(), Pageable.unpaged())).withRel("cursos"));

        return professorResource;
    }

    public ProfessorResource findByUsuarioId(UUID usuarioId) {
        Professor professor = professorRepository.findByUsuarioId(usuarioId).orElseThrow();

        ProfessorResource professorResource = professorMapper
                .map(professor);

        professorResource.add(linkTo(methodOn(ProfessorController.class).buscarProfessorPorId(usuarioId)).withSelfRel());
        professorResource.add(linkTo(methodOn(CursoController.class).buscarCursosPorProfessorId(professor.getUsuarioId(), Pageable.unpaged())).withRel("cursos"));

        return professorResource;
    }

    public Page<ProfessorResource> findAll(Pageable pageable) {
        Page<Professor> professors = professorRepository.findAll(pageable);

        return professors
                .map(professor ->
                        professorMapper
                                .map(professor)
                                .add(linkTo(methodOn(ProfessorController.class)
                                        .buscarProfessorPorId(professor.getUsuarioId())).withSelfRel())
                                .add(linkTo(methodOn(CursoController.class)
                                        .buscarCursosPorProfessorId(professor.getUsuarioId(), Pageable.unpaged()))
                                        .withRel("curso")));

    }

    @Transactional
    public ProfessorResource delete(UUID id){
        Professor professor = professorRepository.findByUsuarioId(id).orElseThrow();

        professorRepository.delete(professor);

        ProfessorResource professorResource = professorMapper
                .map(professor);

        professorResource.add(linkTo(methodOn(ProfessorController.class).deletarProfessor(id)).withSelfRel());

        return professorResource;
    }
    
}
