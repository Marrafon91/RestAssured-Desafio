# RestAssured-Desafio

Projeto de prática com testes de API usando **Rest Assured** em uma aplicação Java baseada no projeto **DSMovie**.

## Sobre o projeto

Este repositório foi criado com o objetivo de praticar testes automatizados em APIs REST, cobrindo cenários como:

- autenticação com token JWT
- validação de status HTTP
- envio de payload JSON
- testes de endpoints protegidos
- validações de entrada
- testes de sucesso e erro em requisições

A proposta foi exercitar a escrita de testes de integração com **Rest Assured**, simulando chamadas reais para os endpoints da aplicação.

## Estrutura do repositório

O repositório está dividido em duas partes:

- `dsmovie-projeto-base` → projeto base da aplicação
- `dsmovie-restassured` → projeto com os testes usando Rest Assured

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Security
- JWT
- JUnit 5
- Rest Assured
- Maven

## Objetivo dos testes

Os testes foram desenvolvidos para validar o comportamento da API em diferentes situações, como:

- acesso autorizado e não autorizado
- token válido e token inválido
- recurso existente e inexistente
- dados válidos e inválidos
- respostas com `200`, `401`, `403`, `404`, `422` e outros status conforme o cenário

## Exemplo de cenário testado

Um dos endpoints testados foi o de avaliação de filmes (`/scores`), que recebe requisição `PUT` com payload contendo `movieId` e `score`.

Exemplos de cenários validados:

- deve retornar `404` quando o `movieId` não existe
- deve retornar `422` quando o `movieId` estiver ausente
- deve retornar `422` quando o `score` for menor que zero

Esses testes ajudam a garantir que a API esteja respeitando corretamente as regras de negócio e as validações do DTO.

## Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/Marrafon91/RestAssured-Desafio.git
