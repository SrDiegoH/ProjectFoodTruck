	function avaliarPrato(id, fk, operacao) {
		document.getElementById("idPrato").value = id;
		document.getElementById("idFoodTruck").value = fk;
		document.getElementById("operacao").value = operacao;
		document.getElementById("quemAvaliar").value = 0;
		
		$("#btnAvalia").trigger("click");
	}
	
	function avaliarFoodTruck(id, operacao) {
		document.getElementById("idFoodTruck").value = id;
		document.getElementById("operacao").value = operacao;
		document.getElementById("quemAvaliar").value = 1;
		
		$("#btnAvalia").trigger("click");
	}
	
	
	$(document).on("submit", "#formAvaliacao", 
		function(event) {
	        var $form = $(this);
	        
	        $.post($form.attr("action"), $form.serialize(),	
	    		function(response) {
	        		console.log(response);
	        		document.getElementById("divFoodTruckPratos").innerHTML = response.pratos;
	        	}
	        );
	
	        event.preventDefault();
		}
	);

	