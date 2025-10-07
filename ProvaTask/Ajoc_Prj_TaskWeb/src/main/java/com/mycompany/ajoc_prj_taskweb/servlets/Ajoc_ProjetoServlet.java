/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ajoc_prj_taskweb.servlets;

import com.mycompany.ajoc_prj_taskweb.Ajoc_DAO.Ajoc_ProjetoDAO;
import com.mycompany.ajoc_prj_taskweb.Ajoc_Model.Ajoc_Projeto;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
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
@WebServlet(name = "Ajoc_ProjetoServlet", urlPatterns = {"/Ajoc_ProjetoServlet"})
public class Ajoc_ProjetoServlet extends HttpServlet {

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
        }

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
                default:
                    throw new ServletException("Ação inválida: " + Ajoc_acao);
            }

        } catch (SQLException ex) {
            throw new ServletException("Erro ao processar ação: " + Ajoc_acao, ex);
        }
    }

    private void Ajoc_salvar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_Projeto Ajoc_projeto = new Ajoc_Projeto();
        Ajoc_projeto.setAjoc_nome(Ajoc_request.getParameter("Ajoc_nome"));
        Ajoc_projeto.setAjoc_descricao(Ajoc_request.getParameter("Ajoc_descricao"));
        Ajoc_projeto.setAjoc_data_criacao(Ajoc_request.getParameter("Ajoc_data_criacao"));
        Ajoc_projeto.setAjoc_data_fim(Ajoc_request.getParameter("Ajoc_data_fim"));

        Ajoc_ProjetoDAO Ajoc_projetoDAO = new Ajoc_ProjetoDAO();

        if (Ajoc_id != null && !Ajoc_id.equals("")) {
            Ajoc_projeto.setAjoc_id(Ajoc_id);
            Ajoc_projetoDAO.Ajoc_editar(Ajoc_projeto);
        } else {
            Ajoc_projeto.setAjoc_id(java.util.UUID.randomUUID());
            Ajoc_projetoDAO.Ajoc_salvar(Ajoc_projeto);
        }

        Ajoc_listar(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_listar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response)
            throws SQLException, ServletException, IOException {

        Ajoc_ProjetoDAO Ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        List<Ajoc_Projeto> Ajoc_listaProjetos = Ajoc_projetoDAO.Ajoc_listar();

        Ajoc_request.setAttribute("Ajoc_listaProjetos", Ajoc_listaProjetos);

        RequestDispatcher dispatcher = Ajoc_request.getRequestDispatcher("/Ajoc_ProjetosTela.jsp");
        dispatcher.forward(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_editar(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_ProjetoDAO ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        Ajoc_Projeto ajoc_projeto = ajoc_projetoDAO.Ajoc_buscarPorId(Ajoc_id);
        if (ajoc_projeto == null) {
            throw new ServletException("Projeto com ID " + Ajoc_id + " não encontrado.");
        }

        if (ajoc_projeto.getAjoc_data_criacao() != null) {
            String d = ajoc_projeto.getAjoc_data_criacao();
            ajoc_projeto.setAjoc_data_criacao(d.substring(6, 10) + "-" + d.substring(3, 5) + "-" + d.substring(0, 2));
        }
        if (ajoc_projeto.getAjoc_data_fim() != null) {
            String d = ajoc_projeto.getAjoc_data_fim();
            ajoc_projeto.setAjoc_data_fim(d.substring(6, 10) + "-" + d.substring(3, 5) + "-" + d.substring(0, 2));
        }

        Ajoc_request.setAttribute("Ajoc_projeto", ajoc_projeto);

        RequestDispatcher ajoc_dispatcher = Ajoc_request.getRequestDispatcher("/Ajoc_ProjetosTela.jsp");
        ajoc_dispatcher.forward(Ajoc_request, Ajoc_response);
    }

    private void Ajoc_excluir(HttpServletRequest Ajoc_request, HttpServletResponse Ajoc_response, UUID Ajoc_id)
            throws SQLException, ServletException, IOException {

        Ajoc_ProjetoDAO Ajoc_projetoDAO = new Ajoc_ProjetoDAO();
        Ajoc_projetoDAO.Ajoc_deletar(Ajoc_id);

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
