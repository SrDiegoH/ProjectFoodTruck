<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Alterar Senha</title>

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
            String foodtruck = (String) request.getAttribute("foodtruck");
            foodtruck = foodtruck == null ? "" : foodtruck.trim();

            Integer id = (Integer) request.getAttribute("id");
            id = id == null ? -1 : id;

            String retorno = (String) request.getAttribute("retorno");
            retorno = retorno == null ? "" : retorno.trim();

            String mensagem = (String) request.getAttribute("mensagem");
            mensagem = mensagem == null ? "" : mensagem.trim();
        %>	    
       	<c:choose>
            <c:when test="${retorno == 'senha'}">
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
		            
			<div class="mobile desktop-hide">
				<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
				    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
				        <i class="material-icons" style="color: #DAB400;">menu</i>
				    </button>
				    
				    <div class="collapse navbar-collapse" id="navbarNavDropdown">
				        <ul class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('alterainformacoes',${id})">Alterar Informações</a></li>                
			                <li class="nav-item"><a class="nav-link active" onclick="fnDirecionaPagina('senha',${id})">Alterar Senha</a></li>
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('prato',${id})">Adicionar Prato</a></li>                
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('buscarprato',${id})">Alterar Prato</a></li>
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('localiza',${id})">Localize-se</a></li>
			                <li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#divConfirm">Sair</a></li>
				        </ul>
				    </div>
				</nav>
			</div>
			
			<div class="mobile-hide">
			    <ul class="nav nav-tabs" role="tablist">
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('alterainformacoes',${id})">Alterar Informações</a></li>                
			        <li class="nav-item"><a class="nav-link active" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('senha',${id})">Alterar Senha</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('prato',${id})">Adicionar Prato</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('buscarprato',${id})">Alterar Prato</a></li>
			        <li class="nav-item"><a class="nav-link active" data-toggle="tab" role="tab" aria-controls="home" onclick="fnDirecionaPagina('localiza',${id})">Localize-se</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="modal" role="tab" aria-controls="home" data-target="#divConfirm">Sair</a></li>	
			    </ul>
			</div>

            <br />

            <div id="skrollr-form">
                <form id="form-atualizacao" action="FachadaAlterarSenha" method="post" style="max-width: 330px; padding: 15px; margin: 0 auto;">
                    <div class="alert alert-danger" role="alert" id="alert"><c:out value="${mensagem}"></c:out></div>
                    <br />
                    <br />					
                    <input type="hidden" name="acao" value="alterar"> 
                    <input type="hidden" id="id" name="id" value="${id}"/>
                    <input type="password" class="form-control" size="20" name="senhaAtual" placeholder="Senha atual" id="senhaAtual" aria-describedby="basic-addon1" required="true" style="border-radius: 4px 4px 0px 0px;">
                    <input type="password" class="form-control" size="20" name="novaSenha" placeholder="Nova senha" id="senha" aria-describedby="basic-addon1" required="true" style="border-radius: 0px 0px 0px 0px;">
                    <input type="password" class="form-control"  size="20" name="confirmarNovaSenha" placeholder="Confirme a nova senha" id="confirmarSenha" aria-describedby="basic-addon1" required="true" style="border-radius: 0px 0px 4px 4px;">
                    <br/>	  				 
                    <div class="btn-toolbar" role="toolbar" aria-label="..." style="display: block;">				
                        <a><input type="submit" id="input-alterar" class="btn btn-primary btn-block" value="Salvar"/></a>
                    </div>
                </form>
            </div>
        </div>


        <br/>

		<div class="modal fade" role="document" id="divConfirm">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Deseja voltar para tela de login?</h4>
					</div>
					<div class="modal-body">
						<p>Ao sair do gerenciamento do Food Truck as informações podem não ser salvas</p>
					</div>
					<div class="modal-footer">
						<input class="btn btn-primary" type="button" data-dismiss="modal" value="Não">
						<a href="login.jsp"><input type="button" class="btn btn-danger" value="Sim"></a>
					</div>
				</div>
			</div>
		</div>   
    </body>
</html>