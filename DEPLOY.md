# Guia de Deploy - DimDim Bank

## Pré-requisitos

- Azure CLI instalado
- Java 17
- Maven 3.6+
- Git

## Opção 1: Deploy via Azure CLI

### 1. Login no Azure
```bash
az login
```

### 2. Criar recursos no Azure
```bash
chmod +x infra/azure/script_app.sh
./infra/azure/script_app.sh
```

### 3. Executar script DDL
- Acesse o Azure Portal
- Vá para SQL Databases > dimdim-bank-db
- Execute o script em `infra/sql/tables.sql`

### 4. Deploy da aplicação
```bash
chmod +x infra/azure/deploy-app.sh
./infra/azure/script_app.sh
```

## Opção 2: Deploy via GitHub Actions

### 1. Configurar secrets no GitHub
No repositório GitHub, vá em Settings > Secrets and variables > Actions e adicione:
- `AZURE_WEBAPP_PUBLISH_PROFILE`: Profile de publicação do Azure Web App

### 2. Push para main
```bash
git add .
git commit -m "Deploy to Azure"
git push origin main
```

## Opção 3: Deploy via IDE

### Eclipse/IntelliJ com Plugin Azure
1. Instale o Azure Toolkit for Eclipse/IntelliJ
2. Faça login com sua conta Azure
3. Clique com botão direito no projeto
4. Selecione "Deploy to Azure Web App"
5. Configure as variáveis de ambiente

## Configurações de Ambiente

### Variáveis de Ambiente Necessárias
- `AZURE_SQL_SERVER`: Nome do servidor SQL
- `AZURE_SQL_DATABASE`: Nome do banco de dados
- `AZURE_SQL_USERNAME`: Usuário do banco
- `AZURE_SQL_PASSWORD`: Senha do banco
- `AZURE_APPLICATION_INSIGHTS_KEY`: Chave do Application Insights
- `SPRING_PROFILES_ACTIVE`: azure

## Monitoramento

### Application Insights
- Acesse o Azure Portal
- Vá para Application Insights > dimdim-bank-insights
- Monitore métricas, logs e performance

### Logs da Aplicação
```bash
az webapp log tail --name dimdim-bank-app --resource-group dimdim-bank-rg
```

## URLs da Aplicação

- **Web Interface**: https://dimdim-bank-app.azurewebsites.net
- **API Documentation**: https://dimdim-bank-app.azurewebsites.net/swagger-ui.html
- **Health Check**: https://dimdim-bank-app.azurewebsites.net/actuator/health

## Endpoints da API

### Clientes
- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Criar cliente
- `GET /api/clientes/{id}` - Buscar cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Excluir cliente

### Contas
- `GET /api/contas` - Listar contas
- `POST /api/contas` - Criar conta
- `GET /api/contas/{id}` - Buscar conta
- `PUT /api/contas/{id}` - Atualizar conta
- `DELETE /api/contas/{id}` - Excluir conta
- `POST /api/contas/{id}/deposito` - Depósito
- `POST /api/contas/{id}/saque` - Saque

### Transações
- `GET /api/transacoes` - Listar transações
- `GET /api/transacoes/{id}` - Buscar transação
- `GET /api/transacoes/conta/{contaId}` - Transações da conta
- `DELETE /api/transacoes/{id}` - Excluir transação

## Troubleshooting

### Problemas Comuns
1. **Erro de conexão com banco**: Verifique as variáveis de ambiente
2. **Aplicação não inicia**: Verifique os logs no Azure Portal
3. **Erro 500**: Verifique se o banco foi criado corretamente

### Logs
```bash
# Logs em tempo real
az webapp log tail --name dimdim-bank-app --resource-group dimdim-bank-rg

# Download de logs
az webapp log download --name dimdim-bank-app --resource-group dimdim-bank-rg
```
