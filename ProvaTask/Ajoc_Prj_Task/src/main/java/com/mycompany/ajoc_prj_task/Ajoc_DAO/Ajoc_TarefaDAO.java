/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_DAO;


import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Tarefa;
import com.mycompany.ajoc_prj_task.Ajoc_util.Ajoc_ManipulaData;
import com.mycompany.ajoc_prj_task.Ajoc_util.Ajoc_conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Alexssander
 */
public class Ajoc_TarefaDAO {

    private final Connection Ajoc_conn;

    public Ajoc_TarefaDAO() {
        this.Ajoc_conn = Ajoc_conexao.Ajoc_getInstancia().Ajoc_conectar();
    }

    public void Ajoc_salvar(Ajoc_Tarefa Ajoc_t) throws SQLException {
        String Ajoc_sql = "INSERT INTO ajoc_tarefa (ajoc_id_tarefa, ajoc_nome, ajoc_descricao, ajoc_prioridade, ajoc_data_inicio, ajoc_data_fim, ajoc_id_projeto) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_t.getAjoc_id()); // agora UUID gerado no código
            Ajoc_ps.setString(2, Ajoc_t.getAjoc_titulo());
            Ajoc_ps.setString(3, Ajoc_t.getAjoc_descricao());
            Ajoc_ps.setString(4, Ajoc_t.getAjoc_prioridade());
            Ajoc_ps.setDate(5, Ajoc_t.getAjoc_data_inicio() != null
                    ? Ajoc_ManipulaData.getInstancia().string2Date(Ajoc_t.getAjoc_data_inicio()) : null);
            Ajoc_ps.setDate(6, Ajoc_t.getAjoc_data_fim() != null
                    ? Ajoc_ManipulaData.getInstancia().string2Date(Ajoc_t.getAjoc_data_fim()) : null);
            Ajoc_ps.setObject(7, Ajoc_t.getAjoc_Projeto() != null ? Ajoc_t.getAjoc_Projeto().getAjoc_id() : null);
            Ajoc_ps.executeUpdate();
        }
    }

    public void Ajoc_editar(Ajoc_Tarefa Ajoc_t) throws SQLException {
        String Ajoc_sql = "UPDATE ajoc_tarefa SET ajoc_nome=?, ajoc_descricao=?, ajoc_prioridade=?, ajoc_data_inicio=?, ajoc_data_fim=?, ajoc_id_projeto=? WHERE ajoc_id_tarefa=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setString(1, Ajoc_t.getAjoc_titulo());
            Ajoc_ps.setString(2, Ajoc_t.getAjoc_descricao());
            Ajoc_ps.setString(3, Ajoc_t.getAjoc_prioridade());
            Ajoc_ps.setDate(4, Ajoc_t.getAjoc_data_inicio() != null
                    ? Ajoc_ManipulaData.getInstancia().string2Date(Ajoc_t.getAjoc_data_inicio()) : null);
            Ajoc_ps.setDate(5, Ajoc_t.getAjoc_data_fim() != null
                    ? Ajoc_ManipulaData.getInstancia().string2Date(Ajoc_t.getAjoc_data_fim()) : null);
            Ajoc_ps.setObject(6, Ajoc_t.getAjoc_Projeto() != null ? Ajoc_t.getAjoc_Projeto().getAjoc_id() : null);
            Ajoc_ps.setObject(7, Ajoc_t.getAjoc_id()); 
            Ajoc_ps.executeUpdate();
        }
    }

    public void Ajoc_deletar(UUID Ajoc_id) throws SQLException {
        String Ajoc_sql = "DELETE FROM ajoc_tarefa WHERE ajoc_id_tarefa=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_id);
            Ajoc_ps.executeUpdate();
        }
    }

    public List<Ajoc_Tarefa> Ajoc_listar() throws SQLException {
        List<Ajoc_Tarefa> Ajoc_lista = new ArrayList<>();
        String Ajoc_sql = "SELECT t.*, p.* FROM ajoc_tarefa t LEFT JOIN ajoc_projeto p ON p.ajoc_id_projeto = t.ajoc_id_projeto";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql); ResultSet Ajoc_rs = Ajoc_ps.executeQuery()) {

            while (Ajoc_rs.next()) {
                Ajoc_Tarefa Ajoc_t = new Ajoc_Tarefa();
                Ajoc_t.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_tarefa"));
                Ajoc_t.setAjoc_titulo(Ajoc_rs.getString("ajoc_nome"));
                Ajoc_t.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));
                Ajoc_t.setAjoc_prioridade(Ajoc_rs.getString("ajoc_prioridade"));

                Timestamp Ajoc_inicio = Ajoc_rs.getTimestamp("ajoc_data_inicio");
                Timestamp Ajoc_fim = Ajoc_rs.getTimestamp("ajoc_data_fim");
                Ajoc_t.setAjoc_data_inicio(Ajoc_inicio != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_inicio.toString()) : null);
                Ajoc_t.setAjoc_data_fim(Ajoc_fim != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_fim.toString()) : null);

                // Preenche objeto projeto
                Ajoc_Projeto Ajoc_p = new Ajoc_Projeto();
                Ajoc_p.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_projeto"));
                Ajoc_p.setAjoc_nome(Ajoc_rs.getString("ajoc_nome"));
                Ajoc_p.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));

                Timestamp Ajoc_criacao = Ajoc_rs.getTimestamp("ajoc_data_criacao");
                Timestamp Ajoc_data_fim_proj = Ajoc_rs.getTimestamp("ajoc_data_fim");
                Ajoc_p.setAjoc_data_criacao(Ajoc_criacao != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_criacao.toString()) : null);
                Ajoc_p.setAjoc_data_fim(Ajoc_data_fim_proj != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_data_fim_proj.toString()) : null);

                Ajoc_t.setAjoc_Projeto(Ajoc_p);

                Ajoc_lista.add(Ajoc_t);
            }
        }
        return Ajoc_lista;
    }

    public Ajoc_Tarefa Ajoc_buscarPorId(UUID Ajoc_id) throws SQLException {
        String Ajoc_sql = "SELECT t.*, p.* FROM ajoc_tarefa t LEFT JOIN ajoc_projeto p ON p.ajoc_id_projeto = t.ajoc_id_projeto WHERE t.ajoc_id_tarefa=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_id);
            try (ResultSet Ajoc_rs = Ajoc_ps.executeQuery()) {
                if (Ajoc_rs.next()) {
                    Ajoc_Tarefa Ajoc_t = new Ajoc_Tarefa();
                    Ajoc_t.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_tarefa"));
                    Ajoc_t.setAjoc_titulo(Ajoc_rs.getString("ajoc_nome"));
                    Ajoc_t.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));
                    Ajoc_t.setAjoc_prioridade(Ajoc_rs.getString("ajoc_prioridade"));

                    Timestamp Ajoc_inicio = Ajoc_rs.getTimestamp("ajoc_data_inicio");
                    Timestamp Ajoc_fim = Ajoc_rs.getTimestamp("ajoc_data_fim");
                    Ajoc_t.setAjoc_data_inicio(Ajoc_inicio != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_inicio.toString()) : null);
                    Ajoc_t.setAjoc_data_fim(Ajoc_fim != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_fim.toString()) : null);

                    // Projeto associado
                    Ajoc_Projeto Ajoc_p = new Ajoc_Projeto();
                    Ajoc_p.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_projeto"));
                    Ajoc_p.setAjoc_nome(Ajoc_rs.getString("ajoc_nome"));
                    Ajoc_p.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));

                    Timestamp Ajoc_criacao = Ajoc_rs.getTimestamp("ajoc_data_criacao");
                    Timestamp Ajoc_data_fim_proj = Ajoc_rs.getTimestamp("ajoc_data_fim");
                    Ajoc_p.setAjoc_data_criacao(Ajoc_criacao != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_criacao.toString()) : null);
                    Ajoc_p.setAjoc_data_fim(Ajoc_data_fim_proj != null ? Ajoc_ManipulaData.getInstancia().date2String(Ajoc_data_fim_proj.toString()) : null);

                    Ajoc_t.setAjoc_Projeto(Ajoc_p);

                    return Ajoc_t;
                }
            }
        }
        return null;
    }

    public void sincronizarArquivoComBanco() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
