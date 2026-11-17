package com.flowdesk.aplication.dto;

public record HistoricoResponse(
        String id,
        String acao,
        String valor_anterior,
        String valor_novo,
        java.time.LocalDateTime dataHora,
        String ticketID,
        String autorAcaoID,
        TicketResponse ticket,
        UsuarioResumo autorAcao
) {
}
