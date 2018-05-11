package dao;

import model.FoodTruck;
import model.Session;

public interface ISessionDao  extends IDao<Session>{
	Session buscarPorHashValor(String hashValor);	
	Session buscarPorFoodTruck(FoodTruck foodTruck); 	
}
