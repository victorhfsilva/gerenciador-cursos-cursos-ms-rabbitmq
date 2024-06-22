package com.example.cursosms.controller;

import com.example.cursosms.fixture.AlunoRequestFixture;
import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class AlunoControllerIntegrationTest {

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
    void registrarAlunoTest() {
        AlunoRequest alunoRequest = AlunoRequestFixture.buildValido();
        HttpEntity<AlunoRequest> requisicao = new HttpEntity<>(alunoRequest);

        ResponseEntity<AlunoResource> resposta = restTemplate
                .postForEntity("http://localhost:" + port + "/alunos",
                        requisicao,
                        AlunoResource.class);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(alunoRequest.usuarioId(), resposta.getBody().getUsuarioId());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void buscarAlunoPorIdTest() {
        ResponseEntity<AlunoResource> resposta = restTemplate
                .getForEntity("http://localhost:" + port + "/alunos/"+"b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21",
                        AlunoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21", resposta.getBody().getUsuarioId().toString());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/db/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/restart_ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void deletarAlunoTest() {
        ResponseEntity<AlunoResource> resposta = restTemplate
                .exchange("http://localhost:" + port + "/alunos/"+"b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21",
                        HttpMethod.DELETE,
                        null,
                        AlunoResource.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21", resposta.getBody().getUsuarioId().toString());
    }


}