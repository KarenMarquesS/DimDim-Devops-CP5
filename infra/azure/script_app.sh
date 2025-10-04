#!/bin/bash

# Variáveis
RESOURCE_GROUP="rg-dimdim"
LOCATION="brazilsouth"
ACR_NAME="dimdimacr"
API_IMAGE_NAME="dimdim-api"
ACI_NAME="dimdim-api-aci"
SQL_SERVER_NAME="dimdim-sql-server-$RANDOM"
SQL_DB_NAME="dimdimdb"
SQL_ADMIN_USER="sqladmin"
SQL_ADMIN_PASS="DimDim@12345"
APPINSIGHTS_NAME="dimdim-insights"

echo ">> Criando Resource Group"
az group create --name $RESOURCE_GROUP --location $LOCATION

echo ">> Criando Azure SQL Server"
az sql server create \
    --name $SQL_SERVER_NAME \
    --resource-group $RESOURCE_GROUP \
    --location $LOCATION \
    --admin-user $SQL_ADMIN_USER \
    --admin-password $SQL_ADMIN_PASS

echo ">> Criando Azure SQL Database"
az sql db create \
    --resource-group $RESOURCE_GROUP \
    --server $SQL_SERVER_NAME \
    --name $SQL_DB_NAME \
    --service-objective S0

echo ">> Habilitando acesso ao SQL (firewall rule para acesso público)"
az sql server firewall-rule create \
    --resource-group $RESOURCE_GROUP \
    --server $SQL_SERVER_NAME \
    --name AllowAzureServices \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 0.0.0.0

echo ">> Criando Azure Container Registry"
az acr create \
    --resource-group $RESOURCE_GROUP \
    --name $ACR_NAME \
    --sku Standard \
    --location $LOCATION \
    --admin-enabled true

ACR_LOGIN_SERVER=$(az acr show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query loginServer --output tsv)
ACR_ADMIN_USERNAME=$(az acr credential show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query username --output tsv)
ACR_ADMIN_PASSWORD=$(az acr credential show --name $ACR_NAME --resource-group $RESOURCE_GROUP --query passwords[0].value --output tsv)

echo ">> Buildando imagem da API Spring Boot"
docker build -t $API_IMAGE_NAME -f Dockerfile .

echo ">> Fazendo login no ACR"
az acr login --name $ACR_NAME

echo ">> Tag e Push da imagem"
docker tag $API_IMAGE_NAME $ACR_LOGIN_SERVER/$API_IMAGE_NAME:v1
docker push $ACR_LOGIN_SERVER/$API_IMAGE_NAME:v1

echo ">> Criando Application Insights"
az monitor app-insights component create \
    --app $APPINSIGHTS_NAME \
    --location $LOCATION \
    --resource-group $RESOURCE_GROUP \
    --application-type web

APPINSIGHTS_CONN=$(az monitor app-insights component show \
    --app $APPINSIGHTS_NAME \
    --resource-group $RESOURCE_GROUP \
    --query connectionString \
    --output tsv)

echo ">> Criando Container Instance para API"
az container create \
    --resource-group $RESOURCE_GROUP \
    --name $ACI_NAME \
    --image $ACR_LOGIN_SERVER/$API_IMAGE_NAME:v1 \
    --cpu 2 \
    --memory 4 \
    --registry-login-server $ACR_LOGIN_SERVER \
    --registry-username $ACR_ADMIN_USERNAME \
    --registry-password $ACR_ADMIN_PASSWORD \
    --ports 8080 \
    --os-type Linux \
    --ip-address Public \
    --environment-variables \
        SPRING_DATASOURCE_URL="jdbc:sqlserver://$SQL_SERVER_NAME.database.windows.net:1433;database=$SQL_DB_NAME;encrypt=true;trustServerCertificate=false;loginTimeout=30;" \
        SPRING_DATASOURCE_USERNAME=$SQL_ADMIN_USER@$SQL_SERVER_NAME \
        SPRING_DATASOURCE_PASSWORD=$SQL_ADMIN_PASS \
        APPLICATIONINSIGHTS_CONNECTION_STRING=$APPINSIGHTS_CONN

API_IP=$(az container show --resource-group $RESOURCE_GROUP --name $ACI_NAME --query ipAddress.ip --output tsv)
echo ">> API rodando em: http://$API_IP:8080"
