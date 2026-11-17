package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "anexo")
public class Anexo {

    @Id
    private String id;
    private String nome_arquivo;
    private String url_caminho;
    private String extensao;
    private Integer tamanho;
    @Field(targetType = FieldType.OBJECT_ID)
    private String ticketID;

    public Anexo(String id, String nome_arquivo, String url_caminho, String extensao, Integer tamanho) {
        this.id = id;
        this.nome_arquivo = nome_arquivo;
        this.url_caminho = url_caminho;
        this.extensao = extensao;
        this.tamanho = tamanho;
    }

    public Anexo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome_arquivo() {
        return nome_arquivo;
    }

    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
    }

    public String getUrl_caminho() {
        return url_caminho;
    }

    public void setUrl_caminho(String url_caminho) {
        this.url_caminho = url_caminho;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
