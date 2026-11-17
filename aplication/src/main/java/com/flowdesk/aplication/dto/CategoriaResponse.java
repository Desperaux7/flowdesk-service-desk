package com.flowdesk.aplication.dto;

import com.flowdesk.aplication.models.Setor;

public record CategoriaResponse(
        String id,
        String nome,
        String descricao,
        String setorRelacionadoID,
        Setor setorRelacionado
) {
}
