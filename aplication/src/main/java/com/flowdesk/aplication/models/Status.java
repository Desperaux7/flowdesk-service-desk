package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status")
public class Status {

    @Id
    private String id;
    private String nome;
    private String cor_kanban;
    private Integer ordem;

    public Status(String id, String nome, String cor_kanban, Integer ordem) {
        this.id = id;
        this.nome = nome;
        this.cor_kanban = cor_kanban;
        this.ordem = ordem;
    }

    public Status() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor_kanban() {
        return cor_kanban;
    }

    public void setCor_kanban(String cor_kanban) {
        this.cor_kanban = cor_kanban;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
