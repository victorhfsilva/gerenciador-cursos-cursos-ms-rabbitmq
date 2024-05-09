package com.example.cursosms.fixture;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.Professor;

import java.util.List;
import java.util.UUID;

public interface ProfessorFixture {
    static Professor buildValido() {
        return builder().build();
    }

    static Professor buildComCurso() {
        return builder()
                .cursos(List.of(CursoFixture.buildValido()))
                .build();

    }
    private static Professor.ProfessorBuilder builder() {
        return Professor.builder()
                .usuarioId(UUID.fromString("18bbaa9d-4b9b-4efb-9bd7-5f51de312e9b"));
    }
}
