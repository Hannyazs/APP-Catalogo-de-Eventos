## App de Gestão de Eventos Acadêmicos

Este é um projeto prático desenvolvido para a faculdade com o objetivo de gerenciar e realizar inscrições em eventos acadêmicos. Ele é dividido em duas partes: um aplicativo nativo em Android (Kotlin) e uma API no backend (Python/FastAPI) integrada a um banco de dados SQLite.

O sistema possui dois perfis de uso:
- **Aluno:** Pode visualizar os eventos disponíveis e se inscrever.
- **Administrador:** Possui permissões para gerenciar a plataforma (Criar, Editar, Excluir e Listar eventos).

---

## Tecnologias Utilizadas

**Mobile (Android)**
- **Linguagem:** Kotlin (Android Studio)
- **Requisições HTTP:** Retrofit 2
- **Interface:** RecyclerView, Fragments e controle dinâmico de botões (escondendo opções de edição para alunos, e de inscrição para administradores).
- **Navegação:** Intents explícitas para transição de telas e passagem de dados (IDs, nomes, etc).

**Backend e Banco de Dados**
- **Linguagem:** Python
- **Framework REST:** FastAPI (rodando com Uvicorn)
- **Banco de Dados:** SQLite3 (tabelas relacionais de eventos e inscrições com uso de INNER JOIN).

---

## Como executar o projeto

### 1. Rodando a API (Backend)
1. Abra a pasta do backend no VS Code.
2. Instale as bibliotecas necessárias rodando no terminal:
   `pip install fastapi uvicorn`
3. Inicie o servidor com o comando:
   `uvicorn main:app --host 0.0.0.0 --port 8000`
*(O `--host 0.0.0.0` é necessário para o emulador do Android conseguir acessar a API local).*

**Swagger (Documentação da API):**
Com a API rodando, você pode ver e testar os endpoints acessando: `http://localhost:8000/docs`

### 2. Rodando o App (Android Studio)
1. Abra o projeto no Android Studio.
2. No arquivo do `RetrofitClient`, configure o IP correto:
   - Se for usar o **Emulador**, coloque: `http://10.0.2.2:8000/`
   - Se for rodar no **celular físico**, coloque o IP local da sua máquina (ex: `http://192.168.1.5:8000/`).
3. Rode o app (Run 'app').

---

## Endpoints da API

- `GET /eventos`: Lista todos os eventos.
- `GET /eventos/{id}`: Busca um evento específico.
- `POST /eventos`: Cria um novo evento.
- `PUT /eventos/{id}`: Edita os dados de um evento.
- `DELETE /eventos/{id}`: Apaga um evento.
- `POST /inscricoes`: Salva a inscrição de um aluno.
- `GET /inscricoes`: Lista as inscrições cruzando os dados com a tabela de eventos (INNER JOIN).

---
