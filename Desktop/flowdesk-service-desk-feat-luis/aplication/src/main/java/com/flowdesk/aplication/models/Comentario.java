package com.flowdesk.aplication.models;

import java.time.LocalDateTime;

public class Comentario {

    private String mensagem;
    private LocalDateTime dataPublicacao;

    public Comentario(String mensagem, LocalDateTime dataPublicacao) {
        this.mensagem = mensagem;
        this.dataPublicacao = dataPublicacao;
    }

    public Comentario() {
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
}