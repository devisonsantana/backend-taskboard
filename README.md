# Task Board Manager

## Boas vindas

Não importa como chegou aqui seja muito bem vindo ao meu simples projeto de task boards, é um projeto totalmente opensource então sinta-se a vontade para clonar ou editar sem problemas.

## Sobre

Este é um projeto que visa a organização de tarefas ou até mesmo sua rotina (*como o [Trello](https://trello.com) por exemplo*), você pode criar um ou mais boards, definir suas tarefas e medida que for terminando as tarefas você pode modificar o status dela de INITIAL até o FINAL, vamos ver um pouco como funciona isso, criamos um board com um nome "tarefas" e outro colocamos o nome "filmes" para exmplo.

|BOARD|         |      |
|:----|:-------:|------|
|Id   |boardName|userID|
|1    |tarefas  |1     |
|2    |filmes   |1     |

Como pode ver temos um ID para usuário para que cada pessoa tenha seu próprio board e suas tarefas pessoais.

Abaixo temos um exemplo coluna do board onde ficam organizados a estrutura de tarefas, é possível escolher o nome da coluna e adicionar mais campos com status PENDING, pode ser registrada como uma coluna de testes ou algo mais especifico como uma tarefas feita em várias etapas. 

|COLUMN|          |     |                  |       |
|:-----|:--------:|:---:|:----------------:|-------|
|Id    |columnName|order|status            |boardId|
|1     |a fazer   |0    |STARTING/INITIAL  |1      |
|2     |fazendo   |1    |PENDING           |1      |
|3     |testando  |2    |PENDING (opcional)|1      |
|4     |finalizado|3    |FINSISHED/FINAL   |1      |
|5     |cancelado |4    |CANCELED          |1      |
|6     |para ver  |0    |STARTING/INITIAL  |2      |
|7     |assistindo|1    |PENDING           |2      |
|8     |assistidos|2    |FINSISHED/FINAL   |2      |
|9     |desistiu  |3    |CANCELED          |2      |

## Inspiração

A idéia desse projeto surgiu enquanto praticava Java usando o liquibase para controle de versão do banco de dados, o projeto em questão se tratava de um board de tarefas, porém todas as interações seriam via terminal, daí me veio a mente "por que não criar uma API disso?", com isso em mente estou iniciando esse projeto de API Rest em Java usando Spring Boot e criar meu próprio gerenciador de tarefas.

Meu intuito é fazer com calma sem pressa de terminar, quero ter bastante liberdade para aplicar coisas novas que vou aprender ainda ao decorrer desse projeto.

A idéia é que seja um projeto full-stack, sendo assim pretendo desenvolver uma página web após a API estar finalizada.

## Divisão de branchs

### Main

Possui tudo que já funciona e provavelmente já tem um link que direciona para a API em execução, se não estiver está em desenvolvimento.

### Develop

Pode conter erros ou bugs cabulosos, código ainda em desenvolvimento e em fase de testes em ambiente local e isolado.

<hr>

<p align="center">
<strong>
Este README ainda está em desenvolvimento e nem tudo o que está escrito nele pode ter na aplicação final.
</strong>
</p>