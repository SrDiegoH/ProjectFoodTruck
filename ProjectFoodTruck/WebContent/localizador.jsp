<%@page import="java.util.ArrayList"%>
<%@page import="model.FoodTruck"%>
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
        <script type="text/javascript" src="js/mapGeral.js"></script>
        <script type="text/javascript" src="js/mudaAvaliacao.js"></script>
        <script type="text/javascript" src="js/npm.js"></script>
        <script type="text/javascript" src="js/scriptGeral.js"></script>

        <style>
            #map {
                width: 100%;
                height: 500px;
            }
        </style>
    </head>
    <body>
        <div id="skrollr-body">
            <header>
                <h1 style="font-family: cursive;color: #DAB400;"><a href="index.html"><img src="imagens/logo.png" style="width: 25%;"></a> Food Tracking</h1>
            </header>
        </div>
        <div id="map"></div>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD1Gl-3mwvp7Pwom8C3V4BLtlStdTgDruM&callback=initMap"></script>
        <br />
        <form action="FachadaLocalGeral" method="post" id="formLocalizacao">
            <input type="hidden" name="latitude" value="${latitude}" id="inputLatitude">
            <input type="hidden" name="longitude" value="${longitude}" id="inputLongitude">
            <button style="display: none;" id="btnSalvar" type="submit"></button>
        </form>
        <br />
        <form action="FachadaMostrarFoodtruck" method="post" id="formMostraFoodTruck">
            <input type="hidden" name="id" value="${id}" id="inputId">
            <button style="display: none;" id="btnMostrar" type="submit"></button>
        </form>
        <br />
        <form action="FachadaAvaliacao" method="post" id="formAvaliacao">
            <input type="hidden" name="id" value="" id="idPrato">
            <input type="hidden" name="fk" value="" id="idFoodTruck">
            <input type="hidden" name="operacao" value="" id="operacao">
            <input type="hidden" name="quemAvaliar" value="" id="quemAvaliar">
            <button style="display: none;" id="btnAvalia" type="submit"></button>
        </form>
        <br /> 

        <!-- Modal -->
        <div class="modal fade bd-example-modal-lg" id="myModal" role="dialog">
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content" id="divFoodTruckPratos">
                </div>
            </div>
        </div>
        <!--Modal-->

    </body>
</html>

