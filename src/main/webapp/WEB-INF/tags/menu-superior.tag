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
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <link href="css/styles.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="<c:url value='./'/>">
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
                        <a class="nav-link js-scroll-trigger" href="<c:url value='vendas'/>">Vendas</a>
                    </li>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <!-- Contact form JS-->
        <script src="assets/mail/jqBootstrapValidation.js"></script>
        <script src="assets/mail/contact_me.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
</nav>
