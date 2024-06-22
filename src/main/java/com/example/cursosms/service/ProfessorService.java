package com.example.cursosms.service;

import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.model.resources.ProfessorResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProfessorService {
    ProfessorResource save(ProfessorRequest professorDto);

    ProfessorResource findByUsuarioId(UUID usuarioId);

    Page<ProfessorResource> findAll(Pageable pageable);

    ProfessorResource delete(UUID id);
}
