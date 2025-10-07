/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.Ajoc_Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Alexssander
 */
public class Ajoc_Tarefa {

    private UUID ajoc_id;
    private String ajoc_titulo;
    private String ajoc_descricao;
    private String ajoc_prioridade;
    private String ajoc_data_inicio;
    private String ajoc_data_fim;
    private Ajoc_Projeto ajoc_Projeto;

    public Ajoc_Tarefa() {
    }

    public Ajoc_Tarefa(UUID Ajoc_id, String Ajoc_titulo, String Ajoc_descricao, String Ajoc_prioridade,
            String Ajoc_data_inicio, String Ajoc_data_fim, Ajoc_Projeto ajoc_Projeto) {
        this.ajoc_id = Ajoc_id;
        this.ajoc_titulo = Ajoc_titulo;
        this.ajoc_descricao = Ajoc_descricao;
        this.ajoc_prioridade = Ajoc_prioridade;
        this.ajoc_data_inicio = Ajoc_data_inicio;
        this.ajoc_data_fim = Ajoc_data_fim;
        this.ajoc_Projeto = ajoc_Projeto;
    }

    public UUID getAjoc_id() {
        return ajoc_id;
    }

    public void setAjoc_id(UUID Ajoc_id) {
        this.ajoc_id = Ajoc_id;
    }

    public String getAjoc_titulo() {
        return ajoc_titulo;
    }

    public void setAjoc_titulo(String Ajoc_nome) {
        this.ajoc_titulo = Ajoc_nome;
    }

    public String getAjoc_descricao() {
        return ajoc_descricao;
    }

    public void setAjoc_descricao(String Ajoc_descricao) {
        this.ajoc_descricao = Ajoc_descricao;
    }

    public String getAjoc_prioridade() {
        return ajoc_prioridade;
    }

    public void setAjoc_prioridade(String Ajoc_prioridade) {
        this.ajoc_prioridade = Ajoc_prioridade;
    }

    public String getAjoc_data_inicio() {
        return ajoc_data_inicio;
    }

    public void setAjoc_data_inicio(String Ajoc_data_inicio) {
        this.ajoc_data_inicio = Ajoc_data_inicio;
    }

    public String getAjoc_data_fim() {
        return ajoc_data_fim;
    }

    public void setAjoc_data_fim(String Ajoc_data_fim) {
        this.ajoc_data_fim = Ajoc_data_fim;
    }

    public Ajoc_Projeto getAjoc_Projeto() {
        return ajoc_Projeto;
    }

    public void setAjoc_Projeto(Ajoc_Projeto ajoc_Projeto) {
        this.ajoc_Projeto = ajoc_Projeto;
    }
}
