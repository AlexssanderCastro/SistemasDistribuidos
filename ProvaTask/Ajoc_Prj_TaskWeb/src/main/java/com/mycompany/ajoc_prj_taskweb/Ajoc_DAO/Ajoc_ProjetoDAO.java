/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.Ajoc_DAO;

import com.mycompany.ajoc_prj_taskweb.Ajoc_Model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_taskweb.Ajoc_util.Ajoc_ManipulaData;
import com.mycompany.ajoc_prj_taskweb.Ajoc_util.Ajoc_conexao;
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
public class Ajoc_ProjetoDAO {

    private Connection Ajoc_conn;

    public Ajoc_ProjetoDAO() {
        this.Ajoc_conn = Ajoc_conexao.Ajoc_getInstancia().Ajoc_conectar();
    }

    public void Ajoc_salvar(Ajoc_Projeto Ajoc_p) throws SQLException {
        String Ajoc_sql = "INSERT INTO ajoc_projeto (ajoc_id_projeto, ajoc_nome, ajoc_descricao, ajoc_data_criacao, ajoc_data_fim) VALUES (?,?,?,?,?)";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_p.getAjoc_id());
            Ajoc_ps.setString(2, Ajoc_p.getAjoc_nome());
            Ajoc_ps.setString(3, Ajoc_p.getAjoc_descricao());
            Ajoc_ps.setDate(4, Ajoc_p.getAjoc_data_criacao() != null
                    ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_string2Date(Ajoc_p.getAjoc_data_criacao()) : null);
            Ajoc_ps.setDate(5, Ajoc_p.getAjoc_data_fim() != null
                    ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_string2Date(Ajoc_p.getAjoc_data_fim()) : null);
            Ajoc_ps.executeUpdate();
        }
    }

    public void Ajoc_editar(Ajoc_Projeto Ajoc_p) throws SQLException {
        String Ajoc_sql = "UPDATE ajoc_projeto SET ajoc_nome=?, ajoc_descricao=?, ajoc_data_criacao=?, ajoc_data_fim=? WHERE ajoc_id_projeto=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setString(1, Ajoc_p.getAjoc_nome());
            Ajoc_ps.setString(2, Ajoc_p.getAjoc_descricao());
            Ajoc_ps.setDate(3, Ajoc_p.getAjoc_data_criacao() != null
                    ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_string2Date(Ajoc_p.getAjoc_data_criacao()) : null);
            Ajoc_ps.setDate(4, Ajoc_p.getAjoc_data_fim() != null
                    ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_string2Date(Ajoc_p.getAjoc_data_fim()) : null);
            Ajoc_ps.setObject(5, Ajoc_p.getAjoc_id()); // ✅ agora String
            Ajoc_ps.executeUpdate();
        }
    }

    public void Ajoc_deletar(UUID Ajoc_id) throws SQLException {
        String Ajoc_sql = "DELETE FROM ajoc_projeto WHERE ajoc_id_projeto=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_id);
            Ajoc_ps.executeUpdate();
        }
    }

    public List<Ajoc_Projeto> Ajoc_listar() throws SQLException {
        List<Ajoc_Projeto> Ajoc_lista = new ArrayList<>();
        String Ajoc_sql = "SELECT * FROM ajoc_projeto";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql); ResultSet Ajoc_rs = Ajoc_ps.executeQuery()) {

            while (Ajoc_rs.next()) {
                Ajoc_Projeto Ajoc_p = new Ajoc_Projeto();
                Ajoc_p.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_projeto"));
                Ajoc_p.setAjoc_nome(Ajoc_rs.getString("ajoc_nome"));
                Ajoc_p.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));

                Timestamp Ajoc_criacao = Ajoc_rs.getTimestamp("ajoc_data_criacao");
                Timestamp Ajoc_fim = Ajoc_rs.getTimestamp("ajoc_data_fim");

                Ajoc_p.setAjoc_data_criacao(Ajoc_criacao != null ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_date2String(Ajoc_criacao.toString()) : null);
                Ajoc_p.setAjoc_data_fim(Ajoc_fim != null ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_date2String(Ajoc_fim.toString()) : null);

                Ajoc_lista.add(Ajoc_p);
            }
        }
        return Ajoc_lista;
    }

    public Ajoc_Projeto Ajoc_buscarPorId(UUID Ajoc_id) throws SQLException {
        String Ajoc_sql = "SELECT * FROM ajoc_projeto WHERE ajoc_id_projeto=?";
        try (PreparedStatement Ajoc_ps = Ajoc_conn.prepareStatement(Ajoc_sql)) {
            Ajoc_ps.setObject(1, Ajoc_id);
            try (ResultSet Ajoc_rs = Ajoc_ps.executeQuery()) {
                if (Ajoc_rs.next()) {
                    Ajoc_Projeto Ajoc_p = new Ajoc_Projeto();
                    Ajoc_p.setAjoc_id((UUID)Ajoc_rs.getObject("ajoc_id_projeto"));
                    Ajoc_p.setAjoc_nome(Ajoc_rs.getString("ajoc_nome"));
                    Ajoc_p.setAjoc_descricao(Ajoc_rs.getString("ajoc_descricao"));

                    Timestamp Ajoc_criacao = Ajoc_rs.getTimestamp("ajoc_data_criacao");
                    Timestamp Ajoc_fim = Ajoc_rs.getTimestamp("ajoc_data_fim");

                    Ajoc_p.setAjoc_data_criacao(Ajoc_criacao != null ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_date2String(Ajoc_criacao.toString()) : null);
                    Ajoc_p.setAjoc_data_fim(Ajoc_fim != null ? Ajoc_ManipulaData.Ajoc_getInstancia().Ajoc_date2String(Ajoc_fim.toString()) : null);

                    return Ajoc_p;
                }
            }
        }
        return null;
    }

}
