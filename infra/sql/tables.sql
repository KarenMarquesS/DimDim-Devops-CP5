
-- Script DDL para DimDim Bank
-- Banco de dados: Azure SQL Server

-- Tabela de Clientes
CREATE TABLE [dbo].[tb_cliente] (
    id BIGINT NOT NULL IDENTITY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14),
    email VARCHAR(255),
    PRIMARY KEY (id)
);

-- Tabela de Contas
CREATE TABLE [dbo].[tb_conta] (
    id BIGINT NOT NULL IDENTITY,
    numero VARCHAR(50) NOT NULL,
    cliente_id BIGINT NOT NULL,
    saldo DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id),
    CONSTRAINT UK_CONTA_NUMERO UNIQUE (numero)
);

-- Tabela de Transações
CREATE TABLE [dbo].[tb_transacao] (
    id BIGINT NOT NULL IDENTITY,
    conta_id BIGINT NOT NULL,
    tipo VARCHAR(50),
    valor DECIMAL(19,2) NOT NULL,
    data_hora DATETIME2 NOT NULL DEFAULT GETDATE(),
    request_id VARCHAR(255),
    PRIMARY KEY (id)
);

-- Foreign Keys
ALTER TABLE [dbo].[tb_conta] 
    ADD CONSTRAINT FK_CONTA_CLIENTE 
    FOREIGN KEY (cliente_id) REFERENCES [dbo].[tb_cliente] (id);

ALTER TABLE [dbo].[tb_transacao] 
    ADD CONSTRAINT FK_TRANSACAO_CONTA 
    FOREIGN KEY (conta_id) REFERENCES [dbo].[tb_conta] (id);

-- Índices para performance
CREATE INDEX IX_Cliente_Email ON Cliente(email);
CREATE INDEX IX_Cliente_CPF ON Cliente(cpf);
CREATE INDEX IX_Conta_Cliente ON Conta(cliente_id);
CREATE INDEX IX_Conta_Numero ON Conta(numero);
CREATE INDEX IX_Transacao_Conta ON Transacao(conta_id);
CREATE INDEX IX_Transacao_DataHora ON Transacao(data_hora);

-- Dados de exemplo
INSERT INTO Cliente (nome, cpf, email) VALUES 
('João Silva', '123.456.789-00', 'joao@email.com'),
('Maria Santos', '987.654.321-00', 'maria@email.com');

INSERT INTO Conta (numero, cliente_id, saldo, tipo) VALUES 
('12345-6', 1, 1000.00, 'CORRENTE'),
('67890-1', 2, 2500.00, 'POUPANCA');
