package controler;

import dao.DaoFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FoodTruck;
import model.Prato;

public class ControlerPrato extends ControlerBase{
	protected ControlerPrato() {}
	
	//Funcoes de regra de negocios e acesso ao dao
	public Map<String, Object> alterar (Integer id){		
		Prato prato = DaoFactory.getPratoDao().find(id);
		
		Map<String, Object> hash = new HashMap<>(); 
							hash.put("foodtruck", prato.getFoodTruck().getNome());
							hash.put("fk", prato.getFoodTruck().getId());
							hash.put("id", prato.getId());
							hash.put("prato", prato.getNome());
							hash.put("descricaoPrato", prato.getDescricao());
							hash.put("preco", prato.getPreco().toString());						
							hash.put("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(prato.getFoodTruck().getId()));
							
		return hash;
	}
	
	public Map<String, Object> excluir (Integer id){
		Prato prato = DaoFactory.getPratoDao().find(id);
		
		DaoFactory.getPratoDao().delete(id);
		
		Map<String, Object> hash = new HashMap<>();		
							hash.put("foodtruck", prato.getFoodTruck().getNome());
							hash.put("id", prato.getFoodTruck().getId());
							hash.put("arrayPratos", DaoFactory.getPratoDao().filtrarPorFoodTruck(prato.getFoodTruck().getId()));
							
		return hash;
	}
	
	public Map<String, Object> cadastrar(Integer id, String foodtruck, Double preco, String prato, String descricao){
		Map<String, Object> hash = new HashMap<>();
		
		try {
			FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);
			
			DaoFactory.getPratoDao().insert(new Prato().setNome(prato)
													   .setPreco(preco)
													   .setDescricao(descricao)
													   .setFoodTruck(foodTruck)
						                   );

			
			hash.put("foodtruck", foodTruck.getNome());
			hash.put("id", foodTruck.getId());
		} catch (Exception e) {
			hash.put("retorno", "preco");
			hash.put("mensagem", "Formato do preco invalido");
			
			hash.put("foodtruck", foodtruck);
			hash.put("id", id);
			hash.put("prato", prato);
			hash.put("descricaoPrato", descricao);
		}
		
		return hash;
	}
	
	public List<Prato> filtrarPorFoodTruck (Integer fk){
		return DaoFactory.getPratoDao().filtrarPorFoodTruck(fk);
	}
	
	public void avaliarPrato(Integer id, Integer operacao){
		Prato prato = DaoFactory.getPratoDao().find(id);
		
		if(operacao == 0)
			prato.setAvaliacaoPositiva(prato.getAvaliacaoPositiva() + 1);
		else 
			prato.setAvaliacaoNegativa(prato.getAvaliacaoNegativa() + 1);
		
		DaoFactory.getPratoDao().update(prato);
	}
}
