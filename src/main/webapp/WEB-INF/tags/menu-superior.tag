<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
    <style>
        /* Aumentar altura da navbar */
        #mainNav {
            height: 125px; /* Defina a altura desejada */
        }

        /* Ajustar o padding dos itens da navbar */
        .navbar-nav .nav-item .nav-link {
            padding-top: 30px;  /* Ajuste o padding superior */
            padding-bottom: 30px; /* Ajuste o padding inferior */
        }

        /* Centralizar os itens da navbar */
        .navbar-nav {
            display: flex;
            justify-content: center; /* Centraliza os itens horizontalmente */
            width: 100%;
        }

        /* Estilo para os campos de email e senha */
        .form-inline {
            display: flex;
            align-items: center;
        }

        .form-inline input[type="email"],
        .form-inline input[type="password"] {
            width: 180px; /* Tamanho reduzido */
            margin-right: 20px; /* Espaço entre os campos */
            padding: 5px; /* Ajuste do padding interno */
        }

        .form-inline button {
            padding: 5px 10px;
            background-color: #007bff; /* Cor de fundo do botão */
            color: white;
            border: none;
            cursor: pointer;
        }

        .form-inline button i {
            margin-right: 5px; /* Espaço antes do ícone */
        }
    </style>
</head>

<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">
            <img src="assets/img/logos/logo2.png" alt="Logo2" style="margin-right: 10px; width: 80px; height: auto"/>
        </a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars ml-1"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav text-uppercase mx-auto">
                <!-- Exibir o botão Categorias apenas se o usuário logado for ADMIN -->
                <c:if test="${usuarioLogado.tipoUsuario == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value='usuarios'/>">Usuários</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value='categorias'/>">Categorias</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="<c:url value='produtos'/>">Produtos</a>
                </li>

                <!-- Verificar se o usuário NÃO está logado -->
                <c:if test="${usuarioLogado == null}">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value='cadastrar'/>">Cadastro</a>
                    </li>
                </c:if>

                <!-- Verificar se o usuário está logado -->
                <c:if test="${usuarioLogado == null}">
                    <!-- Campos de login se o usuário não estiver logado -->
                    <form class="form-inline ml-3" action="<c:url value='login/autenticar'/>" method="post">
                        <input class="form-control mr-2" type="email" name="email" placeholder="Email" required>
                        <input class="form-control mr-2" type="password" name="senha" placeholder="Senha" required>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt"></i>
                        </button>
                    </form>
                </c:if>
				
				<!-- Exibir o botão Categorias apenas se o usuário logado for USER -->
                <c:if test="${usuarioLogado.tipoUsuario == 'USER'}">
                    <li class="nav-item">
                    	<a class="nav-link js-scroll-trigger" href="<c:url value='carrinho'/>"><i class="fas fa-shopping-cart"></i> Carrinho</a>
                    </li>
				</c:if>
				
                <!-- Ícone de sair se o usuário estiver logado -->
                <c:if test="${usuarioLogado != null}">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value='logout'/>">
                            <i class="fas fa-sign-out-alt"></i> Sair
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
