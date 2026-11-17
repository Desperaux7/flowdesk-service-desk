package com.flowdesk.aplication.dto;

import com.flowdesk.aplication.models.Papel;
import com.flowdesk.aplication.models.Setor;

public record UsuarioResumo(
        String id,
        String nome,
        String email,
        String setorId,
        String papelId,
        Setor setor,
        Papel papel
) {
}
