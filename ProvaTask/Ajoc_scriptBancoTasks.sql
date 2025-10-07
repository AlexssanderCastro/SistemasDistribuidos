DROP TABLE IF EXISTS ajoc_tarefa;
DROP TABLE IF EXISTS ajoc_projeto;
CREATE TABLE ajoc_projeto (
    ajoc_id_projeto UUID PRIMARY KEY,
    ajoc_nome VARCHAR(100),
    ajoc_descricao TEXT,
    ajoc_data_criacao VARCHAR(20),
    ajoc_data_fim VARCHAR(20)
);
CREATE TABLE ajoc_tarefa (
    ajoc_id_tarefa UUID PRIMARY KEY,
    ajoc_nome VARCHAR(100),
    ajoc_descricao TEXT,
    ajoc_prioridade VARCHAR(20),
    ajoc_data_inicio VARCHAR(20),
    ajoc_data_fim VARCHAR(20),
    ajoc_id_projeto UUID REFERENCES ajoc_projeto(ajoc_id_projeto)
);


