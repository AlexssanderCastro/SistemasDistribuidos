/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.servlets;

import com.mycompany.ajoc_prj_taskweb.Ajoc_DAO.Ajoc_ProjetoDAO;
import com.mycompany.ajoc_prj_taskweb.Ajoc_DAO.Ajoc_TarefaDAO;
import com.mycompany.ajoc_prj_taskweb.Ajoc_Model.Ajoc_Projeto;
import com.mycompany.ajoc_prj_taskweb.Ajoc_Model.Ajoc_Tarefa;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Alexssander
 */
@WebServlet(name = "Ajoc_TarefaServlet", urlPatterns = {"/Ajoc_TarefaServlet"})
public class Ajoc_TarefaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response)
            throws ServletException, IOException {

        Ajoc_response.setContentType("text/html;charset=UTF-8");

        String Ajoc_acao = Ajoc_request.getParameter("acao");
        String ajocIdStr = Ajoc_request.getParameter("Ajoc_id");
        UUID Ajoc_id = null;

        if (ajocIdStr != null && !ajocIdStr.isEmpty()) {
            try {
                Ajoc_id = UUID.fromString(ajocIdStr);
            } catch (IllegalArgumentException e) {
                System.out.println("ID inválido: " + ajocIdStr);
            }
        } // ✅ Agora é String

        try {
            if (Ajoc_acao == null) {
                Ajoc_listar(Ajoc_request, Ajoc_response);
                return;
            }

            switch (Ajoc_acao) {
                case "Salvar":
                    Ajoc_salvar(Ajoc_request, Ajoc_response, Ajoc_id);
                    break;
                case "Listar":
                    Ajoc_listar(Ajoc_request, Ajoc_response);
                    break;
                case "Editar":
                    Ajoc_editar(Ajoc_request, Ajoc_response, Ajoc_id);
                    break;
                case "Excluir":
                    Ajoc_excluir(Ajoc_request, Ajoc_response, Ajoc_id);
                    break;
                case "":
                    Ajoc_listar(Ajoc_request, Ajoc_response);
                    break;
                default:
                   Ajoc_listar(Ajoc_request, Ajoc_response);
            }

        } catch (SQLException ex) {
            throw new ServletException("Erro ao processar ação: " + Ajoc_acao, ex);
        }
    }

    private void Ajoc_salvar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_Tarefa Ajoc_tarefa = new Ajoc_Tarefa();
        Ajoc_tarefa.setAjoc_titulo(Ajoc_request.getParameter("Ajoc_titulo"));
        Ajoc_tarefa.setAjoc_descricao(Ajoc_request.getParameter("Ajoc_descricao"));
        Ajoc_tarefa.setAjoc_prioridade(Ajoc_request.getParameter("Ajoc_prioridade"));

        String Ajoc_dataInicioStr = Ajoc_request.getParameter("Ajoc_data_inicio");
        Ajoc_tarefa.setAjoc_data_inicio((Ajoc_dataInicioStr != null && !Ajoc_dataInicioStr.isEmpty())
                ? Ajoc_dataInicioStr
                : null);

        String Ajoc_dataFimStr = Ajoc_request.getParameter("Ajoc_data_fim");
        Ajoc_tarefa.setAjoc_data_fim((Ajoc_dataFimStr != null && !Ajoc_dataFimStr.isEmpty())
                ? Ajoc_dataFimStr
                : null);

        String ajocIdStr = Ajoc_request.getParameter("Ajoc_projeto");
        UUID Ajoc_idProjetoParam = null;

        if (ajocIdStr != null && !ajocIdStr.isEmpty()) {
            try {
                Ajoc_idProjetoParam = UUID.fromString(ajocIdStr);
            } catch (IllegalArgumentException e) {
                System.out.println("ID inválido: " + ajocIdStr);
            }
        }
        
        if (Ajoc_idProjetoParam != null && !Ajoc_idProjetoParam.equals("")) {
            Ajoc_ProjetoDAO ajoc_projetoDAO = new Ajoc_ProjetoDAO();
            Ajoc_Projeto ajoc_projeto = ajoc_projetoDAO.Ajoc_buscarPorId(Ajoc_idProjetoParam); // ✅ busca por UUID
            Ajoc_tarefa.setAjoc_Projeto(ajoc_projeto);
        }

        Ajoc_TarefaDAO ajoc_tarefaDAO = new Ajoc_TarefaDAO();

        if (Ajoc_id != null && !Ajoc_id.equals("")) {
            Ajoc_tarefa.setAjoc_id(Ajoc_id); // ✅ agora é String
            ajoc_tarefaDAO.Ajoc_editar(Ajoc_tarefa);
        } else {
            Ajoc_tarefa.setAjoc_id(java.util.UUID.randomUUID()); // ✅ Gera UUID
            ajoc_tarefaDAO.Ajoc_salvar(Ajoc_tarefa);
        }

        Ajoc_listar(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_listar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response)
            throws SQLException, ServletException, IOException {

        Ajoc_TarefaDAO ajoc_tarefaDAO = new Ajoc_TarefaDAO();
        List<Ajoc_Tarefa> Ajoc_listaTarefas = ajoc_tarefaDAO.Ajoc_listar();

        Ajoc_ProjetoDAO Ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        List<Ajoc_Projeto> Ajoc_listaProjetos = Ajoc_projetoDAO.Ajoc_listar();

        Ajoc_request.setAttribute("Ajoc_listaTarefas", Ajoc_listaTarefas);
        Ajoc_request.setAttribute("Ajoc_listaProjetos", Ajoc_listaProjetos);

        RequestDispatcher dispatcher = Ajoc_request.getRequestDispatcher("/Ajoc_TarefasTela.jsp");
        dispatcher.forward(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_editar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_TarefaDAO ajoc_tarefaDAO = new Ajoc_TarefaDAO();
        Ajoc_Tarefa Ajoc_tarefa = ajoc_tarefaDAO.Ajoc_buscarPorId(Ajoc_id);
        if (Ajoc_tarefa == null) {
            throw new ServletException("Tarefa com ID " + Ajoc_id + " não encontrada.");
        }

        if (Ajoc_tarefa.getAjoc_data_inicio() != null) {
            String d = Ajoc_tarefa.getAjoc_data_inicio();
            Ajoc_tarefa.setAjoc_data_inicio(d.substring(6, 10) + "-" + d.substring(3, 5) + "-" + d.substring(0, 2));
        }
        if (Ajoc_tarefa.getAjoc_data_fim() != null) {
            String d = Ajoc_tarefa.getAjoc_data_fim();
            Ajoc_tarefa.setAjoc_data_fim(d.substring(6, 10) + "-" + d.substring(3, 5) + "-" + d.substring(0, 2));
        }

        Ajoc_ProjetoDAO ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        List<Ajoc_Projeto> Ajoc_listaProjetos = ajoc_projetoDAO.Ajoc_listar();

        Ajoc_request.setAttribute("Ajoc_tarefa", Ajoc_tarefa);
        Ajoc_request.setAttribute("Ajoc_listaProjetos", Ajoc_listaProjetos);

        RequestDispatcher dispatcher = Ajoc_request.getRequestDispatcher("/Ajoc_TarefasTela.jsp");
        dispatcher.forward(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_excluir(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_TarefaDAO ajoc_tarefaDAO = new Ajoc_TarefaDAO();
        ajoc_tarefaDAO.Ajoc_deletar(Ajoc_id); // ✅ UUID no delete

        Ajoc_listar(Ajoc_request, Ajoc_response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
