/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.ajoc_prj_task.Ajoc_Forms.Ajoc_TelaInicial;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Tarefa;
import com.mycompany.ajoc_prj_task.Ajoc_util.Ajoc_parseJson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author Alexssander
 */
public class Ajoc_ArquivoHandler {

    private final String ajoc_nomeArquivo = "dados/ajoc_dados.txt";

    public synchronized void salvar(String tabela, Map<String, String> dados) {
        List<String> linhas = Ajoc_lerArquivo();

        String json = Ajoc_formatarJson(dados);
        boolean tabelaEncontrada = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ajoc_nomeArquivo))) {
            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                writer.write(linha);
                writer.newLine();

                if (linha.equals("[TABELA:" + tabela + "]")) {
                    tabelaEncontrada = true;

                    int j = i + 1;
                    while (j < linhas.size() && !linhas.get(j).startsWith("[TABELA:")) {
                        j++;
                    }

                    for (int k = i + 1; k < j; k++) {
                        writer.write(linhas.get(k));
                        writer.newLine();
                    }

                    writer.write(json);
                    writer.newLine();

                    for (int k = j; k < linhas.size(); k++) {
                        writer.write(linhas.get(k));
                        writer.newLine();
                    }
                    if (Ajoc_TelaInicial.ajoc_isMatriz) {
                        if (tabela.equals("projetos")) {
                            Ajoc_ProjetoDAO ajoc_dao = new Ajoc_ProjetoDAO();
                            try {
                                ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseProjeto(json));
                            } catch (SQLException ex) {
                                System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                            }
                        } else {
                            Ajoc_TarefaDAO ajoc_dao = new Ajoc_TarefaDAO();
                            try {
                                ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseTarefa(json));
                            } catch (SQLException ex) {
                                System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                            }
                        }
                    }
                    return;
                }
            }

            if (!tabelaEncontrada) {
                writer.write("[TABELA:" + tabela + "]");
                writer.newLine();
                writer.write(json);
                writer.newLine();
                if (Ajoc_TelaInicial.ajoc_isMatriz) {
                    if (tabela.equals("projetos")) {
                        Ajoc_ProjetoDAO ajoc_dao = new Ajoc_ProjetoDAO();
                        try {
                            ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseProjeto(json));
                        } catch (SQLException ex) {
                            System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
                    } else {
                        Ajoc_TarefaDAO ajoc_dao = new Ajoc_TarefaDAO();
                        try {
                            ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseTarefa(json));
                        } catch (SQLException ex) {
                            System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lê todas as entradas de uma tabela específica.
     */
    public synchronized String Ajoc_lerTodos(String tabela) {
        List<String> linhas = Ajoc_lerArquivo();
        List<String> registros = new ArrayList<>();

        boolean naTabela = false;
        for (String linha : linhas) {
            if (linha.startsWith("[TABELA:")) {
                naTabela = linha.equals("[TABELA:" + tabela + "]");
                continue;
            }
            if (naTabela && !linha.trim().isEmpty()) {
                registros.add(linha);
            }
        }

        if (registros.isEmpty()) {
            return "[]";
        }

        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (String reg : registros) {
            joiner.add(reg);
        }
        return joiner.toString();
    }

    private List<String> Ajoc_lerArquivo() {
        List<String> linhas = new ArrayList<>();
        File file = new File(ajoc_nomeArquivo);
        if (!file.exists()) {
            return linhas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    private String Ajoc_formatarJson(Map<String, String> dados) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : dados.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":\"")
                    .append(entry.getValue()).append("\"");
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    public void sincronizarArquivoComBanco(List<Ajoc_Projeto> ajoc_lista_projetos, List<Ajoc_Tarefa> ajoc_lista_tarefas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ajoc_nomeArquivo, false))) {

            writer.write("[TABELA:projetos]");
            writer.newLine();

            for (Ajoc_Projeto p : ajoc_lista_projetos) {
                Map<String, String> mapProjeto = new LinkedHashMap<>();
                mapProjeto.put("ajoc_id_projeto", p.getAjoc_id().toString());
                mapProjeto.put("ajoc_nome", p.getAjoc_nome());
                mapProjeto.put("ajoc_descricao", p.getAjoc_descricao());
                mapProjeto.put("ajoc_data_criacao", p.getAjoc_data_criacao());
                mapProjeto.put("ajoc_data_fim", p.getAjoc_data_fim());

                String json = Ajoc_formatarJson(mapProjeto);
                writer.write(json);
                writer.newLine();
            }

            writer.newLine();

            writer.write("[TABELA:tarefas]");
            writer.newLine();

            for (Ajoc_Tarefa t : ajoc_lista_tarefas) {
                Map<String, String> mapTarefa = new LinkedHashMap<>();
                mapTarefa.put("ajoc_id_tarefa", t.getAjoc_id().toString());
                mapTarefa.put("ajoc_nome", t.getAjoc_titulo());
                mapTarefa.put("ajoc_descricao", t.getAjoc_descricao());
                mapTarefa.put("ajoc_prioridade", t.getAjoc_prioridade());
                mapTarefa.put("ajoc_data_inicio", t.getAjoc_data_inicio());
                mapTarefa.put("ajoc_data_fim", t.getAjoc_data_fim());

                if (t.getAjoc_Projeto() != null && t.getAjoc_Projeto().getAjoc_id() != null) {
                    mapTarefa.put("ajoc_id_projeto", t.getAjoc_Projeto().getAjoc_id().toString());
                }

                String json = Ajoc_formatarJson(mapTarefa);
                writer.write(json);
                writer.newLine();
            }

            writer.flush();
            System.out.println("[SYNC] Arquivo sincronizado com sucesso: " + ajoc_nomeArquivo);

        } catch (IOException e) {
            System.err.println("[SYNC] Erro ao reescrever arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Ajoc_enviarSyncRegistroPorRegistro() {
        if (!Ajoc_TelaInicial.ajoc_isCon) {
            return;
        }

        Ajoc_ArquivoHandler ajocHandler = new Ajoc_ArquivoHandler();

        String ajoc_listaProjetosJson = ajocHandler.Ajoc_lerTodos("projetos");
        List<Map<String, String>> ajoc_listaProjetos = new Gson().fromJson(ajoc_listaProjetosJson, new TypeToken<List<Map<String, String>>>() {
        }.getType());

        for (Map<String, String> ajoc_projeto : ajoc_listaProjetos) {
            try {
                Ajoc_TelaInicial.ajoc_server.ajoc_enviarMensagem("sync", "projetos", ajoc_projeto);
                System.out.println("[SYNC] Projeto enviado: " + ajoc_projeto);
            } catch (IOException ajoc_e) {
                System.err.println("[SYNC] Erro ao enviar projeto: " + ajoc_e.getMessage());
            }
        }

        String ajoc_listaTarefasJson = ajocHandler.Ajoc_lerTodos("tarefas");
        List<Map<String, String>> ajoc_listaTarefas = new Gson().fromJson(ajoc_listaTarefasJson, new TypeToken<List<Map<String, String>>>() {
        }.getType());

        for (Map<String, String> ajoc_tarefa : ajoc_listaTarefas) {
            try {
                Ajoc_TelaInicial.ajoc_server.ajoc_enviarMensagem("sync", "tarefas", ajoc_tarefa);
                System.out.println("[SYNC] Tarefa enviada: " + ajoc_tarefa);
            } catch (IOException ajoc_e) {
                System.err.println("[SYNC] Erro ao enviar tarefa: " + ajoc_e.getMessage());
            }
        }
    }

    public void sincronizarBanco() throws SQLException {
        Ajoc_ProjetoDAO ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        Ajoc_TarefaDAO ajoc_tarefaDAO = new Ajoc_TarefaDAO();
        List<Ajoc_Projeto> ajoc_lista_projetos = ajoc_projetoDAO.Ajoc_listar();
        List<Ajoc_Tarefa> ajoc_lista_tarefas = ajoc_tarefaDAO.Ajoc_listar();
        this.sincronizarArquivoComBanco(ajoc_lista_projetos, ajoc_lista_tarefas);
    }

    public synchronized void Ajoc_salvarSync(String ajoc_tabela, Map<String, String> dados) {
        List<String> ajoc_linhas = Ajoc_lerArquivo();
        List<String> ajoc_novasLinhas = new ArrayList<>();
        boolean ajoc_tabelaEncontrada = false;
        boolean ajoc_registroAtualizado = false;

        String ajoc_idNovo = ajoc_tabela.equals("projetos")
                ? dados.get("ajoc_id_projeto")
                : dados.get("ajoc_id_tarefa");
        String ajoc_jsonNovo = Ajoc_formatarJson(dados);

        String ajoc_tabelaAtual = null;

        for (int ajoc_i = 0; ajoc_i < ajoc_linhas.size(); ajoc_i++) {
            String ajoc_linha = ajoc_linhas.get(ajoc_i);

            if (ajoc_linha.startsWith("[TABELA:")) {
                ajoc_tabelaAtual = ajoc_linha.substring(8, ajoc_linha.length() - 1);
                ajoc_novasLinhas.add(ajoc_linha);
                if (ajoc_tabelaAtual.equals(ajoc_tabela)) {
                    ajoc_tabelaEncontrada = true;
                }
                continue;
            }

            if (ajoc_tabelaAtual != null && ajoc_tabelaAtual.equals(ajoc_tabela)) {
                String ajoc_idExistente = Ajoc_parseJson.Ajoc_extrairId(ajoc_linha);

                if (ajoc_idExistente != null && ajoc_idExistente.equals(ajoc_idNovo)) {
                    if (!Ajoc_TelaInicial.ajoc_isMatriz) {
                        ajoc_novasLinhas.add(ajoc_jsonNovo);
                    } else {
                        ajoc_novasLinhas.add(ajoc_linha);
                    }
                    ajoc_registroAtualizado = true;
                } else {
                    ajoc_novasLinhas.add(ajoc_linha);
                }
            } else {
                ajoc_novasLinhas.add(ajoc_linha);
            }
        }

        if (!ajoc_tabelaEncontrada) {
            ajoc_novasLinhas.add("[TABELA:" + ajoc_tabela + "]");
        }

        if (!ajoc_registroAtualizado) {
            ajoc_novasLinhas.add(ajoc_jsonNovo);
            if (Ajoc_TelaInicial.ajoc_isMatriz) {
                if (ajoc_tabela.equals("projetos")) {
                    Ajoc_ProjetoDAO ajoc_dao = new Ajoc_ProjetoDAO();
                    try {
                        ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseProjeto(ajoc_jsonNovo));
                    } catch (SQLException ex) {
                        System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                } else {
                    Ajoc_TarefaDAO ajoc_dao = new Ajoc_TarefaDAO();
                    try {
                        ajoc_dao.Ajoc_salvar(Ajoc_parseJson.Ajoc_parseTarefa(ajoc_jsonNovo));
                    } catch (SQLException ex) {
                        System.getLogger(Ajoc_ArquivoHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            }
        }

        try (BufferedWriter ajoc_writer = new BufferedWriter(new FileWriter(this.ajoc_nomeArquivo))) {
            for (String l : ajoc_novasLinhas) {
                ajoc_writer.write(l);
                ajoc_writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
