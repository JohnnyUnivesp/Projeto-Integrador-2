<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Inicial</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/styles.css" />
</head>
<body>

    <!-- Accessibility Widget -->
    <div vw class="enabled">
        <div vw-access-button class="active"></div>
        <div vw-plugin-wrapper>
            <div class="vw-plugin-top-wrapper"></div>
        </div>
    </div>
    <script src="https://vlibras.gov.br/app/vlibras-plugin.js"></script>
    <script>new window.VLibras.Widget('https://vlibras.gov.br/app');</script>

    <!-- Carrossel de Banners -->
    <header class="masthead">

    <!-- Navigation-->
    <tag:menu-superior></tag:menu-superior>
    </header>
		<div class="container mt-4">
     <div id="carouselBanners" class="carousel slide" data-ride="carousel" data-interval="${tempoExibicaoBanner * 1000}">
        <div class="carousel-inner">
            <c:forEach var="banner" items="${banners}" varStatus="status">
                <div class="carousel-item ${status.first ? 'active' : ''}">
                    <img src="img/get/${banner.imagem.nome}" class="d-block w-100 rounded" alt="Banner">
                </div>
            </c:forEach>
        </div>
        <a class="carousel-control-prev" href="#carouselBanners" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselBanners" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <!-- Botão para Editar Banners (somente para Admin) -->
    <c:if test="${isAdmin}">
        <div class="text-center my-3">
            <a href="<c:url value='/editarBanners'/>" class="btn btn-primary mb-3">Editar Banners</a>
        </div>
    </c:if>

<!-- Seção de Categorias e Produtos -->
<div class="container mt-4">
    <!-- Exibir categoria 1 e seus produtos -->
    <c:if test="${not empty categoria_0}">
        <div class="category-section mb-5">
            <div class="d-flex justify-content-between align-items-center">
                <h3>${categoria_0.nome}</h3>
                <a href="<c:url value='/produtos?filtro.categoria.id=${categoria_0.id}'/>" class="btn btn-link">Ver todos...</a>
            </div>
            <div class="row">
                <c:forEach var="produto" items="${produtosCategoria_0}">
                    <div class="col-md-3">
                        <div class="card h-100 shadow-sm" style="min-height: 350px;">
                            <img src="img/get/${produto.imagem.nome}" class="card-img-top" alt="${produto.nome}" style="height: 200px; object-fit: cover;">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${produto.nome}</h5>
                                <div class="mt-auto">
                                    <button class="btn btn-primary w-100" onclick="adicionarAoCarrinho(${produto.id}, 1)">Adicionar ao Carrinho</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Exibir categoria 2 e seus produtos -->
    <c:if test="${not empty categoria_1}">
        <div class="category-section mb-5">
            <div class="d-flex justify-content-between align-items-center">
                <h3>${categoria_1.nome}</h3>
                <a href="<c:url value='/produtos?filtro.categoria.id=${categoria_1.id}'/>" class="btn btn-link">Ver todos...</a>
            </div>
            <div class="row">
                <c:forEach var="produto" items="${produtosCategoria_1}">
                    <div class="col-md-3">
                        <div class="card h-100 shadow-sm" style="min-height: 350px;">
                            <img src="img/get/${produto.imagem.nome}" class="card-img-top" alt="${produto.nome}" style="height: 200px; object-fit: cover;">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${produto.nome}</h5>
                                <div class="mt-auto">
                                    <button class="btn btn-primary w-100" onclick="adicionarAoCarrinho(${produto.id}, 1)">Adicionar ao Carrinho</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Exibir categoria 3 e seus produtos -->
    <c:if test="${not empty categoria_2}">
        <div class="category-section mb-5">
            <div class="d-flex justify-content-between align-items-center">
                <h3>${categoria_2.nome}</h3>
                <a href="<c:url value='/produtos?filtro.categoria.id=${categoria_2.id}'/>" class="btn btn-link">Ver todos...</a>
            </div>
            <div class="row">
                <c:forEach var="produto" items="${produtosCategoria_2}">
                    <div class="col-md-3">
                        <div class="card h-100 shadow-sm" style="min-height: 350px;">
                            <img src="img/get/${produto.imagem.nome}" class="card-img-top" alt="${produto.nome}" style="height: 200px; object-fit: cover;">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${produto.nome}</h5>
                                <div class="mt-auto">
                                    <button class="btn btn-primary w-100" onclick="adicionarAoCarrinho(${produto.id}, 1)">Adicionar ao Carrinho</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Exibir categoria 4 e seus produtos -->
    <c:if test="${not empty categoria_3}">
        <div class="category-section mb-5">
            <div class="d-flex justify-content-between align-items-center">
                <h3>${categoria_3.nome}</h3>
                <a href="<c:url value='/produtos?filtro.categoria.id=${categoria_3.id}'/>" class="btn btn-link">Ver todos...</a>
            </div>
            <div class="row">
                <c:forEach var="produto" items="${produtosCategoria_3}">
                    <div class="col-md-3">
                        <div class="card h-100 shadow-sm" style="min-height: 350px;">
                            <img src="img/get/${produto.imagem.nome}" class="card-img-top" alt="${produto.nome}" style="height: 200px; object-fit: cover;">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${produto.nome}</h5>
                                <div class="mt-auto">
                                    <button class="btn btn-primary w-100" onclick="adicionarAoCarrinho(${produto.id}, 1)">Adicionar ao Carrinho</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>
</div>
<tag:footer></tag:footer>
<script>
    function adicionarAoCarrinho(produtoId, quantidade) {
        $.post("<c:url value='/carrinho/adicionar'/>", { produtoId: produtoId, quantidade: quantidade }, function() {
            alert("Produto adicionado ao carrinho!");
        });
    }
</script>
</div>
</body>
</html>
