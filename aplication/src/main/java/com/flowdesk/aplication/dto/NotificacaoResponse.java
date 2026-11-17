package com.flowdesk.aplication.dto;

import java.time.LocalDateTime;

public record NotificacaoResponse(
        String id,
        String mensagem,
        Boolean lida,
        LocalDateTime dataEnvio,
        String usuarioID,
        UsuarioResumo usuario
) {
}
