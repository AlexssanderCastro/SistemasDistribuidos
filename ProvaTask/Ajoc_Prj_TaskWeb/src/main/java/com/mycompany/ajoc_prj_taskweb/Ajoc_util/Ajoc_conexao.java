/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.Ajoc_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alexssander
 */
public class Ajoc_conexao {

    private static Ajoc_conexao ajoc_instancia;
    private Connection ajoc_conexao;

    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/Ajoc_organizar_tarefas";
    private final String usuario = "postgres";
    private final String senha = "Ajoc1112=";

    private Ajoc_conexao() {
        try {
            Class.forName(driver);
            ajoc_conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Ajoc_conexao Ajoc_getInstancia() {
        if (ajoc_instancia == null) {
            ajoc_instancia = new Ajoc_conexao();
        }
        return ajoc_instancia;
    }

    public Connection Ajoc_conectar() {
        return ajoc_conexao;
    }
}