#!/bin/bash

echo Bem vindo ao processo automatico de deploy

sleep 1

echo INICIANDO PROCESSO MERGE PARA A BRANCH MAIN

git checkout main && git merge develop 

sleep 1

git push origin main && git checkout develop

echo PROCESSO CONCLUIDO