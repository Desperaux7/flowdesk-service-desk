/*
============================================================
10. IMPLEMENTAÇÃO DE EMBEDDED DOCUMENTS
============================================================ */
/* A estratégia principal do FlowDesk continua sendo References.
Os objetos abaixo são exemplos de dados fortemente ligados ao
documento-pai, armazenados dentro dele para demonstrar
Embedded Documents. */

db.usuarios.updateOne(
  { nome: "Carlos Silva" },
  {
    $set: {
      perfil_embedded: {
        contato: {
          telefone: "(75) 99999-1001",
          ramal: "101"
        },
        endereco: {
          rua: "Rua A",
          numero: 100,
          cidade: "Feira de Santana",
          estado: "BA"
        },
        preferencias: {
          idioma: "pt-BR",
          tema: "claro",
          notificacoes_email: true
        },
        acessibilidade: {
          alto_contraste: false,
          tamanho_fonte: "normal"
        }
      }
    }
  }
);

// 10.2 TICKET
db.tickets.updateOne(
  { titulo: "Computador sem acesso à rede" },
  {
    $set: {
      atendimento_embedded: {
        sla: {
          prazo_horas: 4,
          prioridade: "Alta"
        },
        checklist: [
          { item: "Verificar cabo de rede", concluido: true },
          { item: "Verificar configuração de IP", concluido: false }
        ],
        local_atendimento: {
          ambiente: "Setor Administrativo",
          andar: 1
        },
        avaliacao: {
          nota: 5,
          comentario: "Atendimento rápido"
        }
      }
    }
  }
);

// 10.3 SETOR
db.setores.updateOne(
  { nome: "Tecnologia da Informação" },
  {
    $set: {
      detalhes_embedded: {
        endereco: {
          bloco: "A",
          andar: 1,
          sala: "101"
        },
        contato: {
          ramal: "200",
          email: "ti@empresa.com"
        },
        horario_atendimento: {
          inicio: "08:00",
          fim: "18:00"
        },
        cobertura: {
          dias: ["segunda", "terca", "quarta", "quinta", "sexta"],
          plantao_fim_de_semana: false
        }
      }
    }
  }
);

// 10.4 CATEGORIA
db.categorias.updateOne(
  { nome: "Manutenção" },
  {
    $set: {
      configuracao_embedded: {
        sla_padrao: {
          prioridade_alta_horas: 4,
          prioridade_media_horas: 8
        },
        palavras_chave: {
          termos: ["computador", "rede", "impressora", "equipamento"]
        },
        regras_triagem: {
          exige_anexo: false,
          encaminhamento_automatico: true
        },
        formulario: {
          campos_obrigatorios: ["descricao", "prioridade"],
          permite_observacao: true
        }
      }
    }
  }
);

// 10.5 STATUS
db.status.updateOne(
  { nome: "Em Atendimento" },
  {
    $set: {
      configuracao_embedded: {
        kanban: {
          visivel: true,
          coluna: 2
        },
        transicoes: {
          anteriores: ["Aberto"],
          posteriores: ["Aguardando", "Concluído"]
        },
        alerta: {
          ativo: true,
          horas_sem_movimentacao: 24
        },
        metadados: {
          descricao: "Ticket sendo tratado por um atendente",
          encerramento_permitido: false
        }
      }
    }
  }
);

// 10.6 PAPEL
db.papeis.updateOne(
  { nome: "Atendente" },
  {
    $set: {
      configuracao_embedded: {
        escopo: {
          pode_atender_tickets: true,
          pode_gerenciar_usuarios: false
        },
        limites: {
          max_tickets_simultaneos: 20
        },
        interface: {
          menu_principal: ["Tickets", "Notificacoes"],
          dashboard: "atendimento"
        },
        auditoria: {
          registrar_acoes: true,
          nivel: "normal"
        }
      }
    }
  }
);

// 10.7 COMENTARIO
db.comentarios.updateOne(
  { mensagem: "O problema começou hoje pela manhã." },
  {
    $set: {
      dados_embedded: {
        autor_snapshot: {
          nome: "Carlos Silva",
          email: "carlos@empresa.com"
        },
        reacoes: {
          curtidas: 0,
          importantes: 0
        },
        edicao: {
          editado: false,
          quantidade: 0
        },
        contexto: {
          origem: "ticket",
          visibilidade: "interna"
        }
      }
    }
  }
);

// 10.8 HISTORICO
db.historicos.updateOne(
  { acao: "Mudança de Status", valor_novo: "Em Atendimento" },
  {
    $set: {
      dados_embedded: {
        mudanca: {
          campo: "status_id",
          tipo: "atualizacao"
        },
        autor_snapshot: {
          nome: "Ana Souza",
          papel: "Atendente"
        },
        origem: {
          tela: "Detalhes do Ticket",
          dispositivo: "desktop"
        },
        contexto: {
          motivo: "Início do atendimento",
          automatico: false
        }
      }
    }
  }
);

// 10.9 ANEXO
db.anexos.updateOne(
  { nome_arquivo: "erro-rede.png" },
  {
    $set: {
      metadados_embedded: {
        arquivo: {
          mime_type: "image/png",
          dimensoes: {
            largura: 1920,
            altura: 1080
          }
        },
        preview: {
          disponivel: true,
          tamanho: 40960
        },
        seguranca: {
          verificado: true,
          antivirus: "aprovado"
        },
        armazenamento: {
          provedor: "local",
          diretorio: "/uploads/tickets"
        }
      }
    }
  }
);

// 10.10 NOTIFICACAO
db.notificacoes.updateOne(
  { mensagem: "Seu ticket foi atualizado." },
  {
    $set: {
      entrega_embedded: {
        origem: {
          tipo: "ticket",
          evento: "atualizacao"
        },
        acao: {
          tela_destino: "Detalhes do Ticket",
          recurso_id: "ticket_relacionado"
        },
        canal: {
          app: true,
          email: true,
          push: false
        },
        preferencias: {
          permitir_lembrete: true,
          prioridade: "normal"
        }
      }
    }
  }
);

//Os Embedded Documents acima servem exclusivamente para demonstrar incorporação
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


