package com.example.cursosms.fixture;

import com.example.cursosms.model.Curso;

import java.util.List;

public interface CursoFixture {
    static Curso buildValido() {
        return builder().build();
    }

    private static Curso.CursoBuilder builder() {
        return Curso.builder()
                .nome("Java")
                .descricao("Curso de Java")
                .cargaHoraria(40)
                .professor(ProfessorFixture.buildValido())
                .alunos(List.of(AlunoFixture.buildValido()));
    }
}
