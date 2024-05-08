CREATE TABLE professores (
    id BIGSERIAL PRIMARY KEY,
    usuario_id UUID NOT NULL UNIQUE
);

CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,
    carga_horaria INTEGER NOT NULL,
    professor_id BIGINT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professores(id)
);

CREATE TABLE alunos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id UUID NOT NULL UNIQUE
);

CREATE TABLE cursos_alunos (
    curso_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    PRIMARY KEY (curso_id, aluno_id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id),
    FOREIGN KEY (aluno_id) REFERENCES alunos(id)
);