package com.example.cursosms.service;

import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.requests.ProfessorRequest;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.UUID;

public interface MensagensService {
    void registrarAluno(@Payload AlunoRequest alunoRequest);
    void registrarProfessor(@Payload ProfessorRequest professorRequest);
    void deletarAluno(UUID usuarioId);
    void deletarProfessor(UUID usuarioId);
    void logErro();
}
