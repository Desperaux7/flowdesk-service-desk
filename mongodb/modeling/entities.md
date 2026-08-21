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


