<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>E-Commerce - Carrinho</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <!-- Font Awesome icons (free version)-->
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet"
        type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
</head>

<body id="page-top">
    <!-- Navigation-->
    <tag:menu-superior></tag:menu-superior>

    <!-- Masthead-->
    <header class="masthead" id="login">
        <div class="container">
            <h2 class="text-uppercase text-white">Carrinho de Compras</h2>
        </div>
    </header>

    <!-- Conteúdo do Carrinho-->
    <section class="page-section bg-dark text-white" id="portfolio">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3 class="section-subheading text-muted">Confira os produtos adicionados ao seu carrinho</h3>
                </div>
            </div>
            
            <!-- Lista de itens no carrinho -->
            <c:forEach var="item" items="${itens}">
                <div class="row my-3 p-3 border rounded bg-light text-dark">
                    <div class="col-md-4">
                        <img src="img/get/${item.produto.imagem.nome}" class="img-fluid" alt="${item.produto.nome}" />
                    </div>
                    <div class="col-md-5">
                        <h4>${item.produto.nome}</h4>
                        <p>Quantidade: ${item.quantidade}</p>
                        <p>Preço unitário: ${item.produto.valorMoney}</p>
						<!-- Exibir o preço total diretamente -->
    					<p>Preço total: R$ <fmt:formatNumber value="${item.precoTotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/></p>
                    </div>
                    <div class="col-md-3 d-flex flex-column justify-content-center">
                        <form method="post" action="carrinho/atualizarQuantidade" class="mb-2">
                            <input type="hidden" name="produtoId" value="${item.produto.id}" />
                            <input type="number" name="quantidade" value="${item.quantidade}" class="form-control" />
                            <button type="submit" class="btn btn-primary btn-block mt-2">Atualizar</button>
                        </form>
                        <form method="post" action="carrinho/remover">
                            <input type="hidden" name="produtoId" value="${item.produto.id}" />
                            <button type="submit" class="btn btn-danger btn-block">Remover</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
            <!-- Exibe o subtotal -->
			<div class="row mt-4">
	    		<div class="col-md-12 text-right">
	        		<h4>Subtotal: R$ <fmt:formatNumber value="${subtotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/></h4>
	        		<a href="<c:url value="finalizar" />" class="btn btn-success btn-lg">Finalizar Compra</a>
   				</div>
        	</div>
    </section>

    <tag:footer></tag:footer>

    <!-- Bootstrap core JS-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Third party plugin JS-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
    <!-- Contact form JS-->
    <script src="assets/mail/jqBootstrapValidation.js"></script>
    <script src="assets/mail/contact_me.js"></script>
    <!-- Core theme JS-->
    <script src="js/scripts.js"></script>
</body>

</html>
