// 11 REFERENCES
db.usuarios.find({ setor_id: setorTIId }, { _id: 1, nome: 1, setor_id: 1 });
db.usuarios.find({ papel_id: papelAdminId }, { _id: 1, nome: 1, papel_id: 1 });
db.comentarios.find({ autor_id: usuarioCarlosId }, { _id: 1, ticket_id: 1, autor_id: 1, mensagem: 1 });
db.notificacoes.find({ usuario_id: usuarioCarlosId }, { _id: 1, usuario_id: 1, mensagem: 1 });

// 11.2 TICKET
db.comentarios.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
db.historicos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
db.anexos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
db.tickets.find({ _id: ticket1Id }, { _id: 1, titulo: 1, solicitante_id: 1, responsavel_id: 1, setor_destino_id: 1, categoria_id: 1, status_id: 1 });

// 11.3 SETOR
db.usuarios.find({ setor_id: setorTIId }, { _id: 1, nome: 1, setor_id: 1 });
db.tickets.find({ setor_destino_id: setorTIId }, { _id: 1, titulo: 1, setor_destino_id: 1 });
db.categorias.find({ setor_relacionado_id: setorTIId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });

const gestorTI = db.setores.findOne({ _id: setorTIId }, { gestor_id: 1 });
db.usuarios.find({ _id: gestorTI.gestor_id }, { _id: 1, nome: 1 });

// 11.4 CATEGORIA
db.tickets.find({ categoria_id: categoriaManutencaoId }, { _id: 1, titulo: 1, categoria_id: 1 });
db.tickets.find({ categoria_id: categoriaAcessoId }, { _id: 1, titulo: 1, categoria_id: 1 });
db.categorias.find({ _id: categoriaManutencaoId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });
db.categorias.find({ _id: categoriaCompraId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });

// 11.5 STATUS
 db.tickets.find({ status_id: statusAbertoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusAtendimentoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusAguardandoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusConcluidoId }, { _id: 1, titulo: 1, status_id: 1 });

// 11.6 PAPEL
 db.usuarios.find({ papel_id: papelAdminId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.find({ papel_id: papelAtendenteId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.find({ papel_id: papelSolicitanteId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.aggregate([
   { $group: { _id: "$papel_id", quantidade: { $sum: 1 } } }
 ]);

// 11.7 COMENTARIO
 db.comentarios.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ autor_id: usuarioAnaId }, { _id: 1, autor_id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ ticket_id: ticket2Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ autor_id: usuarioCarlosId }, { _id: 1, autor_id: 1, ticket_id: 1, mensagem: 1 });

// 11.8 HISTORICO 
 db.historicos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
 db.historicos.find({ ticket_id: ticket4Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
 db.historicos.find({ autor_acao_id: usuarioAnaId }, { _id: 1, autor_acao_id: 1, ticket_id: 1, acao: 1 });
 db.historicos.find({ autor_acao_id: usuarioPedroId }, { _id: 1, autor_acao_id: 1, ticket_id: 1, acao: 1 });

// 11.9 ANEXO 
 db.anexos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket2Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket1Id, nome_arquivo: "erro-rede.png" }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket2Id, extensao: ".pdf" }, { _id: 1, ticket_id: 1, nome_arquivo: 1, extensao: 1 });

// 11.10 NOTIFICACAO
 db.notificacoes.find({ usuario_id: usuarioCarlosId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioMarianaId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioPedroId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioCarlosId, lida: false }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
/*
