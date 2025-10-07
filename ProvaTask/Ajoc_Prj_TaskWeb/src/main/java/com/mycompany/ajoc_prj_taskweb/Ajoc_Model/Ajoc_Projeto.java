/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.Ajoc_Model;

import java.time.LocalDateTime;

/**
 *
 * @author Alexssander
 */
import java.time.LocalDate;
import java.util.UUID;

public class Ajoc_Projeto {

    private UUID ajoc_id;
    private String ajoc_nome;
    private String ajoc_descricao;
    private String ajoc_data_criacao; 
    private String ajoc_data_fim;     

    public Ajoc_Projeto() {
    }

    public Ajoc_Projeto(UUID Ajoc_id, String Ajoc_nome, String Ajoc_descricao,
            String Ajoc_data_criacao, String Ajoc_data_fim) {
        this.ajoc_id = Ajoc_id;
        this.ajoc_nome = Ajoc_nome;
        this.ajoc_descricao = Ajoc_descricao;
        this.ajoc_data_criacao = Ajoc_data_criacao;
        this.ajoc_data_fim = Ajoc_data_fim;
    }

    public UUID getAjoc_id() {
        return ajoc_id;
    }

    public void setAjoc_id(UUID Ajoc_id) {
        this.ajoc_id = Ajoc_id;
    }

    public String getAjoc_nome() {
        return ajoc_nome;
    }

    public void setAjoc_nome(String Ajoc_nome) {
        this.ajoc_nome = Ajoc_nome;
    }

    public String getAjoc_descricao() {
        return ajoc_descricao;
    }

    public void setAjoc_descricao(String Ajoc_descricao) {
        this.ajoc_descricao = Ajoc_descricao;
    }

    public String getAjoc_data_criacao() {
        return ajoc_data_criacao;
    }

    public void setAjoc_data_criacao(String Ajoc_data_criacao) {
        this.ajoc_data_criacao = Ajoc_data_criacao;
    }

    public String getAjoc_data_fim() {
        return ajoc_data_fim;
    }

    public void setAjoc_data_fim(String Ajoc_data_fim) {
        this.ajoc_data_fim = Ajoc_data_fim;
    }
}
