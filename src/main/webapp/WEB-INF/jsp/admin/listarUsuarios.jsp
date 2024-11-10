<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Admin - Gerenciar Usuários</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="css/styles.css" rel="stylesheet" />
</head>

<body id="page-top">
    <!-- Navigation -->
    <tag:menu-superior></tag:menu-superior>

    <div vw class="enabled">
        <div vw-access-button class="active"></div>
        <div vw-plugin-wrapper>
            <div class="vw-plugin-top-wrapper"></div>
        </div>
    </div>
    <script src="https://vlibras.gov.br/app/vlibras-plugin.js"></script>
    <script>
        new window.VLibras.Widget('https://vlibras.gov.br/app');
    </script>

    <header class="masthead" id="gerenciar-usuarios">
        <div class="container text-center">
            <h2>Gerenciar Usuários</h2>
            <c:if test="${not empty mensagemSucesso}">
                <div class="alert alert-success">
                    ${mensagemSucesso}
                </div>
            </c:if>

            <c:if test="${not empty mensagemErro}">
                <div class="alert alert-danger">
                    ${mensagemErro}
                </div>
            </c:if>
        </div>
    </header>

    <section class="page-section bg-light" id="usuarios">
        <div class="container">
            <form method="get" action="<c:url value='usuarios' />">
                <div class="row mb-4">
                    <div class="col-md-8">
                        <input type="text" name="filtro" placeholder="Buscar por nome ou email" class="form-control" value="${filtro}" />
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-danger">Buscar</button>
                    </div>
                </div>
            </form>

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Nível</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr>
                            <td>${usuario.nome}</td>
                            <td>${usuario.email}</td>
                            <td>${usuario.tipoUsuario}</td>
                            <td>
                                <form method="post" action="<c:url value='usuarios/editarNivel' />" class="d-inline">
                                    <input type="hidden" name="usuarioId" value="${usuario.id}" />
                                    <select name="nivel" class="form-control d-inline" style="width: auto;">
                                        <option value="USER" ${usuario.tipoUsuario == 'USER' ? 'selected' : ''}>USER</option>
                                        <option value="ADMIN" ${usuario.tipoUsuario == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                    </select>
                                    <button type="submit" class="btn btn-info btn-sm">Alterar</button>
                                </form>
                                <form method="post" action="<c:url value='usuarios/excluirUsuario'/>" class="d-inline">
                                    <input type="hidden" name="usuarioId" value="${usuario.id}" />
                                    <button type="submit" class="btn btn-danger btn-sm">Excluir</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

			<div style="display:flex;justify-content:center">
			    <c:if test="${paginaAtual <= 1}">
			        <span class="btn btn-secondary disabled" style="pointer-events: none; opacity: 0.6;">Anterior</span>
			    </c:if>
			    
			    <c:if test="${paginaAtual > 1}">
			        <a href="<c:url value='usuarios?page=${paginaAtual - 1}'/>" class="btn btn-secondary">Anterior</a>
			    </c:if>
			
			    <!-- Lógica para exibir as páginas -->
			    <c:set var="inicio" value="${(paginaAtual - 5 < 1) ? 1 : paginaAtual - 5}" />
			    
			    <!-- Ajusta 'fim' para ser 10 se 'paginaAtual' for de 1 a 5 -->
			            <c:if test="${paginaAtual <= 5}">
			                <c:if test="${totalPaginas >= 10}">
						        <c:set var="fim" value="10" />
						    </c:if>
						    <c:if test="${totalPaginas < 10}">
						        <c:set var="fim" value="${totalPaginas}" />
						    </c:if>
			            </c:if>
			            <c:if test="${paginaAtual > 5}">
			                <c:set var="fim" value="${(paginaAtual + 5 > totalPaginas) ? totalPaginas : paginaAtual + 5}" />
			            </c:if>
			
			    	<c:forEach var="i" begin="${inicio}" end="${fim}">
				        <c:if test="${i == paginaAtual}">
				            <span class="btn btn-dark" style="text-decoration: underline;">${i}</span>
				        </c:if>
				        <c:if test="${i != paginaAtual}">
				            <a href="<c:url value='usuarios?page=${i}'/>" class="btn btn-light">${i}</a>
				        </c:if>
				    </c:forEach>	
			
			    <c:if test="${paginaAtual < totalPaginas}">
			        <a href="<c:url value='usuarios?page=${paginaAtual + 1}'/>" class="btn btn-secondary">Próxima</a>
			    </c:if>
			    
			    <c:if test="${paginaAtual >= totalPaginas}">
			        <span class="btn btn-secondary disabled" style="pointer-events: none; opacity: 0.6;">Próxima</span>
			    </c:if>
			</div>
        </div>
    </section>

    <tag:footer></tag:footer>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
