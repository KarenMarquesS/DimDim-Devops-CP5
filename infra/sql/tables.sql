
-- Script DDL para DimDim Bank
-- Banco de dados: Azure SQL Server

-- Tabela Cliente (Master)
CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nome NVARCHAR(100) NOT NULL,
    cpf NVARCHAR(14) UNIQUE,
    email NVARCHAR(100) UNIQUE,
    data_criacao DATETIME2 DEFAULT GETDATE(),
    data_atualizacao DATETIME2 DEFAULT GETDATE()
);

-- Tabela Conta (Detail)
CREATE TABLE Conta (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    numero NVARCHAR(20) NOT NULL UNIQUE,
    cliente_id BIGINT NOT NULL,
    saldo DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    tipo NVARCHAR(20) NOT NULL DEFAULT 'CORRENTE',
    data_criacao DATETIME2 DEFAULT GETDATE(),
    data_atualizacao DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id) ON DELETE CASCADE
);

-- Tabela Transacao (Detail)
CREATE TABLE Transacao (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    conta_id BIGINT NOT NULL,
    tipo NVARCHAR(20) NOT NULL,
    valor DECIMAL(18,2) NOT NULL,
    data_hora DATETIME2 NOT NULL DEFAULT GETDATE(),
    request_id NVARCHAR(50) UNIQUE,
    FOREIGN KEY (conta_id) REFERENCES Conta(id) ON DELETE CASCADE
);

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
