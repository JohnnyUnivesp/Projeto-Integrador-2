<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Listar Vendas</title>
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
<body>
    <!-- Navigation-->
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
  
  <!-- Masthead-->
  <header class="masthead" id="login">
    <div class="container"></div>
  </header>

    <div class="container mt-4">
        <h2 class="mb-3">Lista de Vendas</h2>

        <!-- Formulário de Busca e Filtro de Status -->
        <form action="vendas" method="get" class="form-inline mb-4">
            <input type="text" name="filtro" class="form-control mr-2" style="width: 350px;" placeholder="Buscar por nome ou ID de pagamento" value="${filtro}">
            
            <select name="status" class="form-control mr-2">
                <option value="">Filtrar por status</option>
                <option value="Em processamento" ${status == 'Em processamento' ? 'selected' : ''}>Em processamento</option>
                <option value="Enviado" ${status == 'Enviado' ? 'selected' : ''}>Enviado</option>
                <option value="Entregue" ${status == 'Entregue' ? 'selected' : ''}>Entregue</option>
            </select>
            
            <button type="submit" class="btn btn-primary">Buscar</button>
        </form>

        <!-- Tabela de Vendas -->
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>ID do Pagamento</th>
                    <th>Comprador</th>
                    <th>Data</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="venda" items="${vendas}">
                    <tr>
                        <td><a href="#modal-${venda.paymentId}" data-toggle="modal">${venda.paymentId}</a></td>
                        <td>${venda.compradorNome}</td>
                        <td>${venda.dataPagamentoFormatada}</td>
                        <td>${venda.status}</td>
                        <td>
                            <button class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-${venda.paymentId}">
                                Detalhes
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Paginação -->
        <div style="display:flex;justify-content:center">
        <c:if test="${paginaAtual <= 1}">
			        <span class="btn btn-secondary disabled" style="pointer-events: none; opacity: 0.6;">Anterior</span>
			    </c:if>
			    
			    <c:if test="${paginaAtual > 1}">
			        <a href="<c:url value='vendas?page=${paginaAtual - 1}&filtro=${filtro}&status=${status}'/>" class="btn btn-secondary">Anterior</a>
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
				            <a href="<c:url value='vendas?page=${i}&filtro=${filtro}&status=${status}'/>" class="btn btn-light">${i}</a>
				        </c:if>
				    </c:forEach>	
			
			    <c:if test="${paginaAtual < totalPaginas}">
			        <a href="<c:url value='usuarios?page=${paginaAtual + 1}&filtro=${filtro}&status=${status}'/>" class="btn btn-secondary">Próxima</a>
			    </c:if>
			    
			    <c:if test="${paginaAtual >= totalPaginas}">
			        <span class="btn btn-secondary disabled" style="pointer-events: none; opacity: 0.6;">Próxima</span>
			    </c:if>
			</div>
        <!-- Modal para Detalhes da Venda -->
        <c:forEach var="venda" items="${vendas}">
            <div class="modal fade" id="modal-${venda.paymentId}" tabindex="-1" role="dialog" aria-labelledby="modalLabel-${venda.paymentId}" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Detalhes da Venda - ID ${venda.paymentId}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Dados do Comprador e Endereço -->
                            <h6>Dados do Comprador</h6>
                            <p>Nome: ${venda.compradorNome}</p>
                            <p>Telefone: ${venda.compradorTelefone}</p>
                            <p>Endereço: ${venda.enderecoComprador}</p>

                            <!-- Atualizar Status e Rastreio -->
                            <form action="<c:url value='vendas/atualizarStatus'/>" method="post" class="mt-4">
                                <input type="hidden" name="vendaId" value="${venda.paymentId}">
                                
                                <div class="form-group">
                                    <label for="novoStatus">Status da Venda:</label>
                                    <select name="novoStatus" id="novoStatus" class="form-control" onchange="toggleCodigoRastreio(this.value)">
                                        <option value="Em processamento" ${venda.status == 'Em processamento' ? 'selected' : ''}>Em processamento</option>
                                        <option value="Enviado" ${venda.status == 'Enviado' ? 'selected' : ''}>Enviado</option>
                                        <option value="Entregue" ${venda.status == 'Entregue' ? 'selected' : ''}>Entregue</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="codigoRastreio">Código de Rastreio:</label>
                                    <input type="text" id="codigoRastreio" value=${venda.codigoRastreio} name="codigoRastreio" class="form-control" ${venda.status != 'Enviado' ? 'disabled' : ''}>
                                </div>

                                <button type="submit" class="btn btn-success">Salvar</button>
                            </form>

                            <!-- Itens da Venda -->
                            <h6 class="mt-4">Itens da Venda</h6>
                            <div class="row">
                                <c:forEach var="item" items="${venda.itens}">
                                    <div class="col-md-4 text-center mb-3">
                                        <img src="img/get/${item.produto.imagem.nome}" alt="${item.produto.nome}" class="img-thumbnail" width="100">
                                        <p>${item.produto.nome}</p>
                                        <p>Categoria: ${item.produto.categoria.nome}</p>
                                        <p>Quantidade: ${item.quantidade}</p>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <tag:footer />

    <!-- Scripts necessários -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/scripts.js"></script>
    <script>
        function toggleCodigoRastreio(status) {
            document.querySelector("input[name='codigoRastreio']").disabled = (status !== "Enviado");
        }
    </script>
</body>
</html>
