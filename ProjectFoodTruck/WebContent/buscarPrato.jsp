<%@page import="java.util.ArrayList"%>
<%@page import="model.Prato"%>
<%@page import="java.util.List"%>
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
        
        <script type="text/javascript" >
            $(document).ready(function () {
                $('[data-mark="confirmation"]').click(function (ev) {
                    var id = $(this).attr('id');

                    document.getElementById("idExcluirPrato").value = id;
                });
                
                $('[data-mark="edit"]').click(function (ev) {
                    var id = $(this).attr('id');
                    fnDirecionaPagina('alterarprato', id);
                });
            });
        </script>

        <%
            String foodtruck = (String) request.getAttribute("foodtruck");
            foodtruck = foodtruck == null ? "" : foodtruck.trim();

            Integer id = (Integer) request.getAttribute("id");
            id = id == null ? -1 : id;

            List<Prato> arrayPratos = (List<Prato>) request.getAttribute("arrayPratos");
            arrayPratos = arrayPratos == null ? new ArrayList() : arrayPratos;
        %>	    
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
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('senha',${id})">Alterar Senha</a></li>
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('prato',${id})">Adicionar Prato</a></li>                
			                <li class="nav-item"><a class="nav-link active" onclick="fnDirecionaPagina('buscarprato',${id})">Alterar Prato</a></li>
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('localiza',${id})">Localize-se</a></li>
			                <li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#divConfirm">Sair</a></li>
				        </ul>
				    </div>
				</nav>
			</div>
			
			<div class="mobile-hide">
			    <ul class="nav nav-tabs" role="tablist">
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('alterainformacoes',${id})">Alterar Informações</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('senha',${id})">Alterar Senha</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('prato',${id})">Adicionar Prato</a></li>                
			        <li class="nav-item"><a class="nav-link active" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('buscarprato',${id})">Alterar Prato</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="tab" role="tab" aria-controls="home" onclick="fnDirecionaPagina('localiza',${id})">Localize-se</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="modal" role="tab" aria-controls="home" data-target="#divConfirm">Sair</a></li>	
			    </ul>
			</div>

            <div id="skrollr-form">
                <table id="tabelaRegistros" class="table table-hover table-condensed">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Nome</th>
                            <th>Alterar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
	                    <c:forEach var="ite" items="<%=arrayPratos%>" varStatus="loop">
	                        <tr>
	                            <td style="color: #DAB400;font-weight: bolder;">
									<c:out value="${ite.getNome()}"></c:out>
								</td>
								<td>
	                            	<button data-mark="edit" class="btn btn-link" id="${ite.getId()}"><i class="material-icons" style="color: #DAB400;">create</i></button>
								</td>
	                            <td>
	                                <button data-mark="confirmation" data-toggle="modal" data-target="#divConfirmExcluirPrato" class="btn btn-link" id="${ite.getId()}"><i class="material-icons" style="color: #DAB400;">delete_forever</i></button>
	                            </td>
	                        </tr>
	                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <br/>

		<div class="modal fade" role="document" id="divConfirm">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Seja voltar para tela de login?</h4>
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
		
		<div class="modal fade" role="document" id="divConfirmExcluirPrato">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Deseja excluir este Prato?</h4>
					</div>
					<div class="modal-body">
						<p>Este prato não poderá mais ser recuperado.</p> 
					</div>					
					<div class="modal-footer"> 
						<form action="FachadaAlterarPratos" method="get" id="FachadaAlterarPratos"> 
							<input type="hidden" name="id" value="id" id="idExcluirPrato"/> 
							<input type="hidden" name="acao" value="excluir"/> 
							
							<input class="btn btn-primary" type="button" data-dismiss="modal" value="Não"/>
							<input class="btn btn-danger" id="btnSalvar" type="submit" value="Sim"/>
						</form> 
					</div>
				</div>
			</div>
		</div>  
    </body>
</html>
