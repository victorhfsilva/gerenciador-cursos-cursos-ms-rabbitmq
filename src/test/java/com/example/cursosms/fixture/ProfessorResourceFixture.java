package com.example.cursosms.fixture;

import com.example.cursosms.model.resource.AlunoResource;
import com.example.cursosms.model.resource.ProfessorResource;

import java.util.UUID;

public interface ProfessorResourceFixture {
    static ProfessorResource buildValido() {
        return builder().build();
    }

    private static ProfessorResource.ProfessorResourceBuilder builder() {
        return ProfessorResource.builder()
                .usuarioId(UUID.fromString("18bbaa9d-4b9b-4efb-9bd7-5f51de312e9b"));
    }
}
