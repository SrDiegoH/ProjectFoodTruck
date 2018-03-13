		
		//função ao iniciar o mapa
		var marker;
	    function initMap() {
	    	var myLatLng;
	    	
	    	//cria o mapa centralizado na posição capturada e zoom definido
	        var map = new google.maps.Map(document.getElementById('map'), {
	            zoom: 17
	        });
            
            //pegar inputs de lat e log
	        inputLatitude = document.getElementById("inputLatitude").value;
	    	inputLongitude = document.getElementById("inputLongitude").value;
	    	
	    	console.log("yay " + inputLatitude + ", " + inputLongitude + " (" + (inputLatitude != 0.0 && inputLongitude != 0.0) + ")");
	    	
	        if (inputLatitude != 0.0 && inputLongitude != 0.0) { //se ja tiver localização definida
	            myLatLng = {
	                lat: parseFloat(inputLatitude),
	                lng: parseFloat(inputLongitude)
	            };
	            
	            map.setCenter(myLatLng); //centralizar mapa com local ja definido
	            
	            criarMarker(myLatLng, map); //cria marcador
	        } else if (navigator.geolocation) { //verifica se navegador pode ser geolocalizado
	                navigator.geolocation.getCurrentPosition (
	            		function(position) {
	            			myLatLng = {  //pega localização
		                        lat: position.coords.latitude,
		                        lng: position.coords.longitude
		                    };
		                					            
		                	map.setCenter(myLatLng); //centralizar mapa com local do navegador
		                	
		                	criarMarker(myLatLng, map); //cria marcador
	            		}, function() {            			
	            			handleLocationError(true, map); //caso der erro: erro ao se geolocalizar
	                	}
	                );
	    	} else { //se não estiver previamente localizado e não poder pegar do navegador setta valores aleatorios
	            myLatLng = {
	                lat: -25.363,
	                lng: 131.044
	            };
	            	            
	            map.setCenter(myLatLng); //centralizar mapa para local fixo no meio do nada
	            criarMarker(myLatLng, map); //cria marcador
	        }
	        

	        map.addListener('click', function(event) {	        	
	        	marker.setMap(null);
	        	criarMarkerEspecifico(event.latLng, map);
	        });
	    }

	    //essa função pega todos os que recebem "myLatLng" pois os atributos não são funções 
	    function criarMarker(myLatLng, map){
	    	mostraObjeto(myLatLng);
	    	//salvar posição inicial
	    	document.getElementById("inputLatitude").value = myLatLng.lat;
    		document.getElementById("inputLongitude").value = myLatLng.lng;
    		console.log("Inicial: " + document.getElementById("inputLatitude").value + ', ' + document.getElementById("inputLongitude").value);
    		
    		//criar market com posição definida
            marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                title: document.getElementById("inputFoodTruck").value,
                draggable: true,
                animation: google.maps.Animation.DROP,
            });
            
            //salva posição ao soltar o arrastar
            marker.addListener('dragend', 
        		function(event) {
            		document.getElementById("inputLatitude").value = event.latLng.lat();
            		document.getElementById("inputLongitude").value = event.latLng.lng();
            		
            		console.log("Novo: " + document.getElementById("inputLatitude").value + ', ' + document.getElementById("inputLongitude").value);
            	}
            );
	    }
	    
	    //essa função pega todos os que recebem "event.LatLng" pois os atributos são funções e se por apenas "lat/lng" vai retornar a poha de um bloco de codigo
	    function criarMarkerEspecifico(eventLatLng, map){
	    	mostraObjeto(eventLatLng);
	    	//salvar posição inicial
	    	document.getElementById("inputLatitude").value = eventLatLng.lat();
    		document.getElementById("inputLongitude").value = eventLatLng.lng();
    		console.log("Inicial: " + document.getElementById("inputLatitude").value + ', ' + document.getElementById("inputLongitude").value);
    		
    		//criar market com posição definida
            marker = new google.maps.Marker({
                position: eventLatLng,
                map: map,
                title: document.getElementById("inputFoodTruck").value,
                draggable: true,
                animation: google.maps.Animation.DROP,
            });
            
            //salva posição ao soltar o arrastar
            marker.addListener('dragend', 
        		function(event) {
            		document.getElementById("inputLatitude").value = event.latLng.lat();
            		document.getElementById("inputLongitude").value = event.latLng.lng();
            		
            		console.log("Novo: " + document.getElementById("inputLatitude").value + ', ' + document.getElementById("inputLongitude").value);
            	}
            );
	    }
	    
	    function handleLocationError(browserHasGeolocation, map) {
	        var infoWindow = new google.maps.InfoWindow({
	        	map: map,
	        	
	        	position:{
	                lat: -25.363,
	                lng: 131.044
	            }, 
	            
	        	content: (browserHasGeolocation? 'Error: The Geolocation service failed.' : 'Error: Your browser doesn\'t support geolocation.')
	        });
	    }
	    
	    //ao clicar no botão de salvar
	    $(document).on("submit", "#formLocalizacao", 
    		function(event) {
		        var $form = $(this);

		        console.log("Salvo: " + document.getElementById("inputLatitude").value + ", " + document.getElementById("inputLongitude").value);
		        
		        //resposta ao salvar
		        $.post($form.attr("action"), $form.serialize(), 
	        		function(response) {
		            	$("#alertSucesso").text(response.mensagem);
		            	document.getElementById("alertSucesso").style.display = "inline";
		        	}
		        );
	
		        event.preventDefault(); // Important! Prevents submitting the form.
	    	}
	    );

	    function mudaValorAcao(valor) {
	        document.getElementById("acao").value = valor;
	    }
	
	    
	    function mostraObjeto(objeto){
	    	console.log("objeto = " + objeto);
	    	
        	for (var i in objeto)
        		if (objeto.hasOwnProperty(i)) 
        			console.log("objeto" + "." + i + " = " + objeto[i]);	
	    }