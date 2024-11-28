-- Script SQL responsável pela criação e inicialização do banco de dados do sistema Banco Malvader.
-- Contém instruções para criação de tabelas, relacionamentos, índices e inserção de dados iniciais.
--
-- Autor: Dérick Rangel
-- Versão: 1.0
-- Desde: 2024-11-27
DROP DATABASE IF EXISTS banco_malvader;

CREATE DATABASE banco_malvader;
USE banco_malvader;

CREATE TABLE usuario
(
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(100)                    NOT NULL,
    cpf             VARCHAR(15)                     NOT NULL UNIQUE,
    data_nascimento DATE,
    telefone        VARCHAR(15),
    tipo_usuario    ENUM ('FUNCIONARIO', 'CLIENTE') NOT NULL,
    senha           VARCHAR(50)                     NOT NULL
);

CREATE TABLE funcionario
(
    id_funcionario     INT AUTO_INCREMENT PRIMARY KEY,
    codigo_funcionario VARCHAR(20) NOT NULL,
    cargo              VARCHAR(50),
    id_usuario         INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

CREATE TABLE cliente
(
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

CREATE TABLE endereco
(
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    cep         VARCHAR(10)  NOT NULL,
    local       VARCHAR(100) NOT NULL,
    numero_casa INT,
    bairro      VARCHAR(50),
    cidade      VARCHAR(50),
    estado      VARCHAR(2),
    id_usuario  INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

CREATE TABLE conta
(
    id_conta     INT AUTO_INCREMENT PRIMARY KEY,
    numero_conta VARCHAR(20)                   NOT NULL,
    agencia      VARCHAR(10)                   NOT NULL,
    saldo        DECIMAL(15, 2)                NOT NULL DEFAULT 0.00,
    tipo_conta   ENUM ('POUPANCA', 'CORRENTE') NOT NULL,
    id_cliente   INT,
    FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente) ON DELETE CASCADE
);

CREATE TABLE conta_corrente
(
    id_conta_corrente INT AUTO_INCREMENT PRIMARY KEY,
    limite            DECIMAL(15, 2) NOT NULL,
    data_vencimento   DATE,
    id_conta          INT,
    FOREIGN KEY (id_conta) REFERENCES conta (id_conta) ON DELETE CASCADE
);

CREATE TABLE conta_poupanca
(
    id_conta_poupanca INT AUTO_INCREMENT PRIMARY KEY,
    taxa_rendimento   DECIMAL(5, 2) NOT NULL,
    id_conta          INT,
    FOREIGN KEY (id_conta) REFERENCES conta (id_conta) ON DELETE CASCADE
);

CREATE TABLE transacao
(
    id_transacao   INT AUTO_INCREMENT PRIMARY KEY,
    tipo_transacao ENUM ('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') NOT NULL,
    valor          DECIMAL(15, 2)                              NOT NULL,
    data_hora      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_conta       INT,
    FOREIGN KEY (id_conta) REFERENCES conta (id_conta) ON DELETE CASCADE
);

CREATE TABLE relatorio
(
    id_relatorio   INT AUTO_INCREMENT PRIMARY KEY,
    tipo_relatorio VARCHAR(50) NOT NULL,
    data_geracao   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    conteudo       TEXT,
    id_funcionario INT,
    FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario) ON DELETE CASCADE
);
