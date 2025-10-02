#!/bin/bash

# Script para deploy da aplicação DimDim Bank
# Execute: az login antes de executar este script

# Configurações
RESOURCE_GROUP="dimdim-bank-rg"
APP_NAME="dimdim-bank-app"
PACKAGE_PATH="target/dimdim-bank-0.0.1-SNAPSHOT.jar"

echo "Compilando aplicação..."
mvn clean package -DskipTests

echo "Fazendo deploy da aplicação..."
az webapp deployment source config-zip \
    --resource-group $RESOURCE_GROUP \
    --name $APP_NAME \
    --src $PACKAGE_PATH

echo "Configurando startup command..."
az webapp config set \
    --resource-group $RESOURCE_GROUP \
    --name $APP_NAME \
    --startup-file "java -jar /home/site/wwwroot/app.jar"

echo "Deploy concluído!"
echo "Aplicação disponível em: https://$APP_NAME.azurewebsites.net"
