	//função ao iniciar o mapa
	var myLatLng;
	var markers = [];
	//gabrielfoodtruck
	
    function initMap() {
        
        //cria o mapa centralizado na posição capturada e zoom definido
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15
        });
        
        //pegar localização do navegador
        if (navigator.geolocation) { //verifica se navegador pode ser geolocalizado
            navigator.geolocation.getCurrentPosition(
                function(position) {
                    myLatLng = { //pega localização
                    		lat: position.coords.latitude,
                            lng: position.coords.longitude
                    };
                    //marcador da posição do navegador
                    var marker = new google.maps.Marker({
                        position: myLatLng,
                        map: map,
                        draggable: false
                    });
                    map.setCenter(myLatLng); //centralizar mapa já que não defini antes
                    
                    google.maps.event.addListener(map, "center_changed", 
                		function() {                        
                        	marker.setPosition(map.getCenter());
                        	fnTelaArrastada(map);
                      	}
                    );
                    
                    //simular click do botão de submit para chamar o ajax
                    $("#btnSalvar").trigger("click");
                },
                function() {
                	
                    handleLocationError(true, map); //caso der erro: erro ao se geolocalizar
                }
            );
        } else {
        	
           handleLocationError(false, map); //caso der erro: usando IE, aquele lixo
        	
        }
        
        var infowindow;
        //ajax pra quando entrar
        $(document).on("submit", "#formLocalizacao",
            function(event) {
                var $form = $(this);
                document.getElementById("inputLatitude").value = myLatLng.lat;
                document.getElementById("inputLongitude").value = myLatLng.lng;
                
                //manda requisição
                $.post($form.attr("action"), $form.serialize(),
                    //retorno do post	
                		
                    function(response) {
                        var infoWindow;
                        
                        for (var i = 0; i < response.listaFoodTruck.length; i++) {
                            var foodTruck = response.listaFoodTruck[i];
                            var conteudo = "<div id='content'>" +
				                                "<h5 style='color: #DAB400;font-weight: bolder;'>" + foodTruck.nome + "</h5>" +
				                                "<div id='bodyContent'>" +
				                                	foodTruck.descricao +
				                                "</div>" +
				                                //melhor usar envio por formulario inves de link, pois precisa usar um response e não reenviar requisição a outra tela
				                                "<button type='button' class='btn btn-primary btn-sm' onclick='abreDialog(" + foodTruck.id + ");' data-toggle='modal' data-target='#myModal'>Veja Mais!</button>" +
			                                "</div>";

                            infowindow = new google.maps.InfoWindow({
                                map: map
                            });

                            var image = 'imagens/foodtruck.png';

                            var marker = new google.maps.Marker({
                                position: {
                                    lat: foodTruck.latitude,
                                    lng: foodTruck.longitude
                                },
                                icon: image,
                                content: conteudo
                            });
                            marker.setMap(map);
                            markers.push(marker);
                            
                            google.maps.event.addListener(marker, 'click', (
	                    		function(marker) {
	                                return function() {
	                                    infowindow.setContent(marker.content);
	                                    infowindow.open(map, marker);
	                                }
	                            }
                    		)(marker));
                        }
                    }
                );

                event.preventDefault(); // Important! Prevents submitting the form.
            }
        );

    }

    function handleLocationError(browserHasGeolocation, map) {

    	myLatLng = { //pega localização
                lat: -8.0949982,
                lng: -34.9255618
            };
            //marcador da posição do navegador
            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                draggable: false
            });
            map.setCenter(myLatLng); //centralizar mapa já que não defini antes
            
            google.maps.event.addListener(map, "center_changed", 
        		function() {                        
                	marker.setPosition(map.getCenter());
                	fnTelaArrastada(map);
              	}
            );
            
            //simular click do botão de submit para chamar o ajax
            $("#btnSalvar").trigger("click");
    }

    function abreDialog(id) {
        //setta o id no input
        document.getElementById("inputId").value = id;

        //manda a requisicao
        $("#btnMostrar").trigger("click");
    }

    function fnTelaArrastada(map) {
    	myLatLng.lat = document.getElementById("inputLatitude").value = map.getCenter().lat();
    	myLatLng.lng = document.getElementById("inputLongitude").value = map.getCenter().lng();
    	
//    	console.log(map.getCenter().lat() + ":" + map.getCenter().lng());
    	clearMarkers();
    	$("#btnSalvar").trigger("click");
    }
    
    function setMapOnAll(map) {
	  for (var i = 0; i < markers.length; i++) {
	    markers[i].setMap(map);
	  }
	}

    	// Removes the markers from the map, but keeps them in the array.
	function clearMarkers() {
	  setMapOnAll(null);
	}


    //ajax pra quando entrar
    $(document).on("submit", "#formMostraFoodTruck",
        function(event) {
            var $form = $(this);

            //manda requisição
            $.post($form.attr("action"), $form.serialize(),
                //retorno do post	
                function(response) {
                    //console.log(response);
                    document.getElementById("divFoodTruckPratos").innerHTML = response.pratos;
                }
            );

            event.preventDefault(); // Important! Prevents submitting the form.
        }
    );