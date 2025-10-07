<%-- 
    Document   : Ajoc_home
    Created on : 26 de set. de 2025, 19:59:01
    Author     : Alexssander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Página Inicial</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    </head>
    <body>
        <div class="container mt-5 text-center">
            <h1>Bem-vindo ao Sistema</h1>
            <p>Escolha uma opção abaixo para continuar:</p>

            <div class="d-grid gap-3 col-6 mx-auto mt-4">
                <!-- Botão para Projetos -->
                <a href="${pageContext.request.contextPath}/Ajoc_ProjetoServlet" class="btn btn-primary btn-lg">
                    Ir para Projetos
                </a>

                <!-- Botão para Tarefas -->
                <a href="${pageContext.request.contextPath}/Ajoc_TarefaServlet" class="btn btn-success btn-lg">
                    Ir para Tarefas
                </a>
            </div>
        </div>

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    </body>
</html>
