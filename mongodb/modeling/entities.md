# Modelagem de Dados - FlowDesk

## 1. Levantamento das entidades

O FlowDesk é um sistema de Service Desk destinado ao gerenciamento de solicitações e demandas internas da organização.

A partir das funcionalidades definidas para o sistema, foram identificadas as principais entidades que participarão da modelagem dos dados no MongoDB.

As entidades identificadas são:

- Usuário
- Ticket
- Setor
- Categoria
- Status
- Papel
- Comentário
- Histórico
- Anexo
- Notificação

Essas entidades representam os principais elementos necessários para o funcionamento do sistema de atendimento, permitindo registrar solicitações, responsáveis, setores, classificações, alterações e comunicações relacionadas aos tickets.

---

# 2. Descrição e responsabilidade das entidades

| Entidade | Descrição | Responsabilidade no sistema |
|---|---|---|
| Usuário | Pessoa que acessa a plataforma, podendo atuar como solicitante, atendente ou administrador. | Autenticar no sistema, abrir, responder e gerenciar tickets conforme suas permissões. |
| Ticket | Solicitação ou demanda criada por um usuário. | Centralizar as informações, o status e o andamento de uma demanda. |
| Setor | Departamento físico ou lógico da organização. | Agrupar usuários e receber ou atender tickets destinados ao setor. |
| Categoria | Classificação utilizada para organizar os tickets. | Facilitar a triagem, organização e geração de relatórios. |
| Status | Representa a etapa atual de um ticket. | Organizar o fluxo de atendimento e a visualização no Kanban. |
| Papel | Perfil de acesso atribuído a um usuário. | Definir as permissões e funcionalidades que o usuário pode acessar. |
| Comentário | Mensagem registrada dentro de um ticket. | Registrar a comunicação entre solicitantes e atendentes. |
| Histórico | Registro das alterações realizadas em um ticket. | Manter a rastreabilidade das movimentações e alterações realizadas. |
| Anexo | Arquivo relacionado a um ticket. | Fornecer informações complementares para auxiliar no atendimento. |
| Notificação | Alerta gerado por uma alteração ou evento do sistema. | Informar os usuários sobre atualizações, respostas e movimentações. |

---

# 3. Levantamento dos atributos

## 3.1 Usuário

- `_id`
- `nome`
- `email`
- `senha_hash`
- `setor_id`
- `papel_id`
- `dataCriacao`

O campo `setor_id` referencia o setor ao qual o usuário pertence.

O campo `papel_id` referencia o papel de acesso atribuído ao usuário.

---

## 3.2 Ticket

- `_id`
- `titulo`
- `descricao`
- `prioridade`
- `solicitante_id`
- `responsavel_id`
- `setor_destino_id`
- `categoria_id`
- `status_id`
- `dataCriacao`
- `dataConclusao`

Os campos `solicitante_id` e `responsavel_id` referenciam documentos da collection `usuarios`.

O campo `setor_destino_id` referencia um documento da collection `setores`.

O campo `categoria_id` referencia um documento da collection `categorias`.

O campo `status_id` referencia um documento da collection `status`.

---

## 3.3 Setor

- `_id`
- `nome`
- `descricao`
- `gestor_id`

O campo `gestor_id` referencia o usuário responsável pela gestão do setor.

---

## 3.4 Categoria

- `_id`
- `nome`
- `descricao`
- `setor_relacionado_id`

O campo `setor_relacionado_id` referencia o setor ao qual a categoria está relacionada, quando aplicável.

---

## 3.5 Status

- `_id`
- `nome`
- `cor_kanban`
- `ordem`

O campo `ordem` determina a posição do status no fluxo de atendimento e no painel Kanban.

---

## 3.6 Papel

- `_id`
- `nome`
- `permissoes`

O campo `permissoes` é um array contendo as permissões associadas ao papel.

Exemplos de papéis:

- Administrador
- Atendente
- Solicitante

---

## 3.7 Comentário

- `_id`
- `ticket_id`
- `autor_id`
- `mensagem`
- `dataPublicacao`

O campo `ticket_id` referencia o ticket ao qual o comentário pertence.

O campo `autor_id` referencia o usuário responsável pela publicação.

---

## 3.8 Histórico

- `_id`
- `ticket_id`
- `autor_acao_id`
- `acao`
- `valor_anterior`
- `valor_novo`
- `dataHora`

O campo `ticket_id` referencia o ticket que sofreu a alteração.

O campo `autor_acao_id` referencia o usuário responsável pela ação.

---

## 3.9 Anexo

- `_id`
- `ticket_id`
- `nome_arquivo`
- `url_caminho`
- `extensao`
- `tamanho`

O campo `ticket_id` referencia o ticket ao qual o arquivo está associado.

---

## 3.10 Notificação

- `_id`
- `usuario_id`
- `mensagem`
- `lida`
- `dataEnvio`

O campo `usuario_id` referencia o usuário que receberá a notificação.

---

# 4. Estratégia geral de identificação

Todos os documentos utilizarão o campo `_id` com o tipo `ObjectId`.

O MongoDB possui suporte nativo ao `ObjectId`, permitindo a identificação única dos documentos sem que a aplicação precise controlar manualmente uma sequência numérica.

A utilização de `ObjectId` também facilita a criação das referências entre as collections.

Exemplo:

```javascript
{
    _id: ObjectId(),
    nome: "Carlos Silva",
    email: "carlos@empresa.com"
}
```
Quando uma entidade precisar fazer referência a esse documento, será armazenado o mesmo identificador:

```javascript
{
    _id: ObjectId(),
    nome: "Computador sem acesso à rede",
    solicitante_id: ObjectId("...")
}
```
Dessa forma, os relacionamentos entre os documentos serão estabelecidos por meio dos identificadores.

## 5. Tipos de dados

A modelagem utiliza diferentes tipos de dados disponíveis no MongoDB, de acordo com a necessidade de cada atributo.

Tipo	Exemplos de utilização
ObjectId	_id, solicitante_id, responsavel_id, setor_id
String	nome, email, titulo, descricao
Boolean	lida
Array	permissoes
Date	dataCriacao, dataConclusao, dataEnvio
Int32	ordem, tamanho

Os valores não serão armazenados como String quando outro tipo de dado for mais adequado ao domínio.

## 6. Collections

As entidades serão representadas pelas seguintes collections:

usuarios
tickets
setores
categorias
status
papeis
comentarios
historicos
anexos
notificacoes

Cada collection armazenará documentos referentes à sua respectiva entidade.

Os relacionamentos serão realizados utilizando campos que armazenam ObjectId de documentos pertencentes a outras collections.

## 7. Estratégia de modelagem

Para este projeto foi escolhida a estratégia de References para os relacionamentos entre as entidades.

Os dados serão armazenados em collections independentes e relacionados por meio de identificadores ObjectId.

A decisão foi tomada considerando as características do FlowDesk:

- Usuários são reutilizados em diversos tickets.
- Setores possuem diversos usuários e recebem diversos tickets.
- Categorias podem ser utilizadas em diversos tickets.
- Status são utilizados por diversos tickets.
- Papéis podem ser atribuídos a diversos usuários.
- Comentários possuem autores e pertencem a tickets.
- Históricos podem possuir grande quantidade de registros.
- Anexos possuem vida associada ao ticket, mas podem ser armazenados independentemente.
- Notificações são consultadas principalmente por usuário.

A utilização de referências evita a duplicação dos dados e permite que as informações sejam atualizadas de forma independente.

