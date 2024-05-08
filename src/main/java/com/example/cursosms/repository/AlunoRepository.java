package com.example.cursosms.repository;

import com.example.cursosms.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    Optional<Aluno> findByUsuarioId(UUID usuarioId);
}
