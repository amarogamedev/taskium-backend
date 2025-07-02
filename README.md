# Taskium

Taskium é uma ferramenta simples de gerenciamento de projetos inspirada no Jira, mas com foco na simplicidade e sem excesso de funcionalidades. Foi criada por [Luis Fellipe Amaro](https://github.com/seu-usuario-aqui) como um projeto pessoal para aprofundar seus conhecimentos em desenvolvimento web.

![image](https://github.com/user-attachments/assets/fd4f7c1f-3cfc-4a5f-9ba4-c102b466595b)

---

## 🚀 Tecnologias utilizadas

- **Backend**: Java 24 com Spring Boot
- **Frontend**: React com Chakra UI
- **Banco de Dados**: MySQL
- **Migrations**: Liquibase
- **Autenticação**: JWT (JSON Web Token)

---

## ✨ Funcionalidades

- ✅ Criar tarefas, bugs ou histórias
- 🔄 Atualizar o status das tarefas via **drag and drop**
- 👥 Adicionar múltiplos membros a um mesmo board
- 🧩 Criar múltiplos boards
- 💬 Adicionar comentários às tarefas
- 🔗 Criar subtarefas
- 📋 Visualizar backlog do board

---

## 🧠 Aprendizados

Durante o desenvolvimento do Taskium, aprendi bastante sobre:

- Implementação e fluxo completo de autenticação usando **JWT**
- Integração entre frontend e backend com foco em segurança e performance
- Boas práticas de arquitetura com Spring Boot
- Gerenciamento de estado e componentes reutilizáveis no React
- Gerenciamento de banco de dados com **Liquibase**

---

## 📦 Como rodar o projeto localmente

### Pré-requisitos

- Node.js (18+)
- Java (21+)
- MySQL

### Backend (Spring Boot)

```bash
# Clone o repositório
git clone https://github.com/amarogamedev/taskium-backend
cd taskium-backend

# Configure o application.properties e variáveis de ambiente com as credenciais do seu banco
```

### Frontend (Vite)

```bash
# Clone o repositório
git clone https://github.com/amarogamedev/taskium-frontend
cd taskium-frontend

# Rode os comandos
npm install
npm run dev
```
