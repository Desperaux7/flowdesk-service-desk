package com.flowdesk.aplication.common;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class ReferenceIds {
    private ReferenceIds() {
    }

    public static String require(String id, String label) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, label + " é obrigatório");
        }
        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de " + label + " inválido");
        }
        return id;
    }

    public static String optional(String id, String label) {
        return id == null || id.isBlank() ? null : require(id, label);
    }
}
