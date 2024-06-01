package com.example.cursosms.service.impl;

import com.example.cursosms.controller.CursoController;
import com.example.cursosms.controller.ProfessorController;
import com.example.cursosms.mapper.ProfessorProfessorRequestMapper;
import com.example.cursosms.mapper.ProfessorProfessorResourceMapper;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.IProfessorService;
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
public class ProfessorService implements IProfessorService {

    private ProfessorRepository professorRepository;
    private ProfessorProfessorRequestMapper professorProfessorRequestMapper;
    private ProfessorProfessorResourceMapper professorProfessorResourceMapper;

    public ProfessorResource save(ProfessorRequest professorDto) {
        //Verificar se o UUID é válido

        Professor professor = professorProfessorRequestMapper.professorRequestToProfessor(professorDto);
        Professor professorSalvo = professorRepository.save(professor);

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professorSalvo);

        professorResource.add(linkTo(methodOn(ProfessorController.class).registrarProfessor(professorDto)).withSelfRel());
        professorResource.add(linkTo(methodOn(CursoController.class).buscarCursosPorProfessorId(professorDto.usuarioId(), Pageable.unpaged())).withRel("cursos"));

        return professorResource;
    }

    public ProfessorResource findByUsuarioId(UUID usuarioId) {
        Professor professor = professorRepository.findByUsuarioId(usuarioId).orElseThrow();

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professor);

        professorResource.add(linkTo(methodOn(ProfessorController.class).buscarProfessorPorId(usuarioId)).withSelfRel());
        professorResource.add(linkTo(methodOn(CursoController.class).buscarCursosPorProfessorId(professor.getUsuarioId(), Pageable.unpaged())).withRel("cursos"));

        return professorResource;
    }

    public Page<ProfessorResource> findAll(Pageable pageable) {
        Page<Professor> professors = professorRepository.findAll(pageable);

        Page<ProfessorResource> professorResources = professors
                .map(professor ->
                        professorProfessorResourceMapper
                                .professorToProfessorResource(professor)
                                .add(linkTo(methodOn(ProfessorController.class)
                                        .buscarProfessorPorId(professor.getUsuarioId())).withSelfRel())
                                .add(linkTo(methodOn(CursoController.class)
                                        .buscarCursosPorProfessorId(professor.getUsuarioId(), Pageable.unpaged()))
                                        .withRel("curso")));

        return professorResources;
    }

    @Transactional
    public ProfessorResource delete(UUID id){
        Professor professor = professorRepository.findByUsuarioId(id).orElseThrow();

        professorRepository.delete(professor);

        ProfessorResource professorResource = professorProfessorResourceMapper
                .professorToProfessorResource(professor);

        professorResource.add(linkTo(methodOn(ProfessorController.class).deletarProfessor(id)).withSelfRel());

        return professorResource;
    }
    
}
