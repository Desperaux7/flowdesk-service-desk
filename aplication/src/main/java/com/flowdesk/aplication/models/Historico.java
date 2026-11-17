package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "historico")
public class Historico {

    @Id
    private String id;
    private String acao;
    private String valor_anterior;
    private String valor_novo;
    private LocalDateTime dataHora;
    @Field(targetType = FieldType.OBJECT_ID)
    private String ticketID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String autorAcaoID;

    public Historico(String id, String acao, String valor_anterior, String valor_novo, LocalDateTime dataHora) {
        this.id = id;
        this.acao = acao;
        this.valor_anterior = valor_anterior;
        this.valor_novo = valor_novo;
        this.dataHora = dataHora;
    }

    public Historico() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getValor_anterior() {
        return valor_anterior;
    }

    public void setValor_anterior(String valor_anterior) {
        this.valor_anterior = valor_anterior;
    }

    public String getValor_novo() {
        return valor_novo;
    }

    public void setValor_novo(String valor_novo) {
        this.valor_novo = valor_novo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getAutorAcaoID() {
        return autorAcaoID;
    }

    public void setAutorAcaoID(String autorAcaoID) {
        this.autorAcaoID = autorAcaoID;
    }
}
