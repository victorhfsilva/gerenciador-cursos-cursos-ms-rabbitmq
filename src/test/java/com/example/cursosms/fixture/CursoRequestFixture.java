package com.example.cursosms.fixture;

import com.example.cursosms.model.Curso;
import com.example.cursosms.model.dto.CursoRequest;

import java.util.List;

public interface CursoRequestFixture {
    static CursoRequest buildValido() {
        return builder().build();
    }

    private static CursoRequest.CursoRequestBuilder builder() {
        return CursoRequest.builder()
                .nome("Java")
                .descricao("Curso de Java")
                .cargaHoraria(40)
                .professor(ProfessorFixture.buildValido().getUsuarioId());
    }
}
