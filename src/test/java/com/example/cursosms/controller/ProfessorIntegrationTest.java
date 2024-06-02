package com.example.cursosms.controller;

import com.example.cursosms.fixture.ProfessorFixture;
import com.example.cursosms.fixture.ProfessorRequestFixture;
import com.example.cursosms.model.Professor;
import com.example.cursosms.model.dto.ProfessorRequest;
import com.example.cursosms.model.resource.ProfessorResource;
import com.example.cursosms.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProfessorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void registrarProfessorTest() {
        ProfessorRequest professorRequest = ProfessorRequestFixture.buildValido();
        HttpEntity<ProfessorRequest> requisicao = new HttpEntity<>(professorRequest);

        ResponseEntity<ProfessorResource> resposta = restTemplate
                .postForEntity("http://localhost:" + port + "/professores",
                        requisicao,
                        ProfessorResource.class);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(professorRequest.usuarioId(), resposta.getBody().getUsuarioId());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void buscarProfessorPorIdTest() {
        ResponseEntity<ProfessorResource> resposta = restTemplate
                .getForEntity("http://localhost:" + port + "/professores/"+"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                        ProfessorResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", resposta.getBody().getUsuarioId().toString());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void deletarProfessorTest() {
        ResponseEntity<ProfessorResource> resposta = restTemplate
                .exchange("http://localhost:" + port + "/professores/"+"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                        HttpMethod.DELETE,
                        null,
                        ProfessorResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", resposta.getBody().getUsuarioId().toString());
    }


}
