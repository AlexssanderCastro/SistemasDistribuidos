/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_cliente_task.Ajoc_server;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 *
 * @author Alexssander
 */
public class Ajoc_serverHandler {

    private Socket ajoc_socket;
    private PrintWriter ajoc_saida;
    private BufferedReader ajoc_entrada;
    private static final Gson ajoc_gson = new Gson();

    private boolean ajoc_conectado = false;

    public void ajoc_conectar(String ajoc_ip, int ajoc_porta) throws IOException {
        ajoc_socket = new Socket(ajoc_ip, ajoc_porta);
        ajoc_saida = new PrintWriter(ajoc_socket.getOutputStream(), true);
        ajoc_entrada = new BufferedReader(new InputStreamReader(ajoc_socket.getInputStream()));
        ajoc_conectado = true;
    }

    public String ajoc_enviarMensagem(String ajoc_acao, String ajoc_tabela, Map<String, String> ajoc_dados) throws IOException {
        if (!ajoc_conectado) {
            throw new IOException("Não está conectado ao servidor.");
        }

        Map<String, Object> mensagem = Map.of(
                "acao", ajoc_acao,
                "tabela", ajoc_tabela,
                "dados", ajoc_dados != null ? ajoc_dados : Map.of() // evita null
        );

        String ajoc_json = ajoc_gson.toJson(mensagem);
        ajoc_saida.println(ajoc_json);

        return ajoc_entrada.readLine();
    }

    public void ajoc_fechar() {
        try {
            if (ajoc_entrada != null) {
                ajoc_entrada.close();
            }
            if (ajoc_saida != null) {
                ajoc_saida.close();
            }
            if (ajoc_socket != null) {
                ajoc_socket.close();
            }
            ajoc_conectado = false;
        } catch (IOException ajoc_e) {
            ajoc_e.printStackTrace();
        }
    }

    public boolean ajoc_isConectado() {
        return ajoc_conectado;
    }
}
