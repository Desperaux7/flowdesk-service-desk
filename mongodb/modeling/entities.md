# Levantamento das entidades
Começamos com o levantamento das principais entidades do domínio que serão utilizadas na construção do banco de dados MongoDB. Na hora de criar o banco, algumas dessas entidades poderão ser incorporadas (embutidas) dentro de outras, mas elas existem conceitualmente
## Entidades: 
  - Usuário
  - Ticket
  - setor
  - Categoria
  - Status
  - Nível de acesso
  - Comentário
  - Histórico de movimentação
  - Anexo
  - Notificação

# Descrição e responsabilidade de cada entidade
| Entidade| Descrição| Responsabilidade no sistema|
|--------|-----------|----------|
| Usuário| Pessoa que acessa a plataforma (colaborador ou atendente).| Autenticar no sistema, abrir, responder ou gerenciar tickets.|
|Ticket| A solicitação ou demanda criada por um usuário| Centralizar as informações, status e andamento de uma demanda.|
| Setor| Departamento físico ou lógico da empresa (ex: TI, RH, Compras).| Agrupar usuários e servir como origem ou destino de tickets.|
|Categoria| Classificação do ticket (ex: Manutenção, Dúvida, Compra).| Organizar os tickets e facilitar a triagem e geração de relatórios.|
|Status| Representa a etapa atual do ticket (ex: Aberto, Em Andamento).| Organizar a visualização no Kanban e ditar o fluxo.|
| Nivel| Perfil de acesso (ex: Admin, Solicitante, Atendente).| Garantir que cada usuário só acesse e faça o que tem permissão.|
|Comentário| Mensagens trocadas entre solicitante e atendentes no ticket.| Registrar a comunicação e dúvidas para a resolução da demanda.|
|Histórico| Registro de qualquer alteração feita em um ticket.| Prover transparência (quem mudou o ticket, de qual setor para qual, e quando).|
|Anexo| Arquivo enviado para complementar a solicitação.| Fornecer contexto adicional (imagens de erros, documentos).|
|Notificação| Alerta gerado por mudanças no sistema.| Avisar os usuários quando um ticket for atualizado, movido ou respondido.|

# Levantamento dos atributos
Para cada entidade, identificamos os principais atributos que precisam ser armazenados.
  - Usuário
      - _id
      - nome
      - email
      - senha_hash
      - setor_id (referência ao Setor)
      - papel_id (referência ao Papel)
      - dataCriacao

  - Ticket
      - _id
      - titulo
      - descricao
      - prioridade (Baixa, Média, Alta)
      - solicitante_id (referência ao Usuário)
      - responsavel_id (referência ao Usuário)
      - setor_destino_id (referência ao Setor)
      - categoria_id (referência à Categoria)
      - status_id (referência ao Status)
      - dataCriacao
      - dataConclusao

   - Setor
      - _id
      - nome
      - descricao
      - gestor_id (referência ao Usuário líder do setor)

   - Categoria
      - _id
      - nome
      - descricao
      - setor_relacionado_id (opcional: se a categoria pertencer a um setor específico)

   - Status
      - _id
      - nome (ex: "Em Atendimento")
      - cor_kanban (ex: "#FF5733")
      - ordem (posição da coluna no painel Kanban)

   - Papel
      - _id
      - nome (ex: "Administrador")
      - permissoes (array de regras de acesso)
    
   - Comentário
      - _id
      - ticket_id (referência ao Ticket)
      - autor_id (referência ao Usuário)
      - mensagem
      - dataPublicacao
    
   - Histórico
      - _id
      - ticket_id (referência ao Ticket)
      - autor_acao_id (quem fez a mudança)
      - acao (ex: "Mudança de Status", "Ticket Transferido")
      - valor_anterior
      - valor_novo
      - dataHora
    
   - Anexo
      - _id
      - ticket_id (referência ao Ticket)
      - nome_arquivo
      - url_caminho (link onde o arquivo está salvo)
      - extensao (ex: .pdf, .png)
      - tamanho
    
   - Notificação
      - _id
      - usuario_id (quem vai receber)
      - mensagem
      - lida (verdadeiro/falso)
      - dataEnvio

## 4. Principais Relacionamentos

*   **Usuário x Ticket:** Um usuário (solicitante) pode abrir `N` tickets (1:N). Um usuário (responsável) pode ser atribuído a `N` tickets (1:N).
*   **Ticket x Comentário / Anexo / Histórico:** Um ticket possui `N` comentários, `N` anexos e `N` registros de histórico (1:N).
*   **Setor x Usuário / Ticket:** Um setor agrupa `N` usuários e recebe `N` tickets (1:N).
*   **Usuário x Papel:** Um usuário possui 1 papel, e um papel pode ser atribuído a `N` usuários (1:N).
*   **Usuário x Notificação:** Um usuário recebe `N` notificações (1:N).

---

## 5. Discussão: Embedded Documents x Collections Separadas

Não mapeamos automaticamente todas as 10 entidades para 10 coleções (collections). A decisão entre **Embutir (Embed)** ou **Referenciar (Reference)** vai se basear na frequência de acesso, tamanho dos dados e necessidade de atualização.

### Possíveis Documentos Incorporados (Embedded Documents)
*   **Papel em Usuário:** Como o papel define permissões e é lido a cada requisição de login/autenticação, ele deve ser embutido dentro da collection `Users` (ou tratado como um array de strings).
*   **Status, Categoria, Setor, e resumo do Usuário em Ticket:** Para carregar o Kanban rapidamente sem fazer joins custosos (`$lookup`), os dados estáticos dessas entidades (como o nome da categoria, nome do solicitante, cor e nome do status) devem ser embutidos no documento do `Ticket`.
*   **Comentários e Anexos em Ticket:** Como pertencem estritamente a um único ticket e geralmente não atingem o limite de 16MB do documento, eles devem ser embutidos como arrays de objetos (`[ { ... } ]`) dentro de `Tickets`.

### Collections Separadas (Referências)
*   **Setores, Categorias, Usuários:** Precisam existir como coleções independentes para gerenciar cadastros e permitir a listagem nas telas de administração. O Ticket apenas copia (embute) os dados de leitura vitais no momento de sua criação ou atualização.
*   **Histórico de Movimentação:** Sofre de crescimento ilimitado. Embutir isso no Ticket o deixaria muito pesado. Deve ser uma collection separada.
*   **Notificações:** O padrão de acesso é baseado no usuário (ex: "buscar notificações não lidas para o usuário X"), não no Ticket. Deve ser uma collection independente.

---

## 6. Justificativa das Decisões de Modelagem

1.  **Prioridade na Leitura (Read-Heavy):** A decisão de embutir informações de leitura no documento principal do `Ticket` atende diretamente à pergunta central: *"Como os dados serão utilizados?"*. Por exemplo, a equipe que for utilizar a plataforma precisa visualizar o painel sem lentidão; ler um documento que já contém o nome do solicitante, o setor responsável e os metadados dos anexos evita múltiplas consultas ao banco de dados.
2.  **Evitar Crescimento Descontrolado (Anti-Pattern: Unbounded Arrays):** A separação do `Histórico de Movimentação` foi decidida porque cada mudança de status, atribuição ou edição gera um novo log. Se isso fosse um array dentro do `Ticket`, a performance de reescrita do documento iria degradar ao longo do tempo.
3.  **Isolamento de Domínio de Acesso:** As `Notificações` foram isoladas porque sua renderização ocorre no cabeçalho da aplicação (o "sininho"), antes mesmo do usuário carregar os dados completos de um chamado, justificando a consulta exclusiva por `usuario_id`.
