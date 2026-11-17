package com.flowdesk.aplication.dto;

import java.time.LocalDateTime;

public record ComentarioResponse(
        String id,
        String mensagem,
        LocalDateTime dataPublicacao,
        String ticketID,
        String autorID,
        TicketResponse ticket,
        UsuarioResumo autor
) {
}
