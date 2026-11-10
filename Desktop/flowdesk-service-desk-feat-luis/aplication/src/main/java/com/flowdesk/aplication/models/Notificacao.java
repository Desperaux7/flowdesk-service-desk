package com.flowdesk.aplication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notificacao")
public class Notificacao {

    @Id
    private String id;
    private String mensagem;
    private Boolean lida;
    private LocalDateTime dataEnvio;

    public Notificacao(String id, String mensagem, Boolean lida, LocalDateTime dataEnvio) {
        this.id = id;
        this.mensagem = mensagem;
        this.lida = lida;
        this.dataEnvio = dataEnvio;
    }

    public Notificacao() {
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

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
