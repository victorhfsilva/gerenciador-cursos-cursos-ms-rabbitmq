package com.example.cursosms.service;

import com.example.cursosms.fixture.ProfessorFixture;
import com.example.cursosms.fixture.ProfessorRequestFixture;
import com.example.cursosms.fixture.ProfessorResourceFixture;
import com.example.cursosms.mapper.ProfessorMapper;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.model.resources.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import com.example.cursosms.service.impl.ProfessorServiceImpl;
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
class ProfessorServiceImplTest {

    @InjectMocks
    private ProfessorServiceImpl professorServiceImpl;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private ProfessorMapper professorMapper;

    @Test
    void saveTest(){
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorMapper.map(professorRequest)).thenReturn(professor);
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);
        when(professorMapper.map(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorServiceImpl.save(professorRequest);

        assertEquals(professorResourceSalvo.getUsuarioId(), professorRequest.usuarioId());
    }

    @Test
    void findByUsuarioIdTest(){
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findByUsuarioId(professor.getUsuarioId())).thenReturn(Optional.of(professor));
        when(professorMapper.map(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorServiceImpl.findByUsuarioId(professor.getUsuarioId());

        assertEquals(professorResourceSalvo.getUsuarioId(), professor.getUsuarioId());
    }

    @Test
    void findAllTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(professor)));
        when(professorMapper.map(professor)).thenReturn(professorResource);

        Page<ProfessorResource> professorResources = professorServiceImpl.findAll(pageable);

        assertEquals(professorResources.getContent().get(0).getUsuarioId(), professor.getUsuarioId());
    }

    @Test
    void deleteTest(){
        Professor professor = ProfessorFixture.buildValido();
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorRepository.findByUsuarioId(professor.getUsuarioId())).thenReturn(Optional.of(professor));
        when(professorMapper.map(professor)).thenReturn(professorResource);

        ProfessorResource professorResourceSalvo = professorServiceImpl.delete(professor.getUsuarioId());

        assertEquals(professorResourceSalvo.getUsuarioId(), professor.getUsuarioId());
    }
}
