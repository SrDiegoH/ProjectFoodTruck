<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Login</title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="css/bootstrap-grid.css">
        <link rel="stylesheet" href="css/bootstrap-reboot.css">
        <link rel="stylesheet" href="css/bootstrap-theme.css">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/cadastro.css">
        <link rel="stylesheet" href="css/demo.css">
        <link rel="stylesheet" href="css/estiloGeral.css">
        <link rel="stylesheet" href="css/genrenciar.css">
        <link rel="stylesheet" href="css/header.css">
        <link rel="stylesheet" href="css/index.css">
        <link rel="stylesheet" href="css/login.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>		
        <script type="text/javascript" src="js/bootstrap.bundle.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.js.map"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/bootstrap.js.map"></script>
        <script type="text/javascript" src="js/map.js"></script>
        <script type="text/javascript" src="js/mapGeral.js"></script>
        <script type="text/javascript" src="js/mudaAvaliacao.js"></script>
        <script type="text/javascript" src="js/npm.js"></script>
        <script type="text/javascript" src="js/scriptGeral.js"></script>

        <%
            String retorno = (String) request.getAttribute("retorno");
            retorno = retorno == null ? "" : retorno.trim();
            
            String mensagem = (String) request.getAttribute("mensagem");
            mensagem = mensagem == null ? "" : mensagem.trim();
        %>		

        <c:choose>
            <c:when test="${retorno == 'login'}">
                <style type="text/css">
                    .alert{
                        display: inline;
                    }	      	
                </style>				
            </c:when>
            <c:otherwise>
                <style type="text/css">
                    .alert{
                        display: none;
                    }	
                </style>
            </c:otherwise>
        </c:choose>		 		
    </head>
    <body>
        <div id="skrollr-body">
            <header>
                <h1 style="font-family: cursive;color: #DAB400;"><a href="index.html"><img src="imagens/logo.png" style="width: 25%;"></a> Food Tracking</h1>
            </header>

            <div id="skrollr-form">
                <form id="form-login" action="FachadaLogin" method="post" class="form-signin">
                    <h2 class="form-signin-heading">Login</h2>
                    <br />
                    <div class="alert alert-danger" role="alert" id="alert"><c:out value="${mensagem}"></c:out></div>
                    <br />
                    <br />		
                    <input type="hidden" name="acao" value="cadastro">
                    <input type="email" id="email" name="email" class="form-control" size="20" placeholder="Email" required="true"  autofocus="true" />
                    <input type="password" id="senha" name="senha" class="form-control" size="20" placeholder="Senha" required="true"  />
                    <div class="btn-toolbar" role="toolbar" aria-label="..." style="display: block;">
                        <a><input type="submit" value="Login" class="btn btn-primary btn-block" /></a>
                        <a href="index.html"><input type="button" value="Cancelar" class="btn btn-danger btn-block" /></a>
                        <a href="recuperarSenha.jsp"><input type="button" value="Esqueceu sua senha?" class="btn btn-secondary btn-block" /></a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
