package com.example.cursosms.fixture;

import com.example.cursosms.model.dto.AlunoRequest;

import java.util.UUID;

public interface AlunoRequestFixture {
    static AlunoRequest buildValido() {
        return builder().build();
    }

    private static AlunoRequest.AlunoRequestBuilder builder() {
        return AlunoRequest.builder()
                .usuarioId(UUID.fromString("28bbaa9d-4b9b-4efb-9bd7-5f51de312e9c"));
    }
}
