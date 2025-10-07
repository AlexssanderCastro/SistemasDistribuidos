/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_cliente_task.Ajoc_util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_Model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_Model.Ajoc_Tarefa;
import java.util.UUID;

/**
 *
 * @author Alexssander
 */
public class Ajoc_parseJson {

    private static final Gson gson = new GsonBuilder().create();

    public static Ajoc_Projeto ajoc_parseProjeto(String json) {
        System.out.println(json);
        try {

            JsonObject dados = gson.fromJson(json, JsonObject.class);
            Ajoc_Projeto projeto = new Ajoc_Projeto();
            System.out.println(dados);
            if (dados.has("ajoc_id_projeto")) {
                projeto.setAjoc_id(UUID.fromString(dados.get("ajoc_id_projeto").getAsString()));
            }

            if (dados.has("ajoc_nome")) {
                projeto.setAjoc_nome(dados.get("ajoc_nome").getAsString());
            }
            if (dados.has("ajoc_descricao")) {
                projeto.setAjoc_descricao(dados.get("ajoc_descricao").getAsString());
            }
            if (dados.has("ajoc_data_criacao")) {
                projeto.setAjoc_data_criacao(dados.get("ajoc_data_criacao").getAsString());
            }
            if (dados.has("ajoc_data_fim")) {
                projeto.setAjoc_data_fim(dados.get("ajoc_data_fim").getAsString());
            }
            System.out.println("Projeto:");
            System.out.println("  ID: " + projeto.getAjoc_id());
            System.out.println("  Nome: " + projeto.getAjoc_nome());
            System.out.println("  Descrição: " + projeto.getAjoc_descricao());
            System.out.println("  Data de Criação: " + projeto.getAjoc_data_criacao());
            System.out.println("  Data de Fim: " + projeto.getAjoc_data_fim());

            return projeto;

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converte JSON em objeto Ajoc_Tarefa
     */
    public static Ajoc_Tarefa ajoc_parseTarefa(String json) {
        try {
            JsonObject dados = gson.fromJson(json, JsonObject.class);
           

            Ajoc_Tarefa tarefa = new Ajoc_Tarefa();

            // UUID da tarefa
            if (dados.has("ajoc_id")) {
                tarefa.setAjoc_id(UUID.fromString(dados.get("ajoc_id").getAsString()));
            }

            // Campos restantes
            if (dados.has("ajoc_nome")) {
                tarefa.setAjoc_titulo(dados.get("ajoc_nome").getAsString());
            }
            if (dados.has("ajoc_descricao")) {
                tarefa.setAjoc_descricao(dados.get("ajoc_descricao").getAsString());
            }
            if (dados.has("ajoc_prioridade")) {
                tarefa.setAjoc_prioridade(dados.get("ajoc_prioridade").getAsString());
            }
            if (dados.has("ajoc_data_inicio")) {
                tarefa.setAjoc_data_inicio(dados.get("ajoc_data_inicio").getAsString());
            }
            if (dados.has("ajoc_data_fim")) {
                tarefa.setAjoc_data_fim(dados.get("ajoc_data_fim").getAsString());
            }

            // Projeto associado (opcional)
            if (dados.has("ajoc_id_projeto")) {
                Ajoc_Projeto ajoc_proj = new Ajoc_Projeto();
                ajoc_proj.setAjoc_id(UUID.fromString(dados.get("ajoc_id_projeto").getAsString()));
                tarefa.setAjoc_Projeto(ajoc_proj);
            }

            return tarefa;

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
