var campoInvalido = "red";
var campoValido = "white";

function validarSoNumero(e){
	return (event.keyCode>47 && event.keyCode<58);
}


function validarNumeroDouble(event){
	return ((event.keyCode > 47 && event.keyCode < 58) || event.keyCode == 46);
}

function validarSoLetrasAcentos(e){
	return (event.keyCode == 32 || // espaço
			event.keyCode == 45 || // -
			event.keyCode>64 && event.keyCode<91 || // letras minúsculas
			event.keyCode>96 && event.keyCode<123 || // LETRAS MAIÚSCULAS
			event.keyCode == 126 || // ~~
			event.keyCode == 168 || // ¨¨
			event.keyCode == 180 // ´´
			);
}

function mascarar(t, mask){
	var tam = t.value.length;
	var saida = mask.substring(1,0);
	var texto = mask.substring(tam)

	if (texto.substring(0,1) != saida)
		t.value += texto.substring(0,1);
}

function textoCaixaAlta(componenTexto){
	componenTexto.value = componenTexto.value.toUpperCase();
}

function fnDirecionaPagina(source){
	var caminho = "";
	switch(source){
		case "alterainformacoes":
			caminho = "FachadaNavegacao?acao=alterarFoodTruck";
			break;
		case "senha":
			caminho = "FachadaNavegacao?acao=Senha";
			break;
		case "prato":
			caminho = "FachadaNavegacao?acao=cadastrarPrato";
			break;
		case "buscarprato":
			caminho = "FachadaNavegacao?acao=buscarPrato";
			break;
		case "localiza":
			caminho = "FachadaNavegacao?acao=localizacao";
			break;
		case "sair":
			caminho = "FachadaNavegacao?acao=sair";
			break;
	}
	
	location.href = caminho;
}
