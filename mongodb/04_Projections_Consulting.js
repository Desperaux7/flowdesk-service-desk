/*
============================================================
12. PROJEÇÃO - 5 CONSULTAS
============================================================
Foram implementadas 5 projeções sendo 3 mais simplistas e duas com condições e $lookup para uma consulta embasada, no final foi explicado o motivo dessas duas projeções
*/
// 12.1 Tela inicial
 db.tickets.find(
   {},
   { _id: 0, titulo: 1, prioridade: 1, status_id: 1 }
 );

// 12.2 Tela de detalhes
 db.tickets.find(
   { titulo: "Computador sem acesso à rede" },
   { _id: 0, titulo: 1, descricao: 1, prioridade: 1, solicitante_id: 1, responsavel_id: 1, dataCriacao: 1 }
 );

// 12.3 Lista de notificações
 db.notificacoes.find(
   {},
   { _id: 0, usuario_id: 1, mensagem: 1, lida: 1, dataEnvio: 1 }
 );

// 12.4 Lista de tickets com nomes reais em vez de apenas ObjectIds
 db.tickets.aggregate([
   {
     $lookup: {
       from: "usuarios",
       localField: "solicitante_id",
       foreignField: "_id",
       as: "solicitante"
     }
   },
   {
     $lookup: {
       from: "usuarios",
       localField: "responsavel_id",
       foreignField: "_id",
       as: "responsavel"
     }
   },
   {
     $lookup: {
       from: "setores",
       localField: "setor_destino_id",
       foreignField: "_id",
       as: "setor"
     }
   },
   {
     $lookup: {
       from: "categorias",
       localField: "categoria_id",
       foreignField: "_id",
       as: "categoria"
     }
   },
   {
     $lookup: {
       from: "status",
       localField: "status_id",
       foreignField: "_id",
       as: "status"
     }
   },
   {
     $project: {
       _id: 0,
       titulo: 1,
       prioridade: 1,
       dataCriacao: 1,
       solicitante: { $arrayElemAt: ["$solicitante.nome", 0] },
       responsavel: { $arrayElemAt: ["$responsavel.nome", 0] },
       setor: { $arrayElemAt: ["$setor.nome", 0] },
       categoria: { $arrayElemAt: ["$categoria.nome", 0] },
       status: { $arrayElemAt: ["$status.nome", 0] }
     }
   }
 ]);

// 12.5 Lista administrativa de usuários com setor e papel
 db.usuarios.aggregate([
   {
     $lookup: {
       from: "setores",
       localField: "setor_id",
       foreignField: "_id",
       as: "setor"
     }
   },
   {
     $lookup: {
       from: "papeis",
       localField: "papel_id",
       foreignField: "_id",
       as: "papel"
     }
   },
   {
     $project: {
       _id: 0,
       nome: 1,
       email: 1,
       dataCriacao: 1,
       setor: { $arrayElemAt: ["$setor.nome", 0] },
       papel: { $arrayElemAt: ["$papel.nome", 0] }
     }
   }
 ]);

/*
IMPORTÂNCIA DAS PROJEÇÕES ELABORADAS:
- Tickets: uma tela de atendimento precisa mostrar títulos e nomes
  legíveis de solicitante, responsável, setor, categoria e status,
  em vez de apresentar apenas ObjectIds.
- Usuários: a administração precisa identificar rapidamente nome,
  e-mail, setor e papel. Os dados já existem nas References da Parte 9.
*/

/*
============================================================
13. SCHEMA FLEXÍVEL
============================================================
O ticket 1 possui o campo; o ticket 2 terá o campo ausente.
*/

// Ticket 1 mantém o campo já existente
db.tickets.updateOne(
  { _id: ticket1Id },
  { $set: { dataConclusao: null } }
);

// Ticket 2 passa a não possuir o campo
db.tickets.updateOne(
  { _id: ticket2Id },
  { $unset: { dataConclusao: "" } }
);

// Comparação estrutural
db.tickets.find(
  { _id: { $in: [ticket1Id, ticket2Id] } },
  { _id: 0, titulo: 1, dataConclusao: 1 }
);

/*
Respostas:
1) A diferença é realmente necessária?
Não como regra geral. Para o FlowDesk, dataConclusao já é parte da
estrutura do ticket e manter um padrão facilita as consultas.

2) A flexibilidade ajuda ou prejudica?
Ajuda quando um campo é realmente opcional; sem controle, pode
prejudicar consultas, validações e manutenção.

3) Seria melhor uma estrutura consistente?
Sim. Os campos principais devem ser padronizados e a flexibilidade
usada somente quando houver uma justificativa real.
*/

/*
============================================================
14. CONSULTAS PARA VALIDAR A MODELAGEM
============================================================
*/
// 14.1 - 3 consultas simples
 db.usuarios.find({ nome: "Carlos Silva" });
 db.tickets.find({ prioridade: "Alta" });
 db.notificacoes.find({ lida: false });

// 14.2 - 2 consultas com múltiplas condições
 db.tickets.find({ prioridade: "Média", dataConclusao: null });
 db.usuarios.find({ setor_id: setorTIId, papel_id: papelSolicitanteId });

// 14.3 - 2 consultas com projeção
 db.tickets.find({}, { _id: 0, titulo: 1, prioridade: 1 });
 db.usuarios.find({}, { _id: 0, nome: 1, email: 1 });

// 14.4 - 1 consulta utilizando referência
 db.tickets.find(
   { setor_destino_id: setorTIId },
   { _id: 0, titulo: 1, setor_destino_id: 1 }
 );


// 14.5 - 1 consulta utilizando array
 db.papeis.find(
   { permissoes: "ticket.comentar" },
   { _id: 0, nome: 1, permissoes: 1 }
 );

// 14.6 - 1 consulta utilizando ordenação
 db.tickets.find(
   {},
   { _id: 0, titulo: 1, prioridade: 1, dataCriacao: 1 }
 ).sort({ dataCriacao: -1 });
