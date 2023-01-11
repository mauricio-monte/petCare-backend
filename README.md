# petCare-backend

Backend do projeto PetCare, implementado durante a disciplina de Projeto de Software 1

## Como executar este projeto localmente

### Dependências

Para executar este projeto localmente, é necessário instalar as dependências:

- [Oracle OpenJDK 19](https://jdk.java.net/19/) 
  > O IntelliJ faz o download dessa versão se for configurada como a versão da JDK para o projeto. 
- [PostgreSQL 15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
  > Durante a instalação do PostgreSQL, será pedido que registre uma senha do usuário superuser (postgres). Guarde essa senha pois será usada em outras etapas

### Configuração Inicial do Banco de Dados

Pode ser feito via pgAdmin 4 ou via SQL Shell (psql)

1. Logar no psql usando o login postgres
    ``` 
    # Para os campos vazios basta apertar enter sem digitar nada 
    Server [localhost]:
    Database [postgres]: 
    Port [5432]:
    Username [postgres]:
    Password for user postgres: <inserir senha cadastrada anteriormente>
    ```

2. Criar banco de dados com o nome `petcare`
    ```
    CREATE DATABASE petcare;
    ```

3. Sair do psql com o comando `exit`

4. Preencher a senha do usuário `postgres` para o profile `dev`, no arquivo application.yml em src/main/resources 

5. Iniciar a aplicação pela IDE
