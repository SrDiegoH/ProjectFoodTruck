<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Atualizar</title>

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

            String prato = (String) request.getAttribute("prato");
            prato = prato == null ? "" : prato.trim();

            String descricaoPrato = (String) request.getAttribute("descricaoPrato");
            descricaoPrato = descricaoPrato == null ? "" : descricaoPrato.trim();

            String preco = (String) request.getAttribute("preco");
            preco = preco == null ? "" : preco.trim().replace(".", ",");
        %>

        <c:choose>
            <c:when test="${retorno == 'preco'}">
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
                <h1 style="font-family: cursive;color: #DAB400;"><a data-toggle="modal" data-target="#divConfirm" style="font-family: cursive;color: #DAB400;"><img src="imagens/logo.png" style="width: 25%;"></a> Food Tracking</h1>
            </header>

			<div class="mobile desktop-hide">
				<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
				    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
				        <i class="material-icons" style="color: #DAB400;">menu</i>
				    </button>
				    
				    <div class="collapse navbar-collapse" id="navbarNavDropdown">
				        <ul class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('alterainformacoes')">Alterar Informações</a></li>                
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('senha')">Alterar Senha</a></li>
			                <li class="nav-item"><a class="nav-link active" onclick="fnDirecionaPagina('prato')">Adicionar Prato</a></li>                
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('buscarprato')">Alterar Prato</a></li>
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('localiza')">Localize-se</a></li>
			                <li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#divConfirm">Sair</a></li>
				        </ul>
				    </div>
				</nav>
			</div>
			
			<div class="mobile-hide">
			    <ul class="nav nav-tabs" role="tablist">
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('alterainformacoes')">Alterar Informações</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('senha')">Alterar Senha</a></li>
			        <li class="nav-item"><a class="nav-link active" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('prato')">Adicionar Prato</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('buscarprato')">Alterar Prato</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('localiza')">Localize-se</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="modal" role="tab" aria-controls="home" data-target="#divConfirm">Sair</a></li>	
			    </ul>
			</div>
	        
	        
            <div id="skrollr-form">
                <form id="form-cadastrar-prato" action="FachadaCadastrarPratos" method="post" style="max-width: 330px; padding: 15px; margin: 0 auto;">
                    <div class="alert alert-danger" role="alert" id="alert"><c:out value="${mensagem}"></c:out></div>
                    <br/>
                    <br/> 
                    <input type="hidden" name="acao" value="cadastro"/>
                    <input type="text" class="form-control" size="20" value="${prato}" name="prato" placeholder="Nome do prato" id="prato" maxlength="100" aria-describedby="basic-addon1" autofocus="true" required="true" style="border-radius: 4px 4px 0px 0px;"/>
                    <input type="number" class="form-control" size="20" value="${preco}" name="preco" placeholder="Preço" id="preco" maxlength="100" aria-describedby="basic-addon1" required="true" step="any" style="border-radius: 0px 0px 0px 0px;"/>
                    <textarea style="resize: none;border-radius: 0px 0px 4px 4px;" class="form-control" rows="5" name="descricaoPrato" placeholder="Descrição (Não Obrigatório)" id="descricaoPrato"  maxlength="500"><c:out value="${descricaoPrato}"></c:out></textarea>
                    <br/>
                    <div class="btn-toolbar" role="toolbar" aria-label="..." style="display: block;">					
                        <a><input type="submit" id="input-cadastrar" class="btn btn-primary btn-block" value="Cadastrar"/></a>
                        <a><input type="reset" value="Limpar" class="btn btn-secondary btn-block" /></a>
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
						<input class="btn btn-danger"  type="button" data-dismiss="modal" value="Sim" onclick="location.href = 'FachadaNavegacao?acao=sair';">
					</div>
				</div>
			</div>
		</div> 
    </body>
</html>