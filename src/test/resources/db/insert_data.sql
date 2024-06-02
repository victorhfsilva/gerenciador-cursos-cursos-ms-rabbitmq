INSERT INTO professores (usuario_id) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12'),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a13');

INSERT INTO cursos (nome, descricao, carga_horaria, professor_id) VALUES
('Curso de Java', 'Curso avançado de Java', 40, 1),
('Curso de Spring', 'Curso completo de Spring Framework', 30, 2),
('Curso de Docker', 'Curso introdutório de Docker', 20, 3);

INSERT INTO alunos (usuario_id) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b21'),
('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b22'),
('b2eebc99-9c0b-4ef8-bb6d-6bb9bd380b23'),
('b3eebc99-9c0b-4ef8-bb6d-6bb9bd380b24');

INSERT INTO cursos_alunos (curso_id, aluno_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 3),
(3, 4);