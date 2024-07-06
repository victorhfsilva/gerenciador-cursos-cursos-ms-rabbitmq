package com.example.cursosms.service.impl;

import com.example.cursosms.config.RabbitMQConfig;
import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.requests.ProfessorRequest;
import com.example.cursosms.service.AlunoService;
import com.example.cursosms.service.MensagensService;
import com.example.cursosms.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MensagensServiceImpl implements MensagensService {

    private AlunoService alunoService;
    private ProfessorService professorService;

    private static final Logger logger = LoggerFactory.getLogger(MensagensServiceImpl.class);

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_REGISTRAR_ALUNO)
    public void registrarAluno(AlunoRequest alunoRequest) {
        alunoService.save(alunoRequest);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_REGISTRAR_PROFESSOR)
    public void registrarProfessor(ProfessorRequest professorRequest) {
        professorService.save(professorRequest);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETAR_ALUNO)
    public void deletarAluno(UUID usuarioId) {
        alunoService.delete(usuarioId);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETAR_PROFESSOR)
    public void deletarProfessor(UUID usuarioId) {
        professorService.delete(usuarioId);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_LOGS_QUEUE)
    public void logErro() {
        logger.error("Erro ao processar uma mensagem.");
    }


}
