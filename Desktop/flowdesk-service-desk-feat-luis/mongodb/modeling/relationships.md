# Relacionamentos - FlowDesk

## 1. Estratégia escolhida

Para o projeto FlowDesk foi escolhida a estratégia de **References** para representar os relacionamentos entre as entidades.

Os dados serão mantidos em collections separadas e relacionados por meio do campo `_id`, utilizando `ObjectId`.

A escolha por References considera que as entidades do sistema possuem vida própria, são reutilizadas por diferentes documentos e precisam ser atualizadas de forma independente.

---

## 2. Principais relacionamentos

| Entidade A | Relação           | Entidade B  | Cardinalidade |
| ---------- | ----------------- | ----------- | ------------- |
| Usuário    | solicita          | Ticket      | 1:N           |
| Usuário    | é responsável por | Ticket      | 1:N           |
| Setor      | possui            | Usuário     | 1:N           |
| Setor      | recebe            | Ticket      | 1:N           |
| Categoria  | classifica        | Ticket      | 1:N           |
| Status     | representa        | Ticket      | 1:N           |
| Papel      | é atribuído a     | Usuário     | 1:N           |
| Ticket     | possui            | Comentário  | 1:N           |
| Usuário    | escreve           | Comentário  | 1:N           |
| Ticket     | possui            | Histórico   | 1:N           |
| Usuário    | realiza           | Histórico   | 1:N           |
| Ticket     | possui            | Anexo       | 1:N           |
| Usuário    | recebe            | Notificação | 1:N           |
| Setor      | possui categoria  | Categoria   | 1:N           |

---

## 3. Representação dos relacionamentos

### 3.1 Usuário → Ticket

Um usuário pode abrir diversos tickets.

```text
Usuário 1 ───────── N Ticket
         solicitante
```

Além disso, um usuário também pode ser responsável por diversos tickets:

```text
Usuário 1 ───────── N Ticket
         responsável
```

Os relacionamentos serão armazenados nos tickets utilizando:

```javascript
solicitante_id: ObjectId(...)
responsavel_id: ObjectId(...)
```

### 3.2 Setor → Usuário

Um setor pode possuir diversos usuários, enquanto cada usuário pertence a um setor.

```text
Setor 1 ───────── N Usuário
```

O relacionamento será representado em `usuarios`:

```javascript
setor_id: ObjectId(...)
```

### 3.3 Setor → Ticket

Um setor pode receber diversos tickets.

```text
Setor 1 ───────── N Ticket
```

O ticket armazenará:

```javascript
setor_destino_id: ObjectId(...)
```

### 3.4 Categoria → Ticket

Uma categoria pode ser utilizada em diversos tickets.

```text
Categoria 1 ───────── N Ticket
```

O relacionamento será armazenado em:

```javascript
categoria_id: ObjectId(...)
```

### 3.5 Status → Ticket

Um status pode estar associado a diversos tickets.

```text
Status 1 ───────── N Ticket
```

O ticket armazenará:

```javascript
status_id: ObjectId(...)
```

### 3.6 Papel → Usuário

Um papel pode ser atribuído a diversos usuários.

```text
Papel 1 ───────── N Usuário
```

O usuário armazenará:

```javascript
papel_id: ObjectId(...)
```

### 3.7 Ticket → Comentário

Um ticket pode possuir diversos comentários.

```text
Ticket 1 ───────── N Comentário
```

Cada comentário armazenará:

```javascript
ticket_id: ObjectId(...)
```

Além disso, o comentário possuirá uma referência ao usuário que o criou:

```javascript
autor_id: ObjectId(...)
```

### 3.8 Ticket → Histórico

Um ticket pode possuir diversos registros de histórico.

```text
Ticket 1 ───────── N Histórico
```

Cada registro armazenará:

```javascript
ticket_id: ObjectId(...)
```

O usuário responsável pela ação será identificado por:

```javascript
autor_acao_id: ObjectId(...)
```

### 3.9 Ticket → Anexo

Um ticket pode possuir diversos anexos.

```text
Ticket 1 ───────── N Anexo
```

O relacionamento será representado por:

```javascript
ticket_id: ObjectId(...)
```

### 3.10 Usuário → Notificação

Um usuário pode receber diversas notificações.

```text
Usuário 1 ───────── N Notificação
```

Cada notificação armazenará:

```javascript
usuario_id: ObjectId(...)
```

### 3.11 Setor → Categoria

Um setor pode possuir diversas categorias relacionadas.

```text
Setor 1 ───────── N Categoria
```

A categoria armazenará:

```javascript
setor_relacionado_id: ObjectId(...)
```

---

## 4. Justificativa da utilização de References

A estratégia de References foi escolhida pelos seguintes motivos.

### 4.1 Dados compartilhados

Usuários, setores, categorias, status e papéis são utilizados por diversos documentos.

Por exemplo, um mesmo usuário pode ser responsável por diversos tickets. Se os dados do usuário fossem duplicados em cada ticket, uma alteração no cadastro do usuário poderia exigir a atualização de vários documentos.

Utilizando uma referência, o ticket mantém apenas:

```javascript
responsavel_id: ObjectId(...)
```

E os dados do usuário permanecem em sua própria collection.

### 4.2 Vida independente das entidades

As entidades do FlowDesk possuem operações próprias.

Um usuário pode ser cadastrado ou atualizado independentemente de um ticket.

Um setor pode ser alterado sem precisar modificar todos os tickets associados.

Uma categoria pode ser atualizada independentemente dos tickets que a utilizam.

Isso caracteriza entidades com vida própria, tornando References apropriado para o modelo.

### 4.3 Redução da duplicação

A utilização de referências evita que os mesmos dados sejam repetidos em diversos documentos.

Por exemplo, se um setor possuir 100 tickets, não é necessário armazenar todas as informações do setor em cada ticket.

Cada ticket pode armazenar somente:

```javascript
setor_destino_id: ObjectId(...)
```

### 4.4 Atualização independente

As collections podem ser atualizadas de forma independente.

Caso o nome de um setor seja alterado, basta atualizar o documento correspondente em `setores`.

Os tickets continuarão apontando para o mesmo `_id`.

### 4.5 Crescimento dos dados

Algumas entidades podem crescer consideravelmente.

O histórico de movimentações é um exemplo.

Um ticket pode possuir diversas alterações durante seu ciclo de vida:

```text
Ticket criado
       ↓
Status alterado
       ↓
Responsável atribuído
       ↓
Setor alterado
       ↓
Comentário adicionado
       ↓
Status alterado
       ↓
Ticket concluído
```

Armazenar todo esse histórico dentro de um único documento poderia fazer com que o documento crescesse continuamente.

Por isso, o histórico será armazenado em uma collection própria e relacionado ao ticket por meio de `ticket_id`.

---

## 5. Exemplos dos documentos

### 5.1 Usuário

```javascript
{
    _id: ObjectId("..."),
    nome: "Carlos Silva",
    email: "carlos@empresa.com",
    senha_hash: "hash_exemplo",
    setor_id: ObjectId("..."),
    papel_id: ObjectId("..."),
    dataCriacao: ISODate("2026-10-01")
}
```

### 5.2 Ticket

```javascript
{
    _id: ObjectId("..."),
    titulo: "Computador sem acesso à rede",
    descricao: "Computador não consegue acessar a rede corporativa.",
    prioridade: "Alta",
    solicitante_id: ObjectId("..."),
    responsavel_id: ObjectId("..."),
    setor_destino_id: ObjectId("..."),
    categoria_id: ObjectId("..."),
    status_id: ObjectId("..."),
    dataCriacao: ISODate("2026-10-05"),
    dataConclusao: null
}
```

### 5.3 Comentário

```javascript
{
    _id: ObjectId("..."),
    ticket_id: ObjectId("..."),
    autor_id: ObjectId("..."),
    mensagem: "O equipamento foi encaminhado para análise.",
    dataPublicacao: ISODate("2026-10-05")
}
```

### 5.4 Histórico

```javascript
{
    _id: ObjectId("..."),
    ticket_id: ObjectId("..."),
    autor_acao_id: ObjectId("..."),
    acao: "Mudança de Status",
    valor_anterior: "Aberto",
    valor_novo: "Em Atendimento",
    dataHora: ISODate("2026-10-05")
}
```

---

## 6. Estratégia de `_id`

Todos os documentos utilizarão `ObjectId` como valor do campo `_id`.

A escolha foi feita porque o MongoDB possui suporte nativo a esse tipo de identificador, permitindo identificar os documentos de forma única.

Além disso, o `ObjectId` pode ser utilizado diretamente nas referências entre collections.

Exemplo:

```javascript
{
    _id: ObjectId("68e2a123456789abcdef1234")
}
```

Outro documento pode armazenar:

```javascript
{
    usuario_id: ObjectId("68e2a123456789abcdef1234")
}
```

Dessa forma, os documentos ficam relacionados sem necessidade de utilizar identificadores textuais personalizados.
