// FlowDesk - Inserção dos dados
// Atividade 3 - Modelagem de Dados no MongoDB

use("flowdesk");

/*
 * ============================================================
 * 1. IDENTIFICADORES
 * ============================================================
 *
 * Os ObjectIds são criados previamente para que possam ser
 * reutilizados nas referências entre as collections.
 */

// Papéis
const papelAdminId = ObjectId();
const papelAtendenteId = ObjectId();
const papelSolicitanteId = ObjectId();

// Setores
const setorTIId = ObjectId();
const setorRHId = ObjectId();
const setorFinanceiroId = ObjectId();

// Categorias
const categoriaManutencaoId = ObjectId();
const categoriaAcessoId = ObjectId();
const categoriaDuvidaId = ObjectId();
const categoriaCompraId = ObjectId();

// Status
const statusAbertoId = ObjectId();
const statusAtendimentoId = ObjectId();
const statusAguardandoId = ObjectId();
const statusConcluidoId = ObjectId();

// Usuários
const usuarioCarlosId = ObjectId();
const usuarioAnaId = ObjectId();
const usuarioMarianaId = ObjectId();
const usuarioJoaoId = ObjectId();
const usuarioPedroId = ObjectId();

// Tickets
const ticket1Id = ObjectId();
const ticket2Id = ObjectId();
const ticket3Id = ObjectId();
const ticket4Id = ObjectId();

// Comentários
const comentario1Id = ObjectId();
const comentario2Id = ObjectId();
const comentario3Id = ObjectId();

// Histórico
const historico1Id = ObjectId();
const historico2Id = ObjectId();
const historico3Id = ObjectId();

// Anexos
const anexo1Id = ObjectId();
const anexo2Id = ObjectId();

// Notificações
const notificacao1Id = ObjectId();
const notificacao2Id = ObjectId();
const notificacao3Id = ObjectId();


/*
 * ============================================================
 * 2. PAPEIS
 * ============================================================
 */

db.papeis.insertMany([
    {
        _id: papelAdminId,
        nome: "Administrador",
        permissoes: [
            "ticket.criar",
            "ticket.visualizar",
            "ticket.editar",
            "ticket.excluir",
            "ticket.atender",
            "usuario.gerenciar",
            "setor.gerenciar",
            "categoria.gerenciar"
        ]
    },
    {
        _id: papelAtendenteId,
        nome: "Atendente",
        permissoes: [
            "ticket.visualizar",
            "ticket.atender",
            "ticket.comentar"
        ]
    },
    {
        _id: papelSolicitanteId,
        nome: "Solicitante",
        permissoes: [
            "ticket.criar",
            "ticket.visualizar",
            "ticket.comentar"
        ]
    }
]);


/*
 * ============================================================
 * 3. SETORES
 * ============================================================
 */

db.setores.insertMany([
    {
        _id: setorTIId,
        nome: "Tecnologia da Informação",
        descricao: "Responsável pelo suporte de tecnologia e infraestrutura."
    },
    {
        _id: setorRHId,
        nome: "Recursos Humanos",
        descricao: "Responsável pelas demandas relacionadas aos colaboradores."
    },
    {
        _id: setorFinanceiroId,
        nome: "Financeiro",
        descricao: "Responsável pelas demandas financeiras da organização."
    }
]);


/*
 * ============================================================
 * 4. CATEGORIAS
 * ============================================================
 */

db.categorias.insertMany([
    {
        _id: categoriaManutencaoId,
        nome: "Manutenção",
        descricao: "Problemas relacionados a equipamentos e infraestrutura.",
        setor_relacionado_id: setorTIId
    },
    {
        _id: categoriaAcessoId,
        nome: "Acesso",
        descricao: "Solicitações relacionadas a contas, sistemas e permissões.",
        setor_relacionado_id: setorTIId
    },
    {
        _id: categoriaDuvidaId,
        nome: "Dúvida",
        descricao: "Dúvidas e orientações sobre sistemas e processos.",
        setor_relacionado_id: setorTIId
    },
    {
        _id: categoriaCompraId,
        nome: "Compra",
        descricao: "Solicitações relacionadas à aquisição de materiais e serviços.",
        setor_relacionado_id: setorFinanceiroId
    }
]);


/*
 * ============================================================
 * 5. STATUS
 * ============================================================
 */

db.status.insertMany([
    {
        _id: statusAbertoId,
        nome: "Aberto",
        cor_kanban: "#3498DB",
        ordem: 1
    },
    {
        _id: statusAtendimentoId,
        nome: "Em Atendimento",
        cor_kanban: "#F1C40F",
        ordem: 2
    },
    {
        _id: statusAguardandoId,
        nome: "Aguardando",
        cor_kanban: "#E67E22",
        ordem: 3
    },
    {
        _id: statusConcluidoId,
        nome: "Concluído",
        cor_kanban: "#2ECC71",
        ordem: 4
    }
]);


/*
 * ============================================================
 * 6. USUÁRIOS
 * ============================================================
 */

db.usuarios.insertMany([
    {
        _id: usuarioCarlosId,
        nome: "Carlos Silva",
        email: "carlos@empresa.com",
        senha_hash: "hash_exemplo_1",
        setor_id: setorTIId,
        papel_id: papelSolicitanteId,
        dataCriacao: ISODate("2026-10-01T08:00:00Z")
    },
    {
        _id: usuarioAnaId,
        nome: "Ana Souza",
        email: "ana@empresa.com",
        senha_hash: "hash_exemplo_2",
        setor_id: setorTIId,
        papel_id: papelAtendenteId,
        dataCriacao: ISODate("2026-10-01T08:30:00Z")
    },
    {
        _id: usuarioMarianaId,
        nome: "Mariana Santos",
        email: "mariana@empresa.com",
        senha_hash: "hash_exemplo_3",
        setor_id: setorRHId,
        papel_id: papelSolicitanteId,
        dataCriacao: ISODate("2026-10-01T09:00:00Z")
    },
    {
        _id: usuarioJoaoId,
        nome: "João Oliveira",
        email: "joao@empresa.com",
        senha_hash: "hash_exemplo_4",
        setor_id: setorFinanceiroId,
        papel_id: papelSolicitanteId,
        dataCriacao: ISODate("2026-10-01T09:30:00Z")
    },
    {
        _id: usuarioPedroId,
        nome: "Pedro Almeida",
        email: "pedro@empresa.com",
        senha_hash: "hash_exemplo_5",
        setor_id: setorTIId,
        papel_id: papelAdminId,
        dataCriacao: ISODate("2026-10-01T10:00:00Z")
    }
]);


/*
 * ============================================================
 * 7. ATUALIZAÇÃO DOS GESTORES DOS SETORES
 * ============================================================
 *
 * Os gestores são usuários existentes e, por isso,
 * são referenciados por ObjectId.
 */

db.setores.updateOne(
    { _id: setorTIId },
    { $set: { gestor_id: usuarioPedroId } }
);

db.setores.updateOne(
    { _id: setorRHId },
    { $set: { gestor_id: usuarioMarianaId } }
);

db.setores.updateOne(
    { _id: setorFinanceiroId },
    { $set: { gestor_id: usuarioJoaoId } }
);


/*
 * ============================================================
 * 8. TICKETS
 * ============================================================
 */

db.tickets.insertMany([
    {
        _id: ticket1Id,
        titulo: "Computador sem acesso à rede",
        descricao: "O computador do setor administrativo não consegue acessar a rede corporativa.",
        prioridade: "Alta",
        solicitante_id: usuarioCarlosId,
        responsavel_id: usuarioAnaId,
        setor_destino_id: setorTIId,
        categoria_id: categoriaManutencaoId,
        status_id: statusAtendimentoId,
        dataCriacao: ISODate("2026-10-05T08:15:00Z"),
        dataConclusao: null
    },
    {
        _id: ticket2Id,
        titulo: "Solicitação de acesso ao sistema",
        descricao: "Solicitação de acesso ao sistema interno da empresa.",
        prioridade: "Média",
        solicitante_id: usuarioMarianaId,
        responsavel_id: usuarioAnaId,
        setor_destino_id: setorTIId,
        categoria_id: categoriaAcessoId,
        status_id: statusAbertoId,
        dataCriacao: ISODate("2026-10-05T09:00:00Z"),
        dataConclusao: null
    },
    {
        _id: ticket3Id,
        titulo: "Dúvida sobre sistema corporativo",
        descricao: "Usuário solicita orientação sobre utilização de uma funcionalidade do sistema.",
        prioridade: "Baixa",
        solicitante_id: usuarioJoaoId,
        responsavel_id: usuarioAnaId,
        setor_destino_id: setorTIId,
        categoria_id: categoriaDuvidaId,
        status_id: statusAguardandoId,
        dataCriacao: ISODate("2026-10-04T14:30:00Z"),
        dataConclusao: null
    },
    {
        _id: ticket4Id,
        titulo: "Solicitação de compra de equipamento",
        descricao: "Solicitação de aquisição de um novo equipamento para o setor.",
        prioridade: "Média",
        solicitante_id: usuarioCarlosId,
        responsavel_id: usuarioPedroId,
        setor_destino_id: setorFinanceiroId,
        categoria_id: categoriaCompraId,
        status_id: statusConcluidoId,
        dataCriacao: ISODate("2026-10-01T10:30:00Z"),
        dataConclusao: ISODate("2026-10-03T16:00:00Z")
    }
]);


/*
 * ============================================================
 * 9. COMENTÁRIOS
 * ============================================================
 */

db.comentarios.insertMany([
    {
        _id: comentario1Id,
        ticket_id: ticket1Id,
        autor_id: usuarioCarlosId,
        mensagem: "O problema começou hoje pela manhã.",
        dataPublicacao: ISODate("2026-10-05T08:30:00Z")
    },
    {
        _id: comentario2Id,
        ticket_id: ticket1Id,
        autor_id: usuarioAnaId,
        mensagem: "Vamos verificar a conexão e os equipamentos de rede.",
        dataPublicacao: ISODate("2026-10-05T08:45:00Z")
    },
    {
        _id: comentario3Id,
        ticket_id: ticket2Id,
        autor_id: usuarioAnaId,
        mensagem: "A solicitação foi recebida e será analisada.",
        dataPublicacao: ISODate("2026-10-05T09:15:00Z")
    }
]);


/*
 * ============================================================
 * 10. HISTÓRICO
 * ============================================================
 */

db.historicos.insertMany([
    {
        _id: historico1Id,
        ticket_id: ticket1Id,
        autor_acao_id: usuarioAnaId,
        acao: "Mudança de Status",
        valor_anterior: "Aberto",
        valor_novo: "Em Atendimento",
        dataHora: ISODate("2026-10-05T08:40:00Z")
    },
    {
        _id: historico2Id,
        ticket_id: ticket1Id,
        autor_acao_id: usuarioAnaId,
        acao: "Atribuição de Responsável",
        valor_anterior: null,
        valor_novo: "Ana Souza",
        dataHora: ISODate("2026-10-05T08:42:00Z")
    },
    {
        _id: historico3Id,
        ticket_id: ticket4Id,
        autor_acao_id: usuarioPedroId,
        acao: "Mudança de Status",
        valor_anterior: "Em Atendimento",
        valor_novo: "Concluído",
        dataHora: ISODate("2026-10-03T16:00:00Z")
    }
]);


/*
 * ============================================================
 * 11. ANEXOS
 * ============================================================
 */

db.anexos.insertMany([
    {
        _id: anexo1Id,
        ticket_id: ticket1Id,
        nome_arquivo: "erro-rede.png",
        url_caminho: "/uploads/tickets/erro-rede.png",
        extensao: ".png",
        tamanho: 245760
    },
    {
        _id: anexo2Id,
        ticket_id: ticket2Id,
        nome_arquivo: "solicitacao-acesso.pdf",
        url_caminho: "/uploads/tickets/solicitacao-acesso.pdf",
        extensao: ".pdf",
        tamanho: 524288
    }
]);


/*
 * ============================================================
 * 12. NOTIFICAÇÕES
 * ============================================================
 */

db.notificacoes.insertMany([
    {
        _id: notificacao1Id,
        usuario_id: usuarioCarlosId,
        mensagem: "Seu ticket foi atualizado.",
        lida: false,
        dataEnvio: ISODate("2026-10-05T08:45:00Z")
    },
    {
        _id: notificacao2Id,
        usuario_id: usuarioMarianaId,
        mensagem: "Seu ticket foi recebido pelo setor de TI.",
        lida: true,
        dataEnvio: ISODate("2026-10-05T09:10:00Z")
    },
    {
        _id: notificacao3Id,
        usuario_id: usuarioCarlosId,
        mensagem: "O ticket foi encaminhado para atendimento.",
        lida: false,
        dataEnvio: ISODate("2026-10-05T09:20:00Z")
    }
]);


/*
 * ============================================================
 * 13. CONFIRMAÇÃO
 * ============================================================
 */

print("Dados do FlowDesk inseridos com sucesso.");

print("Usuários: " + db.usuarios.countDocuments());
print("Tickets: " + db.tickets.countDocuments());
print("Setores: " + db.setores.countDocuments());
print("Categorias: " + db.categorias.countDocuments());
print("Status: " + db.status.countDocuments());
print("Papéis: " + db.papeis.countDocuments());
print("Comentários: " + db.comentarios.countDocuments());
print("Históricos: " + db.historicos.countDocuments());
print("Anexos: " + db.anexos.countDocuments());
print("Notificações: " + db.notificacoes.countDocuments());

db.notificacoes.insertOne({
    _id: ObjectId(),
    usuario_id: usuarioPedroId,
    mensagem: "Bem-vindo ao FlowDesk.",
    lida: false,
    dataEnvio: ISODate("2026-10-05T10:00:00Z")
});
