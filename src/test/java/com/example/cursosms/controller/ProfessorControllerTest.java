package com.example.cursosms.controller;

import com.example.cursosms.fixture.ProfessorResourceFixture;
import com.example.cursosms.fixture.ProfessorRequestFixture;
import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.model.resources.ProfessorResource;
import com.example.cursosms.service.impl.ProfessorServiceImpl;
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
class ProfessorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfessorServiceImpl professorServiceImpl;

    @Test
    void registrarProfessorTest() throws Exception {
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();

        when(professorServiceImpl.save(any(ProfessorRequest.class))).thenReturn(professorResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(professorRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(professorResource.getUsuarioId().toString()));
    }

    @Test
    void buscarProfessorPorIdTest() throws Exception {
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorServiceImpl.findByUsuarioId(any())).thenReturn(professorResource);

        mockMvc.perform(MockMvcRequestBuilders.get("/professores/{id}", professorResource.getUsuarioId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(professorResource.getUsuarioId().toString()));
    }

    @Test
    void buscarTodosProfessorsTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<ProfessorResource> professorResources = List.of(ProfessorResourceFixture.buildValido());
        Page<ProfessorResource> paginaProfessorResources = new PageImpl<>(professorResources, pageable, professorResources.size());

        when(professorServiceImpl.findAll(any(Pageable.class))).thenReturn(paginaProfessorResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/professores"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.professorResourceList.[0].usuarioId").value(professorResources.get(0).getUsuarioId().toString()));
    }

    @Test
    void deletarTest() throws Exception {
        ProfessorResource professorResource = ProfessorResourceFixture.buildValido();

        when(professorServiceImpl.delete(any())).thenReturn(professorResource);

        mockMvc.perform(MockMvcRequestBuilders.delete("/professores/{id}", professorResource.getUsuarioId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuarioId").value(professorResource.getUsuarioId().toString()));
    }
}
