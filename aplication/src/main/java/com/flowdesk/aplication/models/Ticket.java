package com.flowdesk.aplication.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "ticket")
public class Ticket {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    @Field(targetType = FieldType.OBJECT_ID)
    private String solicitanteID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String responsavelID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String categoriaID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String setorDestinoID;
    @Field(targetType = FieldType.OBJECT_ID)
    private String statusID;

    public Ticket(String id, String titulo, String descricao, String prioridade, LocalDateTime dataConclusao, LocalDateTime dataCriacao){
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public Ticket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getSolicitanteID() {
        return solicitanteID;
    }

    public void setSolicitanteID(String solicitanteID) {
        this.solicitanteID = solicitanteID;
    }

    public String getResponsavelID() {
        return responsavelID;
    }

    public void setResponsavelID(String responsavelID) {
        this.responsavelID = responsavelID;
    }

    public String getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }

    public String getSetorDestinoID() {
        return setorDestinoID;
    }

    public void setSetorDestinoID(String setorDestinoID) {
        this.setorDestinoID = setorDestinoID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }
}
