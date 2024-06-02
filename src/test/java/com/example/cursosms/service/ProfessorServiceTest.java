package com.example.cursosms.service;

import com.example.cursosms.fixture.ProfessorFixture;
import com.example.cursosms.fixture.ProfessorRequestFixture;
import com.example.cursosms.fixture.ProfessorResourceFixture;
import com.example.cursosms.mapper.ProfessorProfessorRequestMapper;
import com.example.cursosms.mapper.ProfessorProfessorResourceMapper;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.impl.ProfessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private ProfessorProfessorResourceMapper professorProfessorResourceMapper;

    @Mock
    private ProfessorProfessorRequestMapper professorProfessorRequestMapper;

    @Test
    void saveTest(){
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorProfessorRequestMapper.professorRequestToProfessor(professorRequest)).thenReturn(professor);
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);
        when(professorProfessorResourceMapper.professorToProfessorResource(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorService.save(professorRequest);

        assertEquals(professorResourceSalvo.getUsuarioId(), professorRequest.usuarioId());
    }

    @Test
    void findByUsuarioIdTest(){
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findByUsuarioId(professor.getUsuarioId())).thenReturn(Optional.of(professor));
        when(professorProfessorResourceMapper.professorToProfessorResource(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorService.findByUsuarioId(professor.getUsuarioId());

        assertEquals(professorResourceSalvo.getUsuarioId(), professor.getUsuarioId());
    }

    @Test
    void findAllTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(professor)));
        when(professorProfessorResourceMapper.professorToProfessorResource(professor)).thenReturn(professorResource);

        Page<ProfessorResource> professorResources = professorService.findAll(pageable);

        assertEquals(professorResources.getContent().get(0).getUsuarioId(), professor.getUsuarioId());
    }

    @Test
    void deleteTest(){
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findByUsuarioId(professor.getUsuarioId())).thenReturn(Optional.of(professor));
        when(professorProfessorResourceMapper.professorToProfessorResource(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorService.delete(professor.getUsuarioId());

        assertEquals(professorResourceSalvo.getUsuarioId(), professor.getUsuarioId());
    }
}
