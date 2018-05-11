package dao;

import dao.jinq.FoodTruckDao;
import dao.jinq.LocalDao;
import dao.jinq.PratoDao;
import dao.jinq.SessionDao;

public class DaoFactory {
	
	public static IPratoDao getPratoDao() {
		return new PratoDao();
	}

	public static IFoodTruckDao getFoodTruckDao() {
		return new FoodTruckDao();
	}
	
	public static ILocalDao getLocalDao() {
		return new LocalDao();
	}
	
	public static ISessionDao getSessionDao() {
		return new SessionDao();
	}
}
