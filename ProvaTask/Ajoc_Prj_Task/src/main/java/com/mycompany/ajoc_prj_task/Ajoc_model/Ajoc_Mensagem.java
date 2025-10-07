/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_model;

import java.util.Map;

/**
 *
 * @author Alexssander
 */
public class Ajoc_Mensagem {
    private String acao;   // "adicionar" ou "listar"
    private String tabela; // nome da tabela
    private Map<String, String> dados; // chave/valor

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getTabela() { return tabela; }
    public void setTabela(String tabela) { this.tabela = tabela; }

    public Map<String, String> getDados() { return dados; }
    public void setDados(Map<String, String> dados) { this.dados = dados; }
}
