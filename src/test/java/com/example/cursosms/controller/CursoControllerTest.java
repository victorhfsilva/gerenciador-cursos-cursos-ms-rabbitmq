package com.example.cursosms.controller;

import com.example.cursosms.fixture.CursoRequestFixture;
import com.example.cursosms.fixture.CursoResourceFixture;
import com.example.cursosms.model.requests.CursoRequest;
import com.example.cursosms.model.resources.CursoResource;
import com.example.cursosms.service.impl.CursoServiceImpl;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CursoServiceImpl cursoServiceImpl;

    @Test
    void registrarCursoTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();

        when(cursoServiceImpl.save(any(CursoRequest.class))).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }

    @Test
    void buscarCursoPorIdTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoServiceImpl.findById(any())).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos/{id}", cursoResource.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }

    @Test
    void buscarCursosPorProfessorIdTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<CursoResource> cursoResources = List.of(CursoResourceFixture.buildValido());
        Page<CursoResource> paginaCursoResources = new PageImpl<>(cursoResources, pageable, cursoResources.size());

        when(cursoServiceImpl.findByProfessorId(any(UUID.class), any(Pageable.class))).thenReturn(paginaCursoResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos/professor/b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].nome").value(cursoResources.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].descricao").value(cursoResources.get(0).getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].cargaHoraria").value(cursoResources.get(0).getCargaHoraria()));
    }


    @Test
    void buscarCursosPorAlunoIdTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<CursoResource> cursoResources = List.of(CursoResourceFixture.buildValido());
        Page<CursoResource> paginaCursoResources = new PageImpl<>(cursoResources, pageable, cursoResources.size());

        when(cursoServiceImpl.findByAlunoId(any(UUID.class), any(Pageable.class))).thenReturn(paginaCursoResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos/aluno/b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].nome").value(cursoResources.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].descricao").value(cursoResources.get(0).getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].cargaHoraria").value(cursoResources.get(0).getCargaHoraria()));
    }

    @Test
    void buscarTodosCursosTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<CursoResource> cursoResources = List.of(CursoResourceFixture.buildValido());
        Page<CursoResource> paginaCursoResources = new PageImpl<>(cursoResources, pageable, cursoResources.size());

        when(cursoServiceImpl.findAll(any(Pageable.class))).thenReturn(paginaCursoResources);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].nome").value(cursoResources.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].descricao").value(cursoResources.get(0).getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cursoResourceList.[0].cargaHoraria").value(cursoResources.get(0).getCargaHoraria()));
    }

    @Test
    void atualizarCursoTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();
        CursoRequest cursoRequest = CursoRequestFixture.buildValido();

        when(cursoServiceImpl.update(any(Long.class), any(CursoRequest.class))).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.put("/cursos/{id}", cursoResource.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }

    @Test
    void adicionarAlunoTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoServiceImpl.addAluno(any(Long.class), any(UUID.class))).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cursos/{id}/add/{alunoId}", cursoResource.getId(), UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }

    @Test
    void removerAlunoTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoServiceImpl.removeAluno(any(Long.class), any(UUID.class))).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cursos/{id}/remove/{alunoId}", cursoResource.getId(), UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }

    @Test
    void deletarCursoTest() throws Exception {
        CursoResource cursoResource = CursoResourceFixture.buildValido();

        when(cursoServiceImpl.delete(any(Long.class))).thenReturn(cursoResource);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cursos/{id}", cursoResource.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cursoResource.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cursoResource.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(cursoResource.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(cursoResource.getCargaHoraria()));
    }
}
