package com.example.cursosms.repository;

import com.example.cursosms.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
    @Query("SELECT c FROM Curso c JOIN c.alunos a WHERE a.usuarioId = :alunoId")
    Page<Curso> findCursosByAlunoId(@Param("alunoId") UUID alunoId, Pageable pageable);

    @Query("SELECT c FROM Curso c WHERE c.professor.usuarioId = :professorId")
    Page<Curso> findCursosByProfessorId(@Param("professorId") UUID professorId, Pageable pageable);
}
