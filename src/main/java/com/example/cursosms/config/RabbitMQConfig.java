package com.example.cursosms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_USUARIOS = "exchange.usuarios";

    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";

    public static final String QUEUE_REGISTRAR_ALUNO = "queue.registrar.aluno";

    public static final String QUEUE_REGISTRAR_PROFESSOR = "queue.registrar.professor";

    public static final String QUEUE_DELETAR_ALUNO = "queue.deletar.aluno";

    public static final String QUEUE_DELETAR_PROFESSOR = "queue.deletar.professor";

    public static final String DEAD_LETTER_LOGS_QUEUE = "dead.letter.logs.queue";

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connection) {
        return new RabbitAdmin(connection);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchangeUsuarios() {
        return new DirectExchange(EXCHANGE_USUARIOS);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue queueRegistrarAluno() {
        return QueueBuilder.nonDurable(QUEUE_REGISTRAR_ALUNO)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "log.registrar.aluno")
                .build();
    }

    @Bean
    public Queue queueRegistrarProfessor() {
        return QueueBuilder.nonDurable(QUEUE_REGISTRAR_PROFESSOR)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "log.registrar.professor")
                .build();
    }

    @Bean
    public Queue queueDeletarAluno() {
        return QueueBuilder.nonDurable(QUEUE_DELETAR_ALUNO)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "log.deletar.aluno")
                .build();
    }

    @Bean
    public Queue queueDeletarProfessor() {
        return QueueBuilder.nonDurable(QUEUE_DELETAR_PROFESSOR)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "log.deletar.professor")
                .build();
    }

    @Bean
    public Queue deadLetterLogsQueue() {
        return QueueBuilder.nonDurable(DEAD_LETTER_LOGS_QUEUE).build();
    }

    @Bean
    public Binding bindingRegistrarAluno(DirectExchange exchangeUsuarios, Queue queueRegistrarAluno) {
            return BindingBuilder.bind(queueRegistrarAluno).to(exchangeUsuarios).with("registrar.aluno");
    }

    @Bean
    public Binding bindingRegistrarProfessor(DirectExchange exchangeUsuarios, Queue queueRegistrarProfessor) {
        return BindingBuilder.bind(queueRegistrarProfessor).to(exchangeUsuarios).with("registrar.professor");
    }

    @Bean
    public Binding bindingDeletarAluno(DirectExchange exchangeUsuarios, Queue queueDeletarAluno) {
        return BindingBuilder.bind(queueDeletarAluno).to(exchangeUsuarios).with("deletar.aluno");
    }

    @Bean
    public Binding bindingDeletarProfessor(DirectExchange exchangeUsuarios, Queue queueDeletarProfessor) {
        return BindingBuilder.bind(queueDeletarProfessor).to(exchangeUsuarios).with("deletar.professor");
    }

    @Bean
    public Binding bindingDeadLetterLogsQueue(TopicExchange deadLetterExchange, Queue deadLetterLogsQueue) {
        return BindingBuilder.bind(deadLetterLogsQueue).to(deadLetterExchange).with("log.#");
    }
}
