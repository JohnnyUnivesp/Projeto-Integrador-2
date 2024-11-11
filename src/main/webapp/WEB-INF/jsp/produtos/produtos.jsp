<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>E-Commerce - Produtos</title>
  <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
  <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
  <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet"
    type="text/css" />
  <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
  <link href="css/styles.css" rel="stylesheet" />
</head>

<body id="page-top">
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
    	
    	<style>
			width: 40%!important;    	
    		border-radius: 50px!important;
    		border: 5px solid #ffffff1f!important;
    	</style>
  
  <header class="masthead" id="login">
    <div class="container"></div>
  </header>
  <section class="page-section bg-light" id="portfolio">
    <div class="container">
      <div>
        <form method="get" action="<c:url value="produtos" />">
          <div class="row mb-5">
            <div class="col-md-6">
              <div class="form-group">
                <input name="filtro.nome" value="${filtro.nome}" class="form-control" id="email" type="text" placeholder="Pesquisar.."
                  data-validation-required-message="Please enter your email address." />
                <p class="help-block text-danger"></p>
              </div>
            </div>
            <div class="col-md-3">
              <div class="form-group">
                <select name="filtro.categoria.id" class="form-control" id="email"
                  data-validation-required-message="Please enter your email address.">
                 	<option value="">(Todos produtos)</option>
                  <c:forEach var="categoria" items="${categorias}">
                  	<option ${(filtro != null && filtro.categoria != null && categoria.id == filtro.categoria.id) ?"selected":""} value="${categoria.id}">${categoria.nome}</option>
                  </c:forEach> 
                  
                </select>
                <p class="help-block text-danger"></p>
              </div>
            </div>
            <div class="col-md-3">
              <div class="form-group mb-md-0">
                <button type="submit" class="btn btn-danger btn-xl text-uppercase js-scroll-trigger">Pesquisar</button>
              </div>
            </div>
          </div>
         <div class="row mb-2 ml-1">
		    <c:if test="${not empty sessionScope.usuarioLogado}">
		        <c:choose>
		            <c:when test="${sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
		                <button title="Adicionar Produto" onclick="window.location.href='formproduto'" class="btn btn-success mb-3" type="button">
		                    <i class="fas fa-plus mr-1"></i>
		                    Novo Produto
		                </button>
		            </c:when>
		            <c:otherwise>
		            </c:otherwise>
		        </c:choose>
		    </c:if>
		</div>

        </form>
      </div>
      <div class="row">
      	
      	<c:set var="contador" value="0" />
      	
      	<c:forEach var="produto" items="${produtos}">   
      	
      	<c:set var="contador" value="${contador + 1}" />
      	
        <div class="col-lg-4 col-sm-6 mb-4" style="min-height: 350px;">
          <div class="portfolio-item" style="background-color: #ffffff;">
            <a class="portfolio-link" data-toggle="modal" href="#portfolioModal${produto.id}">
              <div class="portfolio-hover">
                <div class="portfolio-hover-content">
                  <i class="fas fa-plus fa-3x"></i>
                </div>
              </div>
				<div class="d-flex justify-content-center">
                <img class="img-fluid" style="height: 200px; object-fit: cover;" src="img/get/${produto.imagem.nome}" alt="" />
            </div>            
            </a>
            <div class="portfolio-caption">
              <div class="portfolio-caption-heading">${produto.valorMoney}</div>
              <div class="portfolio-caption-subheading text-muted">
                ${produto.nome}
              </div>
            </div>
          </div>
        </div>

			<div class="portfolio-modal modal fade" id="portfolioModal${produto.id}" tabindex="-1" role="dialog" aria-hidden="true">
			    <div class="modal-dialog mx-auto" style="max-width: 700px;">
			        <div class="modal-content">
			            <div class="close-modal" data-dismiss="modal">
			                <img src="assets/img/close-icon.svg" alt="Close modal" />
			            </div>
			            <div class="container">
			                <div class="row justify-content-center">
			                    <div class="col-lg-10">
			                        <div class="modal-body p-4">
			                            <img class="img-fluid d-block mx-auto img-modal mb-4" src="img/get/${produto.imagem.nome}" alt="${produto.nome}" />
			                            <h2 class="text-uppercase text-center mb-3">${produto.valorMoney}</h2>
			                            <p class="item-intro text-muted text-center mb-3">
			                                ${produto.nome}
			                            </p>
			                            <hr />
			                            <p class="product-description text-justify" style="white-space: pre-line;">
			                                ${produto.descricao}
			                            </p>
			                            <ul class="list-inline mt-4">
			                                <li>Estoque disponível: ${produto.quantidadeEstoque} unidade(s)</li>
			                            </ul>
			                            <div class="d-flex justify-content-center mt-4">
			                                <c:if test="${usuarioLogado != null && usuarioLogado.tipoUsuario == 'USER'}">
			                                    <button class="btn btn-primary" onclick="adicionarAoCarrinho(${produto.id}, 1)">Adicionar ao Carrinho</button>
			                                </c:if>
			                                <c:if test="${usuarioLogado == null}">
			                                    <a href="<c:url value='cadastrar'/>"><button class="btn btn-primary">Adicionar ao Carrinho</button></a>
			                                </c:if>
			                                <c:if test="${usuarioLogado != null && usuarioLogado.tipoUsuario == 'ADMIN'}">
			                                    <button class="btn btn-info ml-2" onclick="window.location.href='formproduto?produto.id=${produto.id}'">Editar</button>
			                                    <button class="btn btn-danger ml-2" onclick="window.location.href='deletaproduto/${produto.id}'">Deletar</button>
			                                </c:if>
			                            </div>
			                        </div>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
		</c:forEach>
      </div>
      <p>Total de Produtos: ${contador}</p>
    </div>
  </section>

  <tag:footer></tag:footer>
  <script>
    function adicionarAoCarrinho(produtoId, quantidade) {
        $.post("<c:url value='/carrinho/adicionar'/>", { produtoId: produtoId, quantidade: quantidade }, function() {
            alert("Produto adicionado ao carrinho!");
        });
    }
</script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
  <script src="assets/mail/jqBootstrapValidation.js"></script>
  <script src="assets/mail/contact_me.js"></script>
  <script src="js/scripts.js"></script>
  <script></script>
</body>

</html>