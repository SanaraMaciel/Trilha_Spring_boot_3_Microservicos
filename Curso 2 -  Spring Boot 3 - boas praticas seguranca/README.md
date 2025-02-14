# 🛡️ API REST com Spring Boot 3: Boas Práticas e Segurança

Este projeto foi desenvolvido durante o curso **Spring Boot 3: aplique boas práticas e proteja uma API Rest**. Implementamos melhorias e novos recursos, organizando os pacotes da aplicação em três principais: controller, domain e infra.

## 🗂️ Estrutura do Projeto

- **Controller:** Contém os controladores da aplicação.
  - `AutenticacaoController`
  - `MedicoController`
  - `PacienteController`
- **Domain:** Contém as classes de domínio relacionadas com médicos e pacientes.
- **Infra:** Contém as configurações de infraestrutura, incluindo as de framework e bibliotecas.

## 🚀 Funcionalidades Implementadas

1. **Padronização dos Métodos da API:**
   - Uso de `ResponseEntity` para padronizar os retornos.
   - Código 401 com cabeçalho location no método `cadastrar`.
   - Código 204 no método `excluir`.

2. **Tratamento de Erros:**
   - Classe `TratadorDeErros` usando `ControllerAdvice` do Spring Boot.
   - Tratamento dos erros 404 (`EntityNotFoundException`) e 400.

3. **Segurança com Spring Security:**
   - Implementação de autenticação e autorização com tokens JWT.
   - Configuração de segurança customizada com `SecurityConfigurations`.
   - Autenticação stateless e hashing de senha com algoritmo BCrypt.
   - Interceptação de requisições com `SecurityFilter`.

4. **Geração e Validação de Tokens:**
   - Criação de `TokenService` para geração e validação de tokens JWT.
   - Uso da biblioteca Auth0 Java JWT.

5. **Gestão de Usuários:**
   - Criação das classes `Usuario` e `UsuarioRepository`.
   - Implementação de `AutenticacaoService` e `DadosAutenticacao`.

## 🛠️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
- ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
- ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
- ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white) 
- ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white) 
- ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

## 📚 Continuação

Ainda há muitos recursos a explorar no Spring Boot, como a documentação da API, novas funcionalidades conforme o Trello, e testes automatizados.

---
## 🧛🏽‍♀️ Feito por:
Sanara Maciel Felício

[![LinkedIn](https://img.icons8.com/color/48/linkedin.png)](https://www.linkedin.com/in/sanara-maciel-felicio-99521bb8/)

**Curso concluído: Spring Boot 3: aplique boas práticas e proteja uma API Rest** pela [Alura](https://www.alura.com.br/).
