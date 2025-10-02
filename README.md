# DimDim Bank - Sistema Bancário

Sistema bancário completo desenvolvido em Java Spring Boot com deploy na Azure.

## 🚀 Funcionalidades

### Gestão de Clientes
- ✅ Cadastrar cliente
- ✅ Listar clientes
- ✅ Editar cliente
- ✅ Excluir cliente
- ✅ Buscar por email

### Gestão de Contas
- ✅ Cadastrar conta
- ✅ Listar contas
- ✅ Editar conta
- ✅ Excluir conta
- ✅ Realizar depósitos
- ✅ Realizar saques
- ✅ Consultar saldo

### Gestão de Transações
- ✅ Listar transações
- ✅ Histórico por conta
- ✅ Histórico por cliente
- ✅ Excluir transação

## 🛠️ Tecnologias

- **Backend**: Java 17, Spring Boot 3.4.4
- **Banco de Dados**: Azure SQL Server
- **Monitoramento**: Azure Application Insights
- **Deploy**: Azure App Service
- **Frontend**: Thymeleaf + Bootstrap
- **API**: REST com OpenAPI/Swagger

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- Azure CLI
- Git

## 🚀 Deploy

### Opção 1: Azure CLI
```bash
# Login no Azure
az login

# Criar recursos
chmod +x infra/azure/create-resources.sh
./infra/azure/create-resources.sh

# Deploy da aplicação
chmod +x infra/azure/deploy-app.sh
./infra/azure/deploy-app.sh
```

### Opção 2: GitHub Actions
1. Configure o secret `AZURE_WEBAPP_PUBLISH_PROFILE` no GitHub
2. Faça push para a branch `main`

### Opção 3: IDE (Eclipse/IntelliJ)
1. Instale o Azure Toolkit
2. Clique com botão direito no projeto
3. Selecione "Deploy to Azure Web App"

## 📊 Estrutura do Banco

### Tabelas
- **Cliente** (Master): Dados pessoais dos clientes
- **Conta** (Detail): Contas bancárias dos clientes
- **Transacao** (Detail): Histórico de transações

### Relacionamentos
- Cliente 1:N Conta
- Conta 1:N Transacao

## 🔗 URLs

- **Web Interface**: https://dimdim-bank-app.azurewebsites.net
- **API Documentation**: https://dimdim-bank-app.azurewebsites.net/swagger-ui.html
- **Health Check**: https://dimdim-bank-app.azurewebsites.net/actuator/health

## 📚 API Endpoints

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

## 📁 Estrutura do Projeto

```
dimdim-bank-final/
├── src/main/java/com/DimDim/
│   ├── Application.java
│   ├── config/
│   │   └── ApplicationInsightsConfig.java
│   ├── model/
│   │   ├── Cliente.java
│   │   ├── Conta.java
│   │   └── Transacao.java
│   ├── repository/
│   │   ├── ClienteRepository.java
│   │   ├── ContaRepository.java
│   │   └── TransacaoRepository.java
│   ├── service/
│   │   └── ContaService.java
│   └── web/
│       ├── ClienteWebController.java
│       ├── ClienteRestController.java
│       ├── ContaWebController.java
│       ├── ContaRestController.java
│       ├── TransacaoWebController.java
│       └── TransacaoRestController.java
├── src/main/resources/
│   ├── application-azure.properties
│   └── templates/
│       ├── clientes/
│       ├── contas/
│       └── transacoes/
├── infra/
│   ├── azure/
│   │   ├── create-resources.sh
│   │   └── deploy-app.sh
│   └── sql/
│       └── tables.sql
├── .github/workflows/
│   └── azure-deploy.yml
├── api-examples.json
├── DEPLOY.md
└── pom.xml
```

## 🔧 Configuração

### Variáveis de Ambiente
- `AZURE_SQL_SERVER`: Nome do servidor SQL
- `AZURE_SQL_DATABASE`: Nome do banco de dados
- `AZURE_SQL_USERNAME`: Usuário do banco
- `AZURE_SQL_PASSWORD`: Senha do banco
- `AZURE_APPLICATION_INSIGHTS_KEY`: Chave do Application Insights
- `SPRING_PROFILES_ACTIVE`: azure

## 📈 Monitoramento

### Application Insights
- Métricas de performance
- Logs de aplicação
- Rastreamento de dependências
- Alertas personalizados

### Logs
```bash
# Logs em tempo real
az webapp log tail --name dimdim-bank-app --resource-group dimdim-bank-rg
```

## 🧪 Testes

### Executar Testes
```bash
mvn test
```

### Testes de Integração
```bash
mvn verify
```

## 📝 Documentação da API

A documentação completa da API está disponível em:
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 📞 Suporte

Para suporte, entre em contato através de:
- Email: suporte@dimdimbank.com
- Issues: GitHub Issues

---

**DimDim Bank** - Sistema bancário moderno e seguro! 🏦