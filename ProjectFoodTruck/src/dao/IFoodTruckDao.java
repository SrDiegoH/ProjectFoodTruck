package dao;

import java.util.List;
import model.FoodTruck;

public interface IFoodTruckDao  extends IDao<FoodTruck>{
	boolean login(String email, String senha);
	boolean existeEmail(String email);
	FoodTruck loggar(String email, String senha);
	List<FoodTruck> foodTruckAoRedor(Double lat, Double lon);
	FoodTruck buscarPorCodigo(String codConfirmacao);
	FoodTruck buscarPorEmail(String email);	
}
