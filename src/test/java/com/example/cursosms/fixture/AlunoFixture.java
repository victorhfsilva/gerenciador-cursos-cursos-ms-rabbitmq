package com.example.cursosms.fixture;

import com.example.cursosms.model.Aluno;
import java.util.List;
import java.util.UUID;

public interface AlunoFixture {
    static Aluno buildValido() {
        return builder().build();
    }

    static Aluno buildComCursos() {
        return builder()
                .cursos(List.of(CursoFixture.buildValido()))
                .build();
    }

    private static Aluno.AlunoBuilder builder() {
        return Aluno.builder()
                .usuarioId(UUID.fromString("28bbaa9d-4b9b-4efb-9bd7-5f51de312e9c"));
    }

}
