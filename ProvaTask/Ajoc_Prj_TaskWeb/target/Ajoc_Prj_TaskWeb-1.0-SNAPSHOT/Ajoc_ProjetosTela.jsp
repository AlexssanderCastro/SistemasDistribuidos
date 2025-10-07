<%-- 
    Document   : Ajoc_ProjetosTela
    Created on : 21 de set. de 2025, 23:02:51
    Author     : Alexssander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
        <title>Cadastro de Projetos</title>
    </head>
    <body>
        <div class="container mt-3">
            <h2>CADASTRO DE PROJETOS</h2>
            <form action="${pageContext.request.contextPath}/Ajoc_ProjetoServlet" method="post">
                <c:if test="${not empty Ajoc_projeto}"> 
                    <input type="hidden" name="Ajoc_id" value="${Ajoc_projeto.ajoc_id}" />
                </c:if>

                <div class="mb-3 mt-3">
                    <label for="Ajoc_nome">Nome do Projeto:</label>
                    <input type="text" class="form-control" id="Ajoc_nome" name="Ajoc_nome" 
                           value="${not empty Ajoc_projeto ? Ajoc_projeto.ajoc_nome : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_descricao">Descrição:</label>
                    <textarea class="form-control" id="Ajoc_descricao" name="Ajoc_descricao">${not empty Ajoc_projeto ? Ajoc_projeto.ajoc_descricao : ''}</textarea>
                </div>

                <div class="mb-3">
                    <label for="Ajoc_data_criacao">Data de Criação:</label>
                    <input type="date" class="form-control" id="Ajoc_data_criacao" name="Ajoc_data_criacao" 
                           value="${not empty Ajoc_projeto ? Ajoc_projeto.ajoc_data_criacao : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_data_fim">Data de Fim:</label>
                    <input type="date" class="form-control" id="Ajoc_data_fim" name="Ajoc_data_fim" 
                           value="${not empty Ajoc_projeto ? Ajoc_projeto.ajoc_data_fim : ''}" />
                </div>

                <button type="submit" class="btn btn-primary" name="acao" value="Salvar">Salvar</button>
                <button type="submit" class="btn btn-secondary" name="acao" value="Listar">Listar</button>

            </form>

            <h2 class="mt-5">Lista de Projetos</h2>
            <table class="table table-dark table-striped">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Data de início</th>
                        <th>Data de fim</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="Ajoc_projeto" items="${Ajoc_listaProjetos}">
                        <tr>
                            <td><c:out value="${Ajoc_projeto.ajoc_nome}"/></td>
                            <td><c:out value="${Ajoc_projeto.ajoc_descricao}"/></td>                           
                            <td><c:out value="${Ajoc_projeto.ajoc_data_criacao}"/></td>
                            <td><c:out value="${Ajoc_projeto.ajoc_data_fim}"/></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Ajoc_ProjetoServlet" method="post" style="display:inline">
                                    <input type="hidden" name="Ajoc_id" value="${Ajoc_projeto.ajoc_id}" />
                                    <button type="submit" class="btn btn-primary" name="acao" value="Editar">Editar</button>
                                </form>
                                <form action="${pageContext.request.contextPath}//Ajoc_ProjetoServlet" method="post" style="display:inline">
                                    <input type="hidden" name="Ajoc_id" value="${Ajoc_projeto.ajoc_id}" />
                                    <button type="submit" class="btn btn-danger" name="acao" value="Excluir">Excluir</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
