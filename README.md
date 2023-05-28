# Instruções de Execução

## Geração de chaves para validação do token

1. No terminal, execute os seguintes comandos:

```
$ openssl genrsa -out src/main/resources/certs/private.pem 2048

$ openssl rsa -in src/main/resources/certs/private.pem -out src/main/resources/certs/public.pem -pubout -outform PEM
```

Crie o arquivo .env baseado no .env.example

## Pre Requisitos para Usuarios Windows

- Instalar o Docker Desktop


- Crie o arquivo `.env` baseado no `.env.example`.



- Instalar o Docker Desktop.
- Instalar o WSL:
    1. Abrir o PowerShell.
    2. Digitar `$ wsl --install`.
    3. Ir na Microsoft Store e instalar o Ubuntu 22.04.
    4. Abrir o Ubuntu 22.04.
    5. Instalar as dependências: `$ sudo apt update && sudo apt install openjdk-20-jdk maven docker docker-compose`.
    6. Digitar no terminal do Ubuntu: `$ echo export JAVA_HOME=/lib/jvm/java-20-openjdk-amd64 >> ~/.bashrc`.

## Para build

1. Criar o arquivo JAR: `$ mvn install`.
2. Subir no Docker: `$ docker-compose up --build`.

