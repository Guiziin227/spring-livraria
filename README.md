# 📚 Sistema de Livraria - API REST

Uma API REST completa para gerenciamento de livraria desenvolvida com Spring Boot, oferecendo funcionalidades para controle de livros, autores, categorias, editoras, clientes e vendas.

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados relacional
- **MapStruct** - Mapeamento de objetos
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📋 Funcionalidades

### Entidades Principais
- **Livros** - Cadastro e gerenciamento de livros
- **Autores** - Controle de autores
- **Categorias** - Classificação de livros por categoria
- **Editoras** - Gerenciamento de editoras
- **Clientes** - Cadastro de clientes
- **Vendas** - Controle de vendas e notas fiscais

### Recursos da API
- ✅ CRUD completo para todas as entidades
- ✅ Relacionamentos entre entidades (Many-to-Many, One-to-Many)
- ✅ Validação de dados de entrada
- ✅ Tratamento de exceções personalizadas
- ✅ DTOs para requests e responses
- ✅ Mapeamento automático com MapStruct
- ✅ Formatação de datas (dd/MM/yyyy)

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

```
src/main/java/com/github/guiziin227/livraria/
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
│   ├── requests/        # DTOs de entrada
│   ├── responses/       # DTOs de saída
│   └── simples/         # DTOs simplificados
├── exceptions/          # Exceções personalizadas
├── mapper/              # Mapeadores MapStruct
├── model/               # Entidades JPA
├── repositories/        # Repositórios Spring Data
└── services/           # Lógica de negócio
```

## 🛠️ Configuração e Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- MySQL 8.0+

### Configuração do Banco de Dados
1. Crie um banco de dados MySQL chamado `livraria`
2. Configure as credenciais no arquivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/livraria?useTimeZone=true&serverTimezone=UTC
    username: seu_usuario
    password: sua_senha
```

### Executando a Aplicação
```bash
# Clone o repositório
git clone [URL_DO_REPOSITORIO]

# Navegue até o diretório do projeto
cd livraria

# Execute a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 🔗 Endpoints da API

### Livros
- `GET /api/livros` - Lista todos os livros
- `GET /api/livros/{id}` - Busca livro por ID
- `POST /api/livros` - Cria novo livro
- `PUT /api/livros/{id}` - Atualiza livro
- `DELETE /api/livros/{id}` - Remove livro

### Autores
- `GET /api/autores` - Lista todos os autores
- `GET /api/autores/{id}` - Busca autor por ID
- `POST /api/autores` - Cria novo autor
- `PUT /api/autores/{id}` - Atualiza autor
- `DELETE /api/autores/{id}` - Remove autor

### Categorias
- `GET /api/categorias` - Lista todas as categorias
- `GET /api/categorias/{id}` - Busca categoria por ID
- `POST /api/categorias` - Cria nova categoria
- `PUT /api/categorias/{id}` - Atualiza categoria
- `DELETE /api/categorias/{id}` - Remove categoria

### Editoras
- `GET /api/editoras` - Lista todas as editoras
- `GET /api/editoras/{id}` - Busca editora por ID
- `POST /api/editoras` - Cria nova editora
- `PUT /api/editoras/{id}` - Atualiza editora
- `DELETE /api/editoras/{id}` - Remove editora

### Clientes
- `GET /api/clientes` - Lista todos os clientes
- `GET /api/clientes/{id}` - Busca cliente por ID
- `POST /api/clientes` - Cria novo cliente
- `PUT /api/clientes/{id}` - Atualiza cliente
- `DELETE /api/clientes/{id}` - Remove cliente

### Vendas
- `GET /api/vendas` - Lista todas as vendas
- `GET /api/vendas/{id}` - Busca venda por ID
- `POST /api/vendas` - Cria nova venda
- `PUT /api/vendas/{id}` - Atualiza venda
- `DELETE /api/vendas/{id}` - Remove venda

### Relacionamentos
- `GET /api/livro-autor` - Gerencia relacionamentos livro-autor
- `GET /api/livro-categoria` - Gerencia relacionamentos livro-categoria

## 📊 Exemplo de Uso

### Criando um novo livro
```json
POST /api/livros
{
  "titulo": "O Senhor dos Anéis",
  "isbn": "978-85-359-0277-6",
  "preco": 45.90,
  "dataPublicacao": "15/07/1954",
  "editoraId": 1,
  "estoque": 100
}
```

### Registrando uma venda
```json
POST /api/vendas
{
  "clienteId": 1,
  "livroId": 1,
  "notaFiscal": "NF-2025-001",
  "dataVenda": "04/01/2025",
  "valorTotal": 45.90
}
```

## 🔧 Tratamento de Erros

A API possui tratamento personalizado para diversos tipos de erros:
- `BusinessRuleException` - Regras de negócio
- `ResourceNotFoundException` - Recurso não encontrado
- `DuplicateResourceException` - Recurso duplicado
- `StockException` - Problemas de estoque
- `InvalidDateRangeException` - Datas inválidas

## 📝 Observações

- O projeto utiliza chave primária composta para a entidade `Venda`
- Relacionamentos Many-to-Many são implementados através de tabelas de junção
- Todas as datas são formatadas no padrão brasileiro (dd/MM/yyyy)
- A aplicação possui validação automática de dados de entrada

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

**Desenvolvido com ❤️ por [guiziin227](https://github.com/guiziin227)**
