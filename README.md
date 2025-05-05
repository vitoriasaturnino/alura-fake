
# AluraFake

Uma aplicação web desenvolvida com **Spring Boot** para gerenciamento de cursos e tarefas educacionais, como questões abertas, múltipla escolha e escolha única. O AluraFake permite a criação e publicação de cursos, organização sequencial de tarefas e gerenciamento de usuários.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Maven
- MySQL
- JPA / Hibernate
- JUnit 5

---

## 🛠️ Pré-requisitos

- **Java** 21 ou superior ([Download](https://adoptium.net/))
- **Maven** 3.8 ou superior ([Download](https://maven.apache.org/download.cgi))
- Banco de dados **MySQL** ([Download](https://dev.mysql.com/downloads/mysql/))

---

## 📥 Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/vitoriasaturnino/alurafake.git
cd alurafake
```

### 2. Configuração do banco de dados

Crie um banco de dados MySQL com o nome `alurafake`:

```sql
CREATE DATABASE alurafake;
```

Configure as credenciais do banco em `src/main/resources/application.yml` (ou `application.properties`):

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/alurafake
    username: seu_usuario
    password: sua_senha
```

### 3. Executar o projeto

Compile o projeto e gere o pacote `.jar`:

```bash
mvn clean package
```

Em seguida, execute a aplicação:

```bash
java -jar target/AluraFake-0.0.1-SNAPSHOT.jar
```

O servidor ficará disponível em [http://localhost:8080](http://localhost:8080).

---

## 📌 Endpoints principais

### 👤 Usuários

- **Criar usuário**

```http
POST /user/new
Content-Type: application/json

{
  "name": "Raphael Santos",
  "email": "rapahel.santos@alura.com.br",
  "role": "INSTRUCTOR"
}
```

- **Listar todos os usuários**

```http
GET /user/all
```

---

### 📚 Cursos

- **Criar curso**

```http
POST /course/new
Content-Type: application/json

{
  "title": "Elixir",
  "description": "Curso de Elixir",
  "emailInstructor": "rapahel.santos@alura.com.br"
}
```

- **Publicar curso**

```http
POST /course/4/publish
```

- **Listar todos os cursos**

```http
GET /course/all
```

---

### ✅ Tarefas

- **Criar tarefa de texto aberto**

```http
POST /task/new/opentext
Content-Type: application/json

{
  "courseId": 4,
  "statement": "O que aprendemos na aula de hoje?",
  "order": 1
}
```

- **Criar tarefa de escolha única**

```http
POST /task/new/singlechoice
Content-Type: application/json

{
  "courseId": 4,
  "statement": "Qual linguagem estamos aprendendo neste curso?",
  "order": 2,
  "options": [
    { "option": "Java", "isCorrect": true },
    { "option": "Python", "isCorrect": false },
    { "option": "Elixir", "isCorrect": false }
  ]
}
```

- **Criar tarefa de múltipla escolha**

```http
POST /task/new/multiplechoice
Content-Type: application/json

{
  "courseId": 4,
  "statement": "Quais destas são linguagens de programação?",
  "order": 3,
  "options": [
    { "option": "Java", "isCorrect": true },
    { "option": "Python", "isCorrect": true },
    { "option": "HTML", "isCorrect": false },
    { "option": "Javascript", "isCorrect": true }
  ]
}
```

---

## 🧪 Execução de Testes

Para executar testes unitários e de integração:

```bash
mvn test
```

---

## 👩‍💻 Autora

| [<img src="https://avatars.githubusercontent.com/u/68754092?v=4" width="120" style="border-radius:50%">](https://www.linkedin.com/in/vit%C3%B3ria-cristina-saturnino-de-moura-6393391b0/) |
| :-----------------------------------------------------------------------------------------------------------------------------------------: |
|                               [**Vitória Moura**](https://www.linkedin.com/in/vit%C3%B3ria-cristina-saturnino-de-moura-6393391b0/) |

---

✨ Desenvolvido com dedicação por Vitória Moura.
