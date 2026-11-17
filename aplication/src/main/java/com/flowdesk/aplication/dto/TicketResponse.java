package com.flowdesk.aplication.dto;

import com.flowdesk.aplication.models.Setor;
import com.flowdesk.aplication.models.Status;

import java.time.LocalDateTime;

public record TicketResponse(
        String id,
        String titulo,
        String descricao,
        String prioridade,
        LocalDateTime dataCriacao,
        LocalDateTime dataConclusao,
        String solicitanteID,
        String responsavelID,
        String categoriaID,
        String setorDestinoID,
        String statusID,
        UsuarioResumo solicitante,
        UsuarioResumo responsavel,
        CategoriaResponse categoria,
        Setor setorDestino,
        Status status
) {
}
