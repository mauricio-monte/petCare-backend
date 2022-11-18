# petCare-backend

Backend do projeto PetCare, implementado durante a disciplina de Projeto de Software 1

## Dependências

Para executar este projeto, é necessário instalar as dependências:

- [Oracle OpenJDK 19](https://jdk.java.net/19/)
- [PostgreSQL 15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
  > Durante a instalação do PostgreSQL, será pedido que registre uma senha do usuário superuser (postgres). Guarde essa senha pois será usada em outras etapas

## Configuração Inicial do Banco de Dados

Pode ser feito via pgAdmin 4 ou via SQL Shell (psql)

1. Logar no psql usando o login postgres

   > Aceitar todas as opções default e preencher apenas a senha (cadastrada durante a instalação)

2. Criar banco de dados e o usuário que será utilizado pela aplicação

```
  CREATE USER petcare_backend WITH PASSWORD 'P3tc4r3';
  CREATE DATABASE petcare OWNER petcare_backend;
  GRANT ALL PRIVILEGES ON DATABASE petcare TO petcare_backend;
  GRANT ALL PRIVILEGES ON DATABASE petcare TO postgres;
```

3. Sair do psql com o comando `exit`

4. Iniciar a aplicação
