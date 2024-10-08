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

        /* Se precisar ajustar a navbar colapsada */
        .navbar-collapse {
            padding-top: 30px;  /* Ajuste conforme necessário */
            padding-bottom: 30px; /* Ajuste conforme necessário */
        }
    </style>
</head>

<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">
            <img src="assets/img/logos/logo2.png" alt="Logo2" style="margin-right: 10px; width: 80px; height: auto"/>
            <img src="assets/img/logos/logo.png" alt="Logo" style="margin-right: 10px; width: 400px; height: auto" />
        </a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars ml-1"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <c:if test="${ usuarioLogado != null}">
                <ul class="navbar-nav text-uppercase ml-auto">
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='categorias'/>">Categorias</a></li>
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='produtos'/>">Produtos</a></li>
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='logout'/>">Sair</a></li>
                </ul>
            </c:if>
            <c:if test="${ usuarioLogado == null}">
                <ul class="navbar-nav text-uppercase ml-auto">
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='produtos'/>">Produtos</a></li>
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='login'/>">Login</a></li>
                    <li class="nav-item"><a class="nav-link js-scroll-trigger" href="<c:url value='cadastrar'/>">Cadastrar-se</a></li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>
