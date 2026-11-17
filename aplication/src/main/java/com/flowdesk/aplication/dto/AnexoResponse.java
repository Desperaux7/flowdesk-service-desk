package com.flowdesk.aplication.dto;

public record AnexoResponse(
        String id,
        String nome_arquivo,
        String url_caminho,
        String extensao,
        Integer tamanho,
        String ticketID,
        TicketResponse ticket
) {
}
