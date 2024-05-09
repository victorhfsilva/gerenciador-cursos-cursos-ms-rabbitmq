package com.example.cursosms.fixture;

import com.example.cursosms.model.dto.AlunoRequest;
import com.example.cursosms.model.dto.ProfessorRequest;

import java.util.UUID;

public interface ProfessorRequestFixture {

    static ProfessorRequest buildValido() {
        return builder().build();
    }

    private static ProfessorRequest.ProfessorRequestBuilder builder() {
        return ProfessorRequest.builder()
                .usuarioId(UUID.fromString("18bbaa9d-4b9b-4efb-9bd7-5f51de312e9b"));
    }
}
