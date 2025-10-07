/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ajoc_prj_cliente_task.Forms;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_Model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_Model.Ajoc_Tarefa;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_server.Ajoc_serverHandler;
import com.mycompany.ajoc_prj_cliente_task.Ajoc_util.Ajoc_parseJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alexssander
 */
public class Ajoc_RelatorioTarefas extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Ajoc_RelatorioTarefas.class.getName());

    /**
     * Creates new form Ajoc_RelatorioTarefas
     */
    private Ajoc_serverHandler ajoc_server;
    private final Gson ajoc_gson = new Gson();
    private List<Ajoc_Tarefa> ajoc_tarefas;
    private List<Ajoc_Projeto> ajoc_projetos;

    public Ajoc_RelatorioTarefas(Ajoc_serverHandler ajoc_server) {
        initComponents();
        this.ajoc_server = ajoc_server;
        this.Ajoc_fetchProjetos();
        this.Ajoc_fetchTarefas();
    }
    
    public void Ajoc_atualizar(){
        this.Ajoc_fetchProjetos();
        this.Ajoc_fetchTarefas();
    }

    private void Ajoc_fetchTarefas() {
        try {
            String jsonResposta = this.ajoc_server.ajoc_enviarMensagem("listar", "tarefas", Map.of());
            System.out.println(jsonResposta);
            List<Ajoc_Tarefa> lista = Ajoc_parseListaTarefas(jsonResposta);

            this.ajoc_tarefas = lista;

            Ajoc_atualizarTabelaTarefas();

            System.out.println("Tarefas carregadas: " + ajoc_tarefas.size());

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar tarefas no servidor:\n" + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Ajoc_Tarefa> Ajoc_parseListaTarefas(String jsonResposta) {
        List<Ajoc_Tarefa> lista = new ArrayList<>();

        try {
            JsonObject obj = ajoc_gson.fromJson(jsonResposta, JsonObject.class);

            if (!obj.get("status").getAsString().equals("ok")) {
                return lista;
            }

            JsonArray array = obj.getAsJsonArray("dados");

            for (JsonElement el : array) {
                Ajoc_Tarefa ajoc_t = Ajoc_parseJson.ajoc_parseTarefa(el.toString());
                if (ajoc_t != null) {
                    if (ajoc_t.getAjoc_Projeto() != null && this.ajoc_projetos != null) {
                        UUID idProjeto = ajoc_t.getAjoc_Projeto().getAjoc_id();

                        for (Ajoc_Projeto ajoc_p : this.ajoc_projetos) {
                            if (ajoc_p.getAjoc_id().equals(idProjeto)) {
                                ajoc_t.getAjoc_Projeto().setAjoc_nome(ajoc_p.getAjoc_nome());
                                break;
                            }
                        }
                    }
                    lista.add(ajoc_t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void Ajoc_atualizarTabelaTarefas() {
        DefaultTableModel model = (DefaultTableModel) Ajoc_tableTarefas.getModel();
        model.setRowCount(0); // limpa linhas antigas

        for (Ajoc_Tarefa t : ajoc_tarefas) {
            model.addRow(new Object[]{
                t.getAjoc_titulo(),
                t.getAjoc_descricao(),
                t.getAjoc_prioridade(),
                t.getAjoc_data_inicio(),
                t.getAjoc_data_fim(),
                t.getAjoc_Projeto() != null ? t.getAjoc_Projeto().getAjoc_nome() : ""
            });
        }
    }

    private void Ajoc_fetchProjetos() {
        try {
            String jsonResposta = this.ajoc_listarProjetos();

            List<Ajoc_Projeto> lista = ajoc_parseListaProjetos(jsonResposta);

            this.ajoc_projetos = lista;

            System.out.println("Projetos carregados: " + ajoc_projetos.size());

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar projetos no servidor:\n" + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String ajoc_listarProjetos() throws IOException {
        return this.ajoc_server.ajoc_enviarMensagem("listar", "projetos", null);
    }

    public List<Ajoc_Projeto> ajoc_parseListaProjetos(String jsonResposta) {
        List<Ajoc_Projeto> lista = new ArrayList<>();

        try {
            JsonObject obj = ajoc_gson.fromJson(jsonResposta, JsonObject.class);
            if (!obj.get("status").getAsString().equals("ok")) {
                return lista;
            }

            JsonArray array = obj.getAsJsonArray("dados");
            for (JsonElement el : array) {
                Ajoc_Projeto p = Ajoc_parseJson.ajoc_parseProjeto(el.toString());
                if (p != null) {
                    lista.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Ajoc_tableTarefas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Relatório de tarefas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        Ajoc_tableTarefas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Titulo", "Descrição", "Prioridade", "Data de inicio", "Data de fim", "Projeto"
            }
        ));
        jScrollPane1.setViewportView(Ajoc_tableTarefas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Ajoc_serverHandler ajoc_server = new Ajoc_serverHandler();
        ajoc_server.ajoc_conectar("127.0.0.1", 2014);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Ajoc_RelatorioTarefas(ajoc_server).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Ajoc_tableTarefas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
