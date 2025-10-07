/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_Server;

import com.mycompany.ajoc_prj_task.Ajoc_DAO.Ajoc_ArquivoHandler;
import com.mycompany.ajoc_prj_task.Ajoc_DAO.Ajoc_ProjetoDAO;
import com.mycompany.ajoc_prj_task.Ajoc_DAO.Ajoc_TarefaDAO;
import com.mycompany.ajoc_prj_task.Ajoc_Forms.Ajoc_TelaInicial;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_task.Ajoc_model.Ajoc_Tarefa;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Alexssander
 */
public class Ajoc_ServidorSocketThread extends Thread {

    private int ajoc_porta;
    private ServerSocket ajoc_serverSocket;
    private boolean ajoc_rodando = true;
    private ExecutorService ajoc_poolClientes = Executors.newCachedThreadPool();
    private ScheduledExecutorService ajoc_scheduler = Executors.newSingleThreadScheduledExecutor();
    public static Set<Ajoc_ClientHandler> ajoc_clientesAtivos = ConcurrentHashMap.newKeySet();

    public Ajoc_ServidorSocketThread(int ajoc_porta) {
        this.ajoc_porta = ajoc_porta;
    }

    @Override
    public void run() {
        try {
            ajoc_serverSocket = new ServerSocket(ajoc_porta);
            System.out.println("Servidor iniciado na porta " + ajoc_porta);

            if (Ajoc_TelaInicial.ajoc_isMatriz) {
                iniciarSincronizacaoBanco();
            }
            

            while (ajoc_rodando) {
                Socket clienteSocket = ajoc_serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                Ajoc_ClientHandler ajoc_clienteHandler = new Ajoc_ClientHandler(clienteSocket);
                ajoc_clientesAtivos.add(ajoc_clienteHandler);
                ajoc_poolClientes.submit(ajoc_clienteHandler);
            }
        } catch (IOException ajoc_e) {
            System.out.println("Erro no servidor: " + ajoc_e.getMessage());
        } finally {
            Ajoc_fecharServidor();
        }
    }

    public void Ajoc_pararServidor() {
        ajoc_rodando = false;
        Ajoc_fecharServidor();
    }

    private void Ajoc_fecharServidor() {
        try {
            if (ajoc_serverSocket != null && !ajoc_serverSocket.isClosed()) {
                ajoc_serverSocket.close();
            }
            ajoc_poolClientes.shutdown();
            ajoc_scheduler.shutdown();
            System.out.println("Servidor parado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniciarSincronizacaoBanco() {
        ajoc_scheduler.scheduleAtFixedRate(() -> {
            try {
                Ajoc_ArquivoHandler ajoc_ArquivoHandler = new Ajoc_ArquivoHandler();
                ajoc_ArquivoHandler.sincronizarBanco();
                if(Ajoc_TelaInicial.ajoc_isCon){
                    ajoc_ArquivoHandler.Ajoc_enviarSyncRegistroPorRegistro();
                }
                System.out.println("[SYNC] Arquivos sincronizados com banco!");
            } catch (Exception e) {
                System.err.println("[SYNC] Erro ao sincronizar arquivos: " + e.getMessage());
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

}
