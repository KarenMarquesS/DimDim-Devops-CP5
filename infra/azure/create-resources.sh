#!/bin/bash

# Script para criar recursos no Azure para DimDim Bank
# Execute: az login antes de executar este script

# Configurações
RESOURCE_GROUP="dimdim-bank-rg"
LOCATION="Brazil South"
APP_NAME="dimdim-bank-app"
SQL_SERVER="dimdim-bank-sql"
SQL_DATABASE="dimdim-bank-db"
SQL_USERNAME="dimdimadmin"
SQL_PASSWORD="DimDim@2024!"
APP_INSIGHTS="dimdim-bank-insights"

echo "Criando grupo de recursos..."
az group create --name $RESOURCE_GROUP --location "$LOCATION"

echo "Criando Application Insights..."
az monitor app-insights component create \
    --app $APP_INSIGHTS \
    --location "$LOCATION" \
    --resource-group $RESOURCE_GROUP

echo "Criando SQL Server..."
az sql server create \
    --name $SQL_SERVER \
    --resource-group $RESOURCE_GROUP \
    --location "$LOCATION" \
    --admin-user $SQL_USERNAME \
    --admin-password "$SQL_PASSWORD"

echo "Criando regra de firewall para SQL Server..."
az sql server firewall-rule create \
    --resource-group $RESOURCE_GROUP \
    --server $SQL_SERVER \
    --name "AllowAllWindowsAzureIps" \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 0.0.0.0

echo "Criando banco de dados..."
az sql db create \
    --resource-group $RESOURCE_GROUP \
    --server $SQL_SERVER \
    --name $SQL_DATABASE \
    --service-objective S0

echo "Criando App Service Plan..."
az appservice plan create \
    --name "${APP_NAME}-plan" \
    --resource-group $RESOURCE_GROUP \
    --sku B1 \
    --is-linux

echo "Criando Web App..."
az webapp create \
    --resource-group $RESOURCE_GROUP \
    --plan "${APP_NAME}-plan" \
    --name $APP_NAME \
    --runtime "JAVA:17-java17"

echo "Configurando variáveis de ambiente..."
az webapp config appsettings set \
    --resource-group $RESOURCE_GROUP \
    --name $APP_NAME \
    --settings \
        AZURE_SQL_SERVER="$SQL_SERVER" \
        AZURE_SQL_DATABASE="$SQL_DATABASE" \
        AZURE_SQL_USERNAME="$SQL_USERNAME" \
        AZURE_SQL_PASSWORD="$SQL_PASSWORD" \
        AZURE_APPLICATION_INSIGHTS_KEY="$(az monitor app-insights component show --app $APP_INSIGHTS --resource-group $RESOURCE_GROUP --query instrumentationKey -o tsv)" \
        SPRING_PROFILES_ACTIVE="azure"

echo "Recursos criados com sucesso!"
echo "SQL Server: $SQL_SERVER.database.windows.net"
echo "Database: $SQL_DATABASE"
echo "Web App: https://$APP_NAME.azurewebsites.net"
echo "Application Insights: $APP_INSIGHTS"