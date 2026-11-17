package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "comentario")
public class Comentario {

    @Id
    private String id;
    private String mensagem;
    private LocalDateTime dataPublicacao;
    @Field(targetType = FieldType.OBJECT_ID)
    private String ticketID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String autorID;

    public Comentario(String id, String mensagem, LocalDateTime dataPublicacao) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataPublicacao = dataPublicacao;
    }

    public Comentario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getAutorID() {
        return autorID;
    }

    public void setAutorID(String autorID) {
        this.autorID = autorID;
    }
}
