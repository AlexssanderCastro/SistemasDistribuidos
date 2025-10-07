/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_Server;

import com.google.gson.Gson;
import com.mycompany.ajoc_prj_task.Ajoc_DAO.Ajoc_ArquivoHandler;
import com.mycompany.ajoc_prj_task.Ajoc_Forms.Ajoc_TelaInicial;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Mensagem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Alexssander
 */
public class Ajoc_ClientHandler implements Runnable {

    private Socket ajoc_socket;
    private static final Gson gson = new Gson();
    private final Ajoc_ArquivoHandler ajoc_arquivoHandler = new Ajoc_ArquivoHandler();

    public Ajoc_ClientHandler(Socket socket) {
        this.ajoc_socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader ajoc_entrada = new BufferedReader(new InputStreamReader(ajoc_socket.getInputStream())); PrintWriter ajoc_saida = new PrintWriter(ajoc_socket.getOutputStream(), true);) {
            String ajoc_mensagem;
            while ((ajoc_mensagem = ajoc_entrada.readLine()) != null) {
                System.out.println("Recebido do cliente: " + ajoc_mensagem);

                Ajoc_Mensagem ajoc_msg = gson.fromJson(ajoc_mensagem, Ajoc_Mensagem.class);

                switch (ajoc_msg.getAcao().toLowerCase()) {
                    case "adicionar":
                        ajoc_arquivoHandler.salvar(ajoc_msg.getTabela(), ajoc_msg.getDados());
                        if (Ajoc_TelaInicial.ajoc_isCon) {
                            try {
                                Ajoc_TelaInicial.ajoc_server.ajoc_enviarMensagem(
                                        "add", 
                                        ajoc_msg.getTabela(),
                                        ajoc_msg.getDados()
                                );
                            } catch (IOException e) {
                                System.err.println("[SYNC] Erro ao enviar dados para o outro servidor: " + e.getMessage());
                            }
                        }
                        ajoc_saida.println("{\"status\":\"ok\",\"mensagem\":\"Dados salvos\"}");
                        break;

                    case "listar":
                        String conteudo = ajoc_arquivoHandler.Ajoc_lerTodos(ajoc_msg.getTabela());
                        System.out.println("Chegou aqui no listar:\n" + ajoc_msg.getTabela());
                        ajoc_saida.println("{\"status\":\"ok\",\"dados\":" + conteudo + "}");
                        break;
                    case "add":
                        System.out.println("Chegou aqui");
                        ajoc_arquivoHandler.salvar(ajoc_msg.getTabela(), ajoc_msg.getDados());
                        System.out.println("Aqui tambem");
                        ajoc_saida.println("{\"status\":\"ok\",\"mensagem\":\"Dados salvos\"}");
                        break;
                    case "sync":
                        System.out.println("Chegou aqui");
                        ajoc_arquivoHandler.Ajoc_salvarSync(ajoc_msg.getTabela(), ajoc_msg.getDados());
                        System.out.println("Aqui tambem");
                        ajoc_saida.println("{\"status\":\"ok\",\"mensagem\":\"Dados salvos\"}");
                        ajoc_saida.println("{\"status\":\"ok\",\"mensagem\":\"Sincronização concluída\"}");
                        break;
                    default:
                        ajoc_saida.println("{\"status\":\"erro\",\"mensagem\":\"Ação desconhecida\"}");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + ajoc_socket.getInetAddress());
        } finally {
            try {
                Ajoc_ServidorSocketThread.ajoc_clientesAtivos.remove(this);
                ajoc_socket.close();
            } catch (IOException ajoc_e) {
                ajoc_e.printStackTrace();
            }
        }
    }

}
