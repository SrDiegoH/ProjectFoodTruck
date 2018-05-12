<%@page import="java.util.ArrayList"%>
<%@page import="model.Local"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Localize-se</title>

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
        <script type="text/javascript" src="js/mudaAvaliacao.js"></script>
        <script type="text/javascript" src="js/npm.js"></script>
        <script type="text/javascript" src="js/scriptGeral.js"></script> 
        
        <style>
            #map {
                width: 100%;
                height: 400px;		        
            }
        </style>
        
        <script type="text/javascript" >
            
            $(document).ready(function () {
                $('[data-mark="confirmation"]').click(function (ev) {
                    var id = $('#inputId').attr('value');
                    document.getElementById("idLimparLocalizacao").value = id;
                });
                
                $('[data-mark="excluir"]').click(function (ev) {
                	var id = $(this).attr('id');
                    document.getElementById("idExcluirFavorito").value = id;
                });
            });
            
            function pegarLocalizacao() {
                var lat = document.getElementById("inputLatitude").value;
                var lon = document.getElementById("inputLongitude").value;
				var nome = document.getElementById("inputFavorito").value;

				location.href = "FachadaLocal?acao=adicionarNaLista&latitude=" + lat + "&longitude=" + lon + "&nome=" + nome;
            }
        </script>

        <%
            String foodtruck = (String) request.getAttribute("foodtruck");
            foodtruck = foodtruck == null ? "" : foodtruck.trim();

            Double latitude = (Double) request.getAttribute("latitude");
            latitude = latitude == null ? -1 : latitude;

            Double longitude = (Double) request.getAttribute("longitude");
            longitude = longitude == null ? -1 : longitude;

            List<Local> arrayLocais = (List<Local>) request.getAttribute("arrayLocais");
            arrayLocais = arrayLocais == null ? new ArrayList() : arrayLocais;
        %>
    </head>
    <body>
        <div id="skrollr-body">
            <header>
                <h1 style="font-family: cursive;color: #DAB400;"><a href="index.html" style="font-family: cursive;color: #DAB400;"><img src="imagens/logo.png" style="width: 25%;"></a> Food Tracking</h1>
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
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('prato')">Adicionar Prato</a></li>                
			                <li class="nav-item"><a class="nav-link" onclick="fnDirecionaPagina('buscarprato')">Alterar Prato</a></li>
			                <li class="nav-item"><a class="nav-link active" onclick="fnDirecionaPagina('localiza')">Localize-se</a></li>
			                <li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#divConfirm">Sair</a></li>
				        </ul>
				    </div>
				</nav>
			</div>
			
			<div class="mobile-hide">
			    <ul class="nav nav-tabs" role="tablist">
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('alterainformacoes')">Alterar Informações</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('senha')">Alterar Senha</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('prato')">Adicionar Prato</a></li>                
			        <li class="nav-item"><a class="nav-link" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('buscarprato')">Alterar Prato</a></li>
			        <li class="nav-item"><a class="nav-link active" data-toggle="tab"   role="tab" aria-controls="home" onclick="fnDirecionaPagina('localiza')">Localize-se</a></li>
			        <li class="nav-item"><a class="nav-link" data-toggle="modal" role="tab" aria-controls="home" data-target="#divConfirm">Sair</a></li>	
			    </ul>
			</div>
        <br />

        <div class="alert alert-success" id="alertSucesso" role="alert" style="display:none"></div>

        <br />
        <br />
        <div id="map"></div>	   
       	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD1Gl-3mwvp7Pwom8C3V4BLtlStdTgDruM&callback=initMap"></script>
        <br/>

        <form action="FachadaLocalizacao" method="post" id="formLocalizacao" style="max-width: 330px; padding: 15px; margin: 0 auto;">
            <input type="hidden" name="latitude" value="${latitude}" id="inputLatitude">
            <input type="hidden" name="longitude" value="${longitude}" id="inputLongitude">
            <input type="hidden" name="foodTruck" value="${foodtruck}" id="inputFoodTruck">
            <input type="hidden" name="acao" value="salvar">
            <div class="btn-toolbar" role="toolbar" aria-label="..." style="display: inline;">
	            <a><input type="submit" id="btnSalvar" class="btn btn-primary btn-block" value="Salvar"/></a>
	            <a><input type="button" data-mark="confirmation" data-toggle="modal" data-target="#divConfirmLimparLocalizacao" id="bntAdicionar" class="btn btn-danger btn-block" value="Limpar Localização"/></a>
	            <a><input type="button" data-toggle="modal" data-target="#divNomeLocalFavorito" id="bntAdicionar" class="btn btn-secondary btn-block" value="Adicionar aos favoritos"/></a>
            </div>
        </form>

       	<br/>
        <div id="skrollr-form">
            <table id="tabelaRegistros" class="table table-hover table-condensed">
                <thead class="thead-inverse">
                    <tr>
                        <th>Locais Favoritos</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
				<tbody>
	                <c:forEach var="ite" items="<%=arrayLocais%>" varStatus="loop">
	                    <tr>
	                        <td style="color: #DAB400;font-weight: bolder;">
	                            <c:out value="${ite.getNome()}"></c:out>
                            </td>
                            <td>
                                <a class="btn btn-link" href="FachadaLocal?acao=utilizar&id=${ite.getId()}"><i class="material-icons" style="color: #DAB400;">create</i></a>
	                        </td>
	                        <td>
	                            <a class="btn btn-link" data-mark="excluir" data-toggle="modal" data-target="#divConfirmExcluirFavorito" id="${ite.getId()}"><i class="material-icons" style="color: #DAB400;">delete_forever</i></a>
	                        </td>
	                    </tr>
	                </c:forEach>
				</tbody>
            </table>
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
		

		<div class="modal fade" role="document" id="divNomeLocalFavorito">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Digite o nome do local</h4>
					</div>
					<div class="modal-body">
						<input type="text" id="inputFavorito"  class="form-control">
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-primary" onClick="pegarLocalizacao()" value="Adicionar">
						<input type="button" class="btn btn-danger" data-dismiss="modal" value="Cancelar">
					</div>
				</div>
			</div>
		</div>  
				
		<div class="modal fade" role="document" id="divConfirmLimparLocalizacao">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Remover localização?</h4>
					</div>
					<div class="modal-body">
						<p>Se limpar a localização ninguém irá encontra-lo.</p> 
					</div>					
					<div class="modal-footer"> 
						<form action="FachadaLocalizacao" method="get" id="formLocalizacao2"> 
							<input type="hidden" name="id" value="id" id="idLimparLocalizacao"/> 
							<input type="hidden" name="acao" value="apagar"/> 
							
							<input class="btn btn-primary" type="button" data-dismiss="modal" value="Não"/>
							<input class="btn btn-danger" id="btnSalvar" type="submit" value="Sim"/>
						</form> 
					</div>
				</div>
			</div>
		</div>
				
		<div class="modal fade" role="document" id="divConfirmExcluirFavorito">
			<div class="modal-dialog modal-dialog-centered" role="document"> 
				<div class="modal-content">
					<div class="modal-header">
						<h4>Deseja excluir este favorito?</h4>
					</div>
					<div class="modal-body">
						<p>Este favorito não poderá mais ser recuperado.</p> 
					</div>					
					<div class="modal-footer"> 
						<form action="FachadaLocal" method="get" id="formLocalizacao3"> 
							<input type="hidden" name="id" value="id" id="idExcluirFavorito"/> 
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