package com.example.cursosms.fixture;

import com.example.cursosms.model.Aluno;
import com.example.cursosms.model.Curso;

import java.util.ArrayList;
import java.util.List;

public interface CursoFixture {
    static Curso buildValido() {
        return builder().build();
    }

    static Curso buildSemAlunos() {
        return builder()
                .alunos(new ArrayList<>(List.of()))
                .build();
    }

    private static Curso.CursoBuilder builder() {
        return Curso.builder()
                .nome("Java")
                .descricao("Curso de Java")
                .cargaHoraria(40)
                .professor(ProfessorFixture.buildValido())
                .alunos(new ArrayList(List.of(AlunoFixture.buildValido())));
    }
}
