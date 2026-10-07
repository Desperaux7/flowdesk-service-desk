/*
============================================================
11. REFERENCES
============================================================ */

// 1) setor_id -> setores
 db.usuarios.find({ setor_id: setorTIId }, { _id: 1, nome: 1, setor_id: 1 });
// 2) papel_id -> papeis
 db.usuarios.find({ papel_id: papelAdminId }, { _id: 1, nome: 1, papel_id: 1 });
// 3) autor_id -> comentarios
 db.comentarios.find({ autor_id: usuarioCarlosId }, { _id: 1, ticket_id: 1, autor_id: 1, mensagem: 1 });
// 4) usuario_id -> notificacoes
 db.notificacoes.find({ usuario_id: usuarioCarlosId }, { _id: 1, usuario_id: 1, mensagem: 1 });

// 11.2 TICKET - 4 exemplos
// 1) ticket_id -> comentarios
 db.comentarios.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
// 2) ticket_id -> historicos
 db.historicos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
// 3) ticket_id -> anexos
 db.anexos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
// 4) ticket com suas references do arquivo de relationships
 db.tickets.find({ _id: ticket1Id }, { _id: 1, titulo: 1, solicitante_id: 1, responsavel_id: 1, setor_destino_id: 1, categoria_id: 1, status_id: 1 });

// 11.3 SETOR - 4 exemplos
// 1) setor_id -> usuarios
 db.usuarios.find({ setor_id: setorTIId }, { _id: 1, nome: 1, setor_id: 1 });
// 2) setor_destino_id -> tickets
 db.tickets.find({ setor_destino_id: setorTIId }, { _id: 1, titulo: 1, setor_destino_id: 1 });
// 3) setor_relacionado_id -> categorias
 db.categorias.find({ setor_relacionado_id: setorTIId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });
// 4) gestor_id -> usuarios
 const gestorTI = db.setores.findOne({ _id: setorTIId }, { gestor_id: 1 });
 db.usuarios.find({ _id: gestorTI.gestor_id }, { _id: 1, nome: 1 });

// 11.4 CATEGORIA - 4 exemplos
// 1) categoria_id -> tickets (Manutenção)
 db.tickets.find({ categoria_id: categoriaManutencaoId }, { _id: 1, titulo: 1, categoria_id: 1 });
// 2) categoria_id -> tickets (Acesso)
 db.tickets.find({ categoria_id: categoriaAcessoId }, { _id: 1, titulo: 1, categoria_id: 1 });
// 3) setor_relacionado_id -> setor (Manutenção)
 db.categorias.find({ _id: categoriaManutencaoId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });
// 4) setor_relacionado_id -> setor (Compra)
 db.categorias.find({ _id: categoriaCompraId }, { _id: 1, nome: 1, setor_relacionado_id: 1 });

// 11.5 STATUS - 4 exemplos
 db.tickets.find({ status_id: statusAbertoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusAtendimentoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusAguardandoId }, { _id: 1, titulo: 1, status_id: 1 });
 db.tickets.find({ status_id: statusConcluidoId }, { _id: 1, titulo: 1, status_id: 1 });

// 11.6 PAPEL - 4 exemplos
 db.usuarios.find({ papel_id: papelAdminId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.find({ papel_id: papelAtendenteId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.find({ papel_id: papelSolicitanteId }, { _id: 1, nome: 1, papel_id: 1 });
 db.usuarios.aggregate([
   { $group: { _id: "$papel_id", quantidade: { $sum: 1 } } }
 ]);

// 11.7 COMENTARIO - 4 exemplos
 db.comentarios.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ autor_id: usuarioAnaId }, { _id: 1, autor_id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ ticket_id: ticket2Id }, { _id: 1, ticket_id: 1, mensagem: 1 });
 db.comentarios.find({ autor_id: usuarioCarlosId }, { _id: 1, autor_id: 1, ticket_id: 1, mensagem: 1 });

// 11.8 HISTORICO - 4 exemplos
 db.historicos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
 db.historicos.find({ ticket_id: ticket4Id }, { _id: 1, ticket_id: 1, acao: 1, valor_novo: 1 });
 db.historicos.find({ autor_acao_id: usuarioAnaId }, { _id: 1, autor_acao_id: 1, ticket_id: 1, acao: 1 });
 db.historicos.find({ autor_acao_id: usuarioPedroId }, { _id: 1, autor_acao_id: 1, ticket_id: 1, acao: 1 });

// 11.9 ANEXO - 4 exemplos
 db.anexos.find({ ticket_id: ticket1Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket2Id }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket1Id, nome_arquivo: "erro-rede.png" }, { _id: 1, ticket_id: 1, nome_arquivo: 1 });
 db.anexos.find({ ticket_id: ticket2Id, extensao: ".pdf" }, { _id: 1, ticket_id: 1, nome_arquivo: 1, extensao: 1 });

// 11.10 NOTIFICACAO - 4 exemplos
 db.notificacoes.find({ usuario_id: usuarioCarlosId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioMarianaId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioPedroId }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
 db.notificacoes.find({ usuario_id: usuarioCarlosId, lida: false }, { _id: 1, usuario_id: 1, mensagem: 1, lida: 1 });
/*


