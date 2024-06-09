package com.example.cursosms.fixture;

import com.example.cursosms.model.resources.AlunoResource;

import java.util.UUID;

public interface AlunoResourceFixture {
    static AlunoResource buildValido() {
        return builder().build();
    }

    private static AlunoResource.AlunoResourceBuilder builder() {
        return AlunoResource.builder()
                .usuarioId(UUID.fromString("28bbaa9d-4b9b-4efb-9bd7-5f51de312e9c"));
    }
}
