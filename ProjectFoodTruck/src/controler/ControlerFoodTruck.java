package controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.fileupload.FileItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DaoFactory;
import model.FoodTruck;
import model.Prato;
import util.EmailHelper;
import util.ImagemHelper;

public class ControlerFoodTruck extends ControlerBase {
	protected ControlerFoodTruck() {}	
	
	//Funcoes de regra de negocios e acesso ao dao	
	public Map<String, Object> alterarSenha(Integer id, String senhaAtual, String novaSenha, String confirmarNovaSenha){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
		
		Map<String, Object> hash = new HashMap<>();
		
		if(!foodTruck.getSenha().equals(senhaAtual)) {
			hash.put("retorno", "senha");
			hash.put("mensagem", "A senha atual esta incorreta");
		} else if(!novaSenha.equals(confirmarNovaSenha)) {
			hash.put("retorno", "senha");
			hash.put("mensagem", "As senhas estao diferentes");
		} else {
			foodTruck.setSenha(novaSenha);
			DaoFactory.getFoodTruckDao().update(foodTruck);
		}
		
		hash.put("id", id);							
		return hash;
	}
	
	public Map<String, Object> alterar(Integer id, String descricao, String foodtruck, String email){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);		
				  foodTruck.setDescricao(descricao);
				  foodTruck.setNome(foodtruck);
				  foodTruck.setEmail(email);
			  
		DaoFactory.getFoodTruckDao().update(foodTruck);
		
		Map<String, Object> hash = new HashMap<>();
							hash.put("email", foodTruck.getEmail());
							hash.put("descricao", foodTruck.getDescricao());
							hash.put("foodtruck", foodTruck.getNome());
							hash.put("id", foodTruck.getId());
		
		return hash;
	}
	
	public Map<String, Object> alterarEmail(String id){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().buscarPorCodigo(id);
				  foodTruck.setContaConfirmada("SIM");
				  
		DaoFactory.getFoodTruckDao().update(foodTruck);
		
		Map<String, Object> hash = new HashMap<>();
							hash.put("email", foodTruck.getEmail());
							hash.put("descricao", foodTruck.getDescricao());
							hash.put("foodtruck", foodTruck.getNome());
							hash.put("id", foodTruck.getId());
							hash.put("confirmada", foodTruck.getContaConfirmada().toLowerCase());
							
		return hash;
	}
	
	public void cadastrar(String foodtruck, String email, String descricao, String senha, String senha2){
		FoodTruck foodTruck = new FoodTruck().setEmail(email)
							                 .setNome(foodtruck)
							                 .setSenha(senha)
							                 .setDescricao(descricao)
							                 .setLatitude(0.0)
							                 .setLongitude(0.0)
							                 .setContaConfirmada("NAO")
							                 .setCodConfirmacao(geradorDeCodigoAleatorio());
		
		DaoFactory.getFoodTruckDao().insert(foodTruck);
		
		foodTruck = DaoFactory.getFoodTruckDao().loggar(email, senha);
		EmailHelper.enviarEmail(email, foodtruck, foodTruck.getCodConfirmacao());
	}
	
	public boolean existeEmail(String email){
		return DaoFactory.getFoodTruckDao().existeEmail(email);
	}
	
	public void enviarNovaSenhaPorEmail(String email){
		String senha = geradorDeCodigoAleatorio();
		
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().buscarPorEmail(email);
				  foodTruck.setSenha(senha); 
				  
	    DaoFactory.getFoodTruckDao().update(foodTruck);
	    
		EmailHelper.emailNovaSenha(email, foodTruck.getNome(), senha, foodTruck.getId().toString());
	}	
		
	public Map<String, Object> buscarPorId(String id){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(Integer.parseInt(id));
		
		Map<String, Object> hash = new HashMap<>();
							hash.put("email", foodTruck.getEmail());
							hash.put("descricao", foodTruck.getDescricao());
							hash.put("foodtruck", foodTruck.getNome());
							hash.put("id", foodTruck.getId());
							hash.put("confirmada", foodTruck.getContaConfirmada().toLowerCase());
							
		return hash;
	}
	
	public Map<String, Object> uploadImagem(Integer id, String context, List<FileItem> fileItemsList){		
		String retorno = ImagemHelper.gravarImagemFoodTruck(id.toString(), context, fileItemsList);
		
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
				  foodTruck.setLocalImagem(retorno);
		
		DaoFactory.getFoodTruckDao().update(foodTruck);

		Map<String, Object> hash = new HashMap<>();		
							hash.put("email", foodTruck.getEmail());
							hash.put("descricao", foodTruck.getDescricao());
							hash.put("foodtruck", foodTruck.getNome());
							hash.put("id", foodTruck.getId());
		return hash;
	}
	
	public String buscarFoodTruckAoRedor(Double lat, Double lon){	
		FoodTruck f = new FoodTruck();
		
		 ArrayList<FoodTruck> foodTrucks = (ArrayList<FoodTruck>) DaoFactory.getFoodTruckDao().foodTruckAoRedor(lat, lon);
		 System.out.println(foodTrucks.get(0).getNome());
		 Map<String, ArrayList<FoodTruck>> options = new HashMap<>();
		                              options.put("listaFoodTruck", foodTrucks);
  		Gson g = new GsonBuilder().disableInnerClassSerialization().create();
  				String json = g.toJson(options);
		 return json;//new Gson().toJson(options);
	}
	
	public Map<String, Object> alterarLocalizacao(boolean isApagar, Integer id, Double latitude, Double longitude){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
				  foodTruck.setLatitude(latitude);
			  	  foodTruck.setLongitude(longitude);
	
		DaoFactory.getFoodTruckDao().update(foodTruck);
		
		
		Map<String, Object> hash = new HashMap<>();		
				
		if(isApagar) {
			hash.put("foodtruck", foodTruck.getNome());				
			hash.put("id", foodTruck.getId());
			hash.put("latitude", foodTruck.getLatitude());
			hash.put("longitude", foodTruck.getLongitude());
		} else {
			Map<String, String> options = new HashMap<>();
			                    options.put("mensagem", "Localizacao atualizada");
			                    options.put("retorno", "0");
			    
            hash.put("json", new Gson().toJson(options));		
		}
		
		return hash;
	}
	
	public Map<String, Object> loggar(String email, String senha){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().loggar(email, senha);
		
		Map<String, Object> hash = new HashMap<>();
		
		try{
			if(foodTruck != null){
				if(foodTruck.getContaConfirmada().equalsIgnoreCase("SIM")){
					hash.put("email", foodTruck.getEmail());
					hash.put("descricao", foodTruck.getDescricao());		
					hash.put("foodtruck", foodTruck.getNome());
					hash.put("id", foodTruck.getId());
				} else
					throw new Exception("Sua Conta ainda nao foi confirmada.");
			} else
				throw new Exception("Email ou senha incorretos.");
		} catch (Exception e){
			hash.put("mensagem", e.getMessage());
			hash.put("retorno", "login");
		}
		
		return hash;
	}
	
	private static String geradorDeCodigoAleatorio(){
//    QNT. DE DIGITO  ----   RANGE   ----  COMBINACOES  ----  QNT. DE FT COM COD. DIFERENTES  
//		    1         ----  (0 - 9)  ----     10^1      ----     10
//		    2         ----  (0 - 9)  ----     10^2      ----     100
//		    3         ----  (0 - 9)  ----     10^3      ----     1_000
//		    ...
//		    7         ----  (0 - 9)  ----     10^7      ----     10_000_000
		return IntStream.range(0, 7)
				        .mapToObj(num -> new Random().nextInt(10) + "")
		                .reduce("", (a, b) -> a + b);
	}
	
	public Map<String, Object> carregaNavegacao(String acao, Integer id){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
		
		Map<String, Object> hash = new HashMap<>();
		
		hash.put("foodtruck", foodTruck.getNome());				
		hash.put("id", foodTruck.getId());
		
		if(acao.equalsIgnoreCase("localizacao")){			
			hash.put("latitude", foodTruck.getLatitude());
			hash.put("longitude", foodTruck.getLongitude());
			hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(id));
			hash.put("url", "alterarLocalizacao.jsp");
		} else if(acao.equalsIgnoreCase("alterarFoodTruck")){			
			hash.put("email", foodTruck.getEmail());
			hash.put("descricao", foodTruck.getDescricao());
			hash.put("confirmada", foodTruck.getContaConfirmada().toLowerCase());
			hash.put("url", "gerenciarFoodTruck.jsp");
		} else if(acao.equalsIgnoreCase("cadastrarPrato")){
			hash.put("url", "cadastrarPrato.jsp");
		} else if(acao.equalsIgnoreCase("buscarPrato")){
			hash.put("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(id));
			hash.put("url", "buscarPrato.jsp");
		} else if(acao.equalsIgnoreCase("senha")){
			hash.put("url", "alterarSenha.jsp");
		}
		
		return hash;
	}
	
	public String carregarModal(Integer id){
		 FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
		 
		 List<Prato> pratos = ControlerFactory.getPratoControler().filtrarPorFoodTruck(foodTruck.getId());
		 StringBuilder estilo = new StringBuilder();
					   estilo.append("<div class='modal-header' style='display:-webkit-inline-box;'>");
					   
				   		if(foodTruck.getLocalImagem() != null && !foodTruck.getLocalImagem().trim().isEmpty())
				   			estilo.append("<img  class='img-circle' width='150' height='100' src='imagensFoodTruck/").append(foodTruck.getLocalImagem()).append("'>");
				   		else
				   			estilo.append("<img  class='img-circle' width='150' height='100' src='imagens/foodtruck.png'>");
				   		
						   estilo.append("<h4 class='modal-title' id='hNomeFoodTruck'>").append(foodTruck.getNome()).append("</h4>");						   
						   estilo.append("<p>").append(foodTruck.getDescricao()).append("</p></h4>");
						   
						   estilo.append("<button type='button' class='btn btn-link' ")
						  	 	 .append("onclick='avaliarFoodTruck(").append(foodTruck.getId()).append(", 0, \"").append(foodTruck.getNome()).append("\");' >")
						         .append(foodTruck.getAvaliacaoPositiva())
						         .append("<i class='material-icons' style='color: #DAB400;'>thumb_up</i></button>");
						   
						   estilo.append("<button type='button' class='btn btn-link' ")							   
					  	 	     .append("onclick='avaliarFoodTruck(").append(foodTruck.getId()).append(", 1, \"").append(foodTruck.getNome()).append("\");' >")
					  	 	     .append(foodTruck.getAvaliacaoNegativa())
					  	 	     .append("<i class='material-icons' style='color: #DAB400;'>thumb_down</i></button>");
					   estilo.append("</div>");
					   
					   estilo.append("<div class='modal-body'>");
						   estilo.append("<table class='table table-hover table-condensed'>");
							   estilo.append("<thead>");
								   estilo.append("<tr>");
								   	   estilo.append("<th>#</th>");
									   estilo.append("<th>Nome</th>");
									   estilo.append("<th>Descrição</th>");
									   estilo.append("<th>Preço</th>");
									   estilo.append("<th></th>");
									   estilo.append("<th></th>");
								   estilo.append("</tr>");
							   estilo.append("</thead>");//
							   estilo.append("<tbody>");
			  
							  for (Prato pt : pratos){
								  estilo.append("<tr>");

							   		if(pt.getLocalImagem() != null && !pt.getLocalImagem().trim().isEmpty())
							   			estilo.append("<td>").append("<img height='30' class='img-rounded' width='30' src='imagensPratos/"+pt.getLocalImagem()+"'").append("</td>");
							   		else
							   			estilo.append("<td>").append("<img height='30' class='img-rounded' width='30' src='imagens/prato.png'").append("</td>");
								  
									  estilo.append("<td style='color: #DAB400;font-weight: bolder;'>").append(pt.getNome()).append("</td>");
									  estilo.append("<td style='color: #DAB400;font-weight: bolder;'>").append(pt.getDescricao()).append("</td>");
									  estilo.append("<td style='color: #DAB400;font-weight: bolder;'>").append(pt.getPreco()).append("</td>");
									  										  
									  estilo.append("<td><button type='button' class='btn btn-link' ")
									  		.append("onclick='avaliarPrato(").append(pt.getId()).append(", ").append(foodTruck.getId()).append(", 0, \"").append(foodTruck.getNome()).append("\");' >")
									        .append(pt.getAvaliacaoPositiva())
									        .append("<i class='material-icons' style='color: #DAB400;'>thumb_up</i></button></td>");
									  
									  estilo.append("<td><button type='button' class='btn btn-link' ")
									  		.append("onclick='avaliarPrato(").append(pt.getId()).append(", ").append(foodTruck.getId()).append(", 1, \"").append(foodTruck.getNome()).append("\");' >")
									        .append(pt.getAvaliacaoNegativa())
									        .append("<i class='material-icons' style='color: #DAB400;'>thumb_down</i></button></td>");									  
								  estilo.append("</tr>");
							  }
			
							  estilo.append("</tbody>");
						  estilo.append("</table>");
					  estilo.append("<div class='modal-footer'><button type='button' class='btn btn-danger' data-dismiss='modal'>Fechar</button></div>");
			 
		  Map<String, String> options = new HashMap<>();
		  					  options.put("pratos", estilo.toString());

		  return new Gson().toJson(options);		
	}
	
	public void avaliarFoodTruck(Integer id, Integer operacao){
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
		
		if(operacao == 0)
			foodTruck.setAvaliacaoPositiva(foodTruck.getAvaliacaoPositiva() + 1);
		else 
			foodTruck.setAvaliacaoNegativa(foodTruck.getAvaliacaoNegativa() + 1);
		
		DaoFactory.getFoodTruckDao().update(foodTruck);
	}
}
