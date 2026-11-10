package com.flowdesk.aplication.models;

import java.time.LocalDateTime;

public class Historico {

    private String acao;
    private String valor_anterior;
    private String valor_novo;
    private LocalDateTime dataHora;

    public Historico(String acao, String valor_anterior, String valor_novo, LocalDateTime dataHora) {
        this.acao = acao;
        this.valor_anterior = valor_anterior;
        this.valor_novo = valor_novo;
        this.dataHora = dataHora;
    }

    public Historico() {
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
}