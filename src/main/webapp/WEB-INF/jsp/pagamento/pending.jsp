<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="utf-8" />
    <title>Pagamento - Pendente</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <link href="css/styles.css" rel="stylesheet" />
</head>

<body id="page-top">
    <tag:menu-superior></tag:menu-superior>
    <header class="masthead" id="pending">
        <div class="container">
            <h2 class="text-uppercase text-white">Pagamento Pendente</h2>
            <p class="text-white">Estamos processando seu pagamento. Por favor, aguarde a confirmação.</p>
        </div>
    </header>
    <tag:footer></tag:footer>
</body>
</html>
