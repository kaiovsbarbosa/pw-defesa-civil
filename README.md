# pw-defesa-civil

##  Contexto Geral

O sistema desenvolvido permite realizar manipulações de criação, deleção, atualização e visualização dos processos de vistoria, visita técnica e mapeamento. Ele possui um banco de dados que persiste com informações processadas, além de oferecer uma visualização gráfica através do acesso do portal. Possui também um pequeno relatório na página inicial sobre a quantidade de processos.

---

## Justificativa

O projeto demonstra sua relevância no contexto interno da Defesa Civil, facilitando atendimentos e registro de ocorrências.

---


##  Funcionalidades Principais

- Autenticação JWT com controle de acesso por perfil (`Administrador`, `Operador`, `Visualizador`).
- Criação, deleção, atualização e visualização de visita técnica
- Criação, deleção, atualização e visualização de mapeamento
- Criação, deleção, atualização e visualização de vistoria
- Criação, deleção, atualização e visualização de equipes
- Criação, deleção, atualização e visualização de usuários
- Criação, deleção, atualização e visualização de perfis
- Sistema de atribuição de cargos e permissões

---

## Tecnologias e Ferramentas Utilizadas

| Categoria         | Ferramenta / Tecnologia          |
|-------------------|----------------------------------|
| **Linguagem**     | Java 21                          |
| **Framework**     | Spring Boot 3.x                  |
| **Banco de Dados**| PostgreSQL                       |
| **Frontend**      | HTML5, CSS3, JavaScript          |
| **ORM**           | JPA                              |
| **Segurança**     | Spring Security + JWT            |
| **Build Tool**    | Maven                            |

---

## Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

- Java JDK 21
- SpringBoot 3.5.3+
- Maven 3.8+
- PostgreSQL 14+
- Git
- Navegador Web

---

##  Como Executar o Projeto

1. **Clone o repositório**  
2. **Configure as propriedades da aplicação**
3. **Para propriedade default, configure as credenciais e crie um schema no banco de dados: CREATE DATABASE defesa_civil;**  
4. **Insira um usuário com email e senha na base de dados escolhida**  
5. **Execute a aplicação**

## Colaboradores

[Brayan](github.com/BrayanCordeiro)
[Davi](github.com/dwmgs)
[Jean](github.com/jeanlcsss)
[Kaio](github.com/kaiovsbarbosa)
[Rafael](github.com/rafaal2)