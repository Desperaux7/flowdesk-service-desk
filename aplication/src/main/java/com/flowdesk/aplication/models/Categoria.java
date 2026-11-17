package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "categoria")
public class Categoria {

    @Id
    private String id;
    private String nome;
    private String descricao;
    @Field(targetType = FieldType.OBJECT_ID)
    private String setorRelacionadoID;

    public Categoria(String id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Categoria() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSetorRelacionadoID() {
        return setorRelacionadoID;
    }

    public void setSetorRelacionadoID(String setorRelacionadoID) {
        this.setorRelacionadoID = setorRelacionadoID;
    }
}
