package controler;

import dao.DaoFactory;
import java.util.HashMap;
import java.util.Map;
import model.FoodTruck;
import model.Local;
import model.Prato;

public class ControlerLocal extends ControlerBase {
	protected ControlerLocal() {}
	
	//Funcoes de regra de negocios e acesso ao dao
	public Map<String, Object> cadastrar(Integer id, Double latitude, Double longitude, String nome){
		Map<String, Object> hash = new HashMap<>();
		
		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(id);

		DaoFactory.getLocalDao().insert(new Local().setNome(nome)
												   .setLatitude(latitude)
												   .setLongitude(longitude)
												   .setFoodTruck(foodTruck)
					                   );

		hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(id));
		hash.put("foodtruck", foodTruck.getNome());
		hash.put("id", foodTruck.getId());
		hash.put("latitude", foodTruck.getLatitude());
		hash.put("longitude", foodTruck.getLongitude());
		
		return hash;
	}

	public Map<String, Object> atualizarLocal(Integer id){
		Local local = DaoFactory.getLocalDao().find(id);
		
//		FoodTruck foodTruck = DaoFactory.getFoodTruckDao().find(local.getFoodTruck().getId());
		FoodTruck foodTruck = local.getFoodTruck();
		foodTruck.setLatitude(local.getLatitude());
		foodTruck.setLongitude(local.getLongitude());

		DaoFactory.getFoodTruckDao().update(foodTruck);

		Map<String, Object> hash = new HashMap<>();
							hash.put("foodtruck", foodTruck.getNome());
							hash.put("id", foodTruck.getId());
							hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(foodTruck.getId()));
							hash.put("latitude", foodTruck.getLatitude());
							hash.put("longitude", foodTruck.getLongitude());		
		return hash;
	}	
	
	public Map<String, Object> excluir (Integer id){
		Local local = DaoFactory.getLocalDao().find(id);
		
		DaoFactory.getLocalDao().delete(id);
		
		Map<String, Object> hash = new HashMap<>();		
							hash.put("foodtruck", local.getFoodTruck().getNome());
							hash.put("id", local.getFoodTruck().getId());
							hash.put("arrayLocais", DaoFactory.getLocalDao().filtrarPorFoodTruck(local.getFoodTruck().getId()));
							hash.put("latitude", local.getFoodTruck().getLatitude());
							hash.put("longitude", local.getFoodTruck().getLongitude());
		return hash;
	}
}
