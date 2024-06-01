package com.example.cursosms.service;

import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.ProfessorResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProfessorService {
    ProfessorResource save(ProfessorRequest professorDto);

    ProfessorResource findByUsuarioId(UUID usuarioId);

    Page<ProfessorResource> findAll(Pageable pageable);

    ProfessorResource delete(UUID id);
}
