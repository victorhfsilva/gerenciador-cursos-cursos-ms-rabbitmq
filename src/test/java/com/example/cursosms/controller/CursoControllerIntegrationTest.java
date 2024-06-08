package com.example.cursosms.controller;

import com.example.cursosms.fixture.CursoRequestFixture;
import com.example.cursosms.fixture.ProfessorRequestFixture;
import com.example.cursosms.model.dto.CursoRequest;
import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.CursoResource;
import com.example.cursosms.model.resource.ProfessorResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CursoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void registrarCursoTest() {
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();
        HttpEntity<ProfessorRequest> requisicaoProfessor = new HttpEntity<>(professorRequest);

        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        HttpEntity<CursoRequest> requisicaoCurso = new HttpEntity<>(cursoRequest);

        ResponseEntity<ProfessorResource> respostaProfessor = restTemplate
                .postForEntity("http://localhost:" + port + "/professores",
                        requisicaoProfessor,
                        ProfessorResource.class);

        ResponseEntity<CursoResource> respostaCurso = restTemplate
                .postForEntity("http://localhost:" + port + "/cursos",
                        requisicaoCurso,
                        CursoResource.class);

        assertEquals(HttpStatus.CREATED, respostaCurso.getStatusCode());
        assertEquals(cursoRequest.nome(), respostaCurso.getBody().getNome());
        assertEquals(cursoRequest.descricao(), respostaCurso.getBody().getDescricao());
        assertEquals(cursoRequest.cargaHoraria(), respostaCurso.getBody().getCargaHoraria());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void buscarCursoPorIdTest() {
        ResponseEntity<CursoResource> resposta = restTemplate
                .getForEntity("http://localhost:" + port + "/cursos/" + 1,
                        CursoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Curso de Java", resposta.getBody().getNome());
        assertEquals("Curso avancado de Java", resposta.getBody().getDescricao());
        assertEquals(40, resposta.getBody().getCargaHoraria());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void atualizarCursoTest(){
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();
        HttpEntity<ProfessorRequest> requisicaoProfessor = new HttpEntity<>(professorRequest);

        CursoRequest cursoRequest = CursoRequestFixture.buildValido();
        HttpEntity<CursoRequest> requisicaoCurso = new HttpEntity<>(cursoRequest);

        ResponseEntity<ProfessorResource> respostaProfessor = restTemplate
                .postForEntity("http://localhost:" + port + "/professores",
                        requisicaoProfessor,
                        ProfessorResource.class);

        ResponseEntity<CursoResource> respostaCurso = restTemplate
                .exchange("http://localhost:" + port + "/cursos/1",
                        HttpMethod.PUT,
                        requisicaoCurso,
                        CursoResource.class);

        assertEquals(HttpStatus.OK, respostaCurso.getStatusCode());
        assertEquals(cursoRequest.nome(), respostaCurso.getBody().getNome());
        assertEquals(cursoRequest.descricao(), respostaCurso.getBody().getDescricao());
        assertEquals(cursoRequest.cargaHoraria(), respostaCurso.getBody().getCargaHoraria());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void adicionarAlunoTest(){
        ResponseEntity<CursoResource> resposta = restTemplate
                .exchange("http://localhost:" + port + "/cursos/1/add/b3eebc99-9c0b-4ef8-bb6d-6bb9bd380b24",
                        HttpMethod.PATCH,
                        null,
                        CursoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Curso de Java", resposta.getBody().getNome());
        assertEquals("Curso avancado de Java", resposta.getBody().getDescricao());
        assertEquals(40, resposta.getBody().getCargaHoraria());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void removerAlunoTest(){
        ResponseEntity<CursoResource> resposta = restTemplate
                .exchange("http://localhost:" + port + "/cursos/1/remove/b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21",
                        HttpMethod.PATCH,
                        null,
                        CursoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Curso de Java", resposta.getBody().getNome());
        assertEquals("Curso avancado de Java", resposta.getBody().getDescricao());
        assertEquals(40, resposta.getBody().getCargaHoraria());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void deletarCursoTest() {
        ResponseEntity<CursoResource> resposta = restTemplate
                .exchange("http://localhost:" + port + "/cursos/" + 1,
                        HttpMethod.DELETE,
                        null,
                        CursoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Curso de Java", resposta.getBody().getNome());
        assertEquals("Curso avancado de Java", resposta.getBody().getDescricao());
        assertEquals(40, resposta.getBody().getCargaHoraria());
    }

}
