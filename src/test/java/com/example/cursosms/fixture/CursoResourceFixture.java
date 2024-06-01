package com.example.cursosms.fixture;

import com.example.cursosms.model.resource.CursoResource;
import java.util.List;

public interface CursoResourceFixture {

    static CursoResource buildValido() {
        return builder().build();
    }

    private static CursoResource.CursoResourceBuilder builder() {
        return CursoResource.builder()
                .id(1L)
                .nome("Java")
                .descricao("Curso de Java")
                .cargaHoraria(40);
    }
}
