package dao;

import dao.jinq.AvaliacaoDao;
import dao.jinq.FoodTruckDao;
import dao.jinq.LocalDao;
import dao.jinq.PratoDao;

public class DaoFactory {
	
	public static IAvaliacaoDao getAvaliacaoDao() {
		return new AvaliacaoDao();
	}
	
	public static IPratoDao getPratoDao() {
		return new PratoDao();
	}

	public static IFoodTruckDao getFoodTruckDao() {
		return new FoodTruckDao();
	}
	
	public static ILocalDao getLocalDao() {
		return new LocalDao();
	}
}
