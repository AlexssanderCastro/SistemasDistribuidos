/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_Server;

import com.google.gson.Gson;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Mensagem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
    private Consumer<Ajoc_Mensagem> ajoc_notificacaoListener;

    // Executor para agendamentos no cliente
    private ScheduledExecutorService ajoc_scheduler;

    /**
     * Conecta ao servidor e inicia thread de escuta
     */
    public void ajoc_conectar(String ajoc_ip, int ajoc_porta) throws IOException {
        ajoc_socket = new Socket(ajoc_ip, ajoc_porta);
        ajoc_saida = new PrintWriter(ajoc_socket.getOutputStream(), true);
        ajoc_entrada = new BufferedReader(new InputStreamReader(ajoc_socket.getInputStream()));
        ajoc_conectado = true;

        iniciarEscuta();
    }

    /**
     * Envia uma mensagem simples ao servidor (requisição/reposta)
     */
    public void ajoc_enviarMensagem(String ajoc_acao, String ajoc_tabela, Map<String, String> ajoc_dados) throws IOException {
        if (!ajoc_conectado) {
            throw new IOException("Não está conectado ao servidor.");
        }

        Map<String, Object> mensagem = Map.of(
                "acao", ajoc_acao,
                "tabela", ajoc_tabela,
                "dados", ajoc_dados != null ? ajoc_dados : Map.of()
        );

        String ajoc_json = ajoc_gson.toJson(mensagem);
        ajoc_saida.println(ajoc_json);
        
    }

    /**
     * Envia uma sincronização completa para o servidor remoto
     */
    public void ajoc_enviarSincronizacao(String ajoc_tabela, List<?> dados) {
        if (!ajoc_conectado) {
            return;
        }

        Map<String, Object> mensagem = Map.of(
                "acao", "sync",
                "tabela", ajoc_tabela,
                "dados", dados
        );

        String ajoc_json = ajoc_gson.toJson(mensagem);
        ajoc_saida.println(ajoc_json);
    }

    /**
     * Thread que escuta continuamente mensagens do servidor
     */
    private void iniciarEscuta() {
        new Thread(() -> {
            try {
                String linha;
                while ((linha = ajoc_entrada.readLine()) != null) {
                    System.out.println("[NOTIFICAÇÃO] Recebido do servidor: " + linha);

                    try {
                        Ajoc_Mensagem msg = ajoc_gson.fromJson(linha, Ajoc_Mensagem.class);
                        if (ajoc_notificacaoListener != null) {
                            ajoc_notificacaoListener.accept(msg);
                        }
                    } catch (Exception parseErr) {
                        System.err.println("[ERRO] Falha ao interpretar mensagem: " + parseErr.getMessage());
                    }
                }
            } catch (IOException e) {
                if (ajoc_conectado) {
                    System.err.println("[ERRO] Conexão perdida: " + e.getMessage());
                }
            }
        }, "Ajoc-Server-Listener").start();
    }

    /**
     * Configura listener de notificações recebidas do servidor
     */
    public void setAjoc_notificacaoListener(Consumer<Ajoc_Mensagem> listener) {
        this.ajoc_notificacaoListener = listener;
    }

    /**
     * Inicia um scheduler para atualizar relatórios no cliente
     */
    public void iniciarAtualizacaoPeriodica(Runnable tarefa, long intervaloSegundos) {
        if (ajoc_scheduler != null && !ajoc_scheduler.isShutdown()) {
            ajoc_scheduler.shutdownNow();
        }

        ajoc_scheduler = Executors.newSingleThreadScheduledExecutor();
        ajoc_scheduler.scheduleAtFixedRate(tarefa, 0, intervaloSegundos, TimeUnit.SECONDS);
    }

    /**
     * Fecha conexão e encerra threads
     */
    public void ajoc_fechar() {
        try {
            if (ajoc_scheduler != null) {
                ajoc_scheduler.shutdownNow();
            }
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
