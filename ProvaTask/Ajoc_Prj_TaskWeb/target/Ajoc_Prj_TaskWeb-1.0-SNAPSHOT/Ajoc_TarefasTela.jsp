<%-- 
    Document   : Ajoc_TarefasTela
    Created on : 21 de set. de 2025, 23:02:38
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
        <title>Cadastro de Tarefas</title>
    </head>
    <body>
        <div class="container mt-3">
            <h2>CADASTRO DE TAREFAS</h2>
            <form action="${pageContext.request.contextPath}/Ajoc_TarefaServlet" method="post">

                <c:if test="${not empty Ajoc_tarefa}">
                    <input type="hidden" name="Ajoc_id" value="${Ajoc_tarefa.ajoc_id}" />
                </c:if>

                <div class="mb-3 mt-3">
                    <label for="Ajoc_titulo">Título:</label>
                    <input type="text" class="form-control" id="Ajoc_titulo" name="Ajoc_titulo" 
                           value="${not empty Ajoc_tarefa ? Ajoc_tarefa.ajoc_titulo : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_descricao">Descrição:</label>
                    <textarea class="form-control" id="Ajoc_descricao" name="Ajoc_descricao">${not empty Ajoc_tarefa ? Ajoc_tarefa.ajoc_descricao : ''}</textarea>
                </div>

                <div class="mb-3">
                    <label for="Ajoc_prioridade">Prioridade:</label>
                    <input type="text" class="form-control" id="Ajoc_prioridade" name="Ajoc_prioridade" 
                           value="${not empty Ajoc_tarefa ? Ajoc_tarefa.ajoc_prioridade : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_data_inicio">Data de Início:</label>
                    <input type="date" class="form-control" id="Ajoc_data_inicio" name="Ajoc_data_inicio" 
                           value="${not empty Ajoc_tarefa ? Ajoc_tarefa.ajoc_data_inicio : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_data_fim">Data de Fim:</label>
                    <input type="date" class="form-control" id="Ajoc_data_fim" name="Ajoc_data_fim" 
                           value="${not empty Ajoc_tarefa ? Ajoc_tarefa.ajoc_data_fim : ''}" />
                </div>

                <div class="mb-3">
                    <label for="Ajoc_projeto">Projeto:</label>
                    <select class="form-control" id="Ajoc_projeto" name="Ajoc_projeto">
                        <c:forEach var="proj" items="${Ajoc_listaProjetos}">
                            <option value="${proj.ajoc_id}" 
                                    <c:if test="${Ajoc_tarefa!=null && Ajoc_tarefa.ajoc_Projeto.ajoc_id == proj.ajoc_id}">selected</c:if>>
                                ${proj.ajoc_nome}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary" name="acao" value="Salvar">Salvar</button>
                <button type="submit" class="btn btn-secondary" name="acao" value="Listar">Listar</button>

            </form>

            <h2 class="mt-5">Lista de Tarefas</h2>
            <table class="table table-dark table-striped">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Descrição</th>
                        <th>Prioridade</th>
                        <th>Data de início</th>
                        <th>Data de fim</th>
                        <th>Projeto</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ajoc_tarefa" items="${Ajoc_listaTarefas}">
                        <tr>
                            <td><c:out value="${ajoc_tarefa.ajoc_titulo}"/></td>
                            <td><c:out value="${ajoc_tarefa.ajoc_descricao}"/></td>
                            <td><c:out value="${ajoc_tarefa.ajoc_prioridade}"/></td>
                            <td><c:out value="${ajoc_tarefa.ajoc_data_inicio}"/></td>
                            <td><c:out value="${ajoc_tarefa.ajoc_data_fim}"/></td>
                            <td><c:out value="${ajoc_tarefa.ajoc_Projeto.ajoc_nome}"/></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Ajoc_TarefaServlet" method="post" style="display:inline">
                                    <input type="hidden" name="Ajoc_id" value="${ajoc_tarefa.ajoc_id}" />
                                    <button type="submit" class="btn btn-primary" name="acao" value="Editar">Editar</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/Ajoc_TarefaServlet" method="post" style="display:inline">
                                    <input type="hidden" name="Ajoc_id" value="${ajoc_tarefa.ajoc_id}" />
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

