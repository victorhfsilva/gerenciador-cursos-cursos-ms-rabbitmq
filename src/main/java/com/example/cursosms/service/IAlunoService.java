package com.example.cursosms.service;

import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAlunoService {

    AlunoResource save(AlunoRequest alunoDto);

    AlunoResource findByUsuarioId(UUID usuarioId);

    Page<AlunoResource> findAlunosByCursoId(Long cursoId, Pageable pageable);

    Page<AlunoResource> findAll(Pageable pageable);

    AlunoResource delete(UUID id);
}
