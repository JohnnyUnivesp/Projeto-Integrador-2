<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Banners</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/styles.css" />
</head>
<body>
    <header class="masthead">
    <!-- Navigation -->
    <tag:menu-superior></tag:menu-superior>

    </header>
    <div class="container mt-4" style="justify-content">
        <section class="page-section" id="editarBanners">
            <div class="container">
                <h2 class="text-center mb-4">Gerenciar Banners</h2>

                <!-- Exibe mensagens de erro, se houver -->
                <c:if test="${not empty errors}">
                    <div class="alert alert-danger" role="alert">
                        <c:forEach var="error" items="${errors}">
                            ${error.message}<br/>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- Formulário para Adicionar ou Editar Banner -->
                <form method="post" action="<c:url value='/adicionarBanner' />" enctype="multipart/form-data">
                    <input type="hidden" name="banner.id" value="${banner.id}">
                    <input type="hidden" name="banner.ativo" value="${banner.isAtivo()}">

                    <div class="form-group">
                        <label>Imagem do Banner:</label>

                        <!-- Se o banner tiver uma imagem existente, exibe informações da imagem -->
                        <c:if test="${banner.imagem != null}">
                            <div class="form-group">
                                <label>Imagem atual: ${banner.imagem.nome}</label>
                                <!-- Campos ocultos para manter a imagem existente -->
                                <input type="hidden" name="imagem.id" value="${banner.imagem.id}">
                                <input type="hidden" name="imagem.nome" value="${banner.imagem.nome}">
                            </div>
                        </c:if>

                        <!-- Campo para upload de nova imagem -->
                        <div class="form-group">
                            <input type="file" name="banner.imagem.file" id="input-id" class="file" data-preview-file-type="text">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">Salvar Banner</button>
                </form>

                <!-- Configurar Tempo de Exibição -->
                <h6 class="mt-3">Tempo de Exibição</h6>
                <form id="formConfigurarTempo" action="<c:url value='/configurarTempoExibicao'/>" method="post">
                    <div class="form-group">
                        <label for="tempoExibicaoBanner">Tempo de exibição (segundos)</label>
                        <input type="number" class="form-control" id="tempoExibicao" name="tempoExibicao" min="1" value="${tempoExibicaoBanner}" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Salvar Configuração</button>
                </form>

                <!-- Listagem dos Banners Existentes com opção de Exclusão -->
                <h3 class="mt-4">Banners Atuais</h3>
                <ul class="list-group mb-3">
                    <c:forEach var="banner" items="${banners}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <img src="img/get/${banner.imagem.nome}" alt="Banner" width="100" class="mr-3"/>
                                ${banner.titulo}
                            </div>
                            <!-- Botão para excluir o banner -->
                            <form action="<c:url value='/removerBanner/${banner.id}' />" method="post" class="mb-0">
                                <button type="submit" class="btn btn-danger">Remover</button>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
    </div>
	<tag:footer></tag:footer>
    <!-- Scripts do Bootstrap e JavaScript -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
