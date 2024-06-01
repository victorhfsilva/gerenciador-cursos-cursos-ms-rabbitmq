package com.example.cursosms.repository;

import com.example.cursosms.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    Optional<Aluno> findByUsuarioId(UUID usuarioId);

    @Query("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.id = :cursoId")
    Page<Aluno> findAlunosByCursoId(@Param("cursoId") Long cursoId, Pageable pageable);

}
