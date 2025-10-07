$RG = "rg-dimdimbank"
$LOCATION = "brazilsouth"
$AZURE_SQL_SERVER="sqlserver-rm554556"
$AZURE_SQL_DATABASE="dimdimdb"
$AZURE_SQL_USERNAME="admsql"
$AZURE_SQL_PASSWORD="Fiap@2tdsvms"


az group create --name $RG --location $LOCATION
az sql server create -l $LOCATION -g $RG -n $AZURE_SQL_SERVER -u $AZURE_SQL_USERNAME -p $AZURE_SQL_PASSWORD --enable-public-network true
az sql db create -g $RG -s $AZURE_SQL_SERVER -n $AZURE_SQL_DATABASE --service-objective Basic --backup-storage-redundancy Local --zone-redundant false
az sql server firewall-rule create -g $RG -s $AZURE_SQL_SERVER -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255

# Cria os objetos de Banco
# Certifique-se de ter o sqlcmd instalado em seu ambiente
Invoke-Sqlcmd -ServerInstance "$AZURE_SQL_SERVER.database.windows.net" `
              -Database "$AZURE_SQL_DATABASE" `
              -Username "$AZURE_SQL_USERNAME" `
              -Password "$AZURE_SQL_PASSWORD" `
              -Query @"
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
"@
