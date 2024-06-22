package com.example.cursosms.controller;

import com.example.cursosms.fixture.AlunoRequestFixture;
import com.example.cursosms.fixture.AlunoResourceFixture;
import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import com.example.cursosms.service.impl.AlunoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlunoServiceImpl alunoServiceImpl;

    @Test
    void registrarAlunoTest() throws Exception {
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();
        AlunoRequest alunoRequest = AlunoRequestFixture.buildValido();

        when(alunoServiceImpl.save(any(AlunoRequest.class))).thenReturn(alunoResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(alunoResource.getUsuarioId().toString()));
    }

    @Test
    void buscarAlunoPorIdTest() throws Exception {
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoServiceImpl.findByUsuarioId(any())).thenReturn(alunoResource);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos/{id}", alunoResource.getUsuarioId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(alunoResource.getUsuarioId().toString()));
    }

    @Test
    void buscarAlunoPorCursoIdTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<AlunoResource> alunoResources = List.of(AlunoResourceFixture.buildValido());
        Page<AlunoResource> paginaAlunoResources = new PageImpl<>(alunoResources, pageable, alunoResources.size());

        when(alunoServiceImpl.findAlunosByCursoId(any(Long.class), any(Pageable.class))).thenReturn(paginaAlunoResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos/curso/{cursoId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.alunoResourceList.[0].usuarioId").value(alunoResources.get(0).getUsuarioId().toString()));
    }

    @Test
    void buscarTodosAlunosTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<AlunoResource> alunoResources = List.of(AlunoResourceFixture.buildValido());
        Page<AlunoResource> paginaAlunoResources = new PageImpl<>(alunoResources, pageable, alunoResources.size());

        when(alunoServiceImpl.findAll(any(Pageable.class))).thenReturn(paginaAlunoResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.alunoResourceList.[0].usuarioId").value(alunoResources.get(0).getUsuarioId().toString()));
    }

    @Test
    void deletarAlunoTest() throws Exception {
        AlunoResource alunoResource = AlunoResourceFixture.buildValido();

        when(alunoServiceImpl.delete(any())).thenReturn(alunoResource);

        mockMvc.perform(MockMvcRequestBuilders.delete("/alunos/{id}", alunoResource.getUsuarioId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(alunoResource.getUsuarioId().toString()));
    }
}
