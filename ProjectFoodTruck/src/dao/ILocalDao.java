package dao;

import java.util.List;

import model.Local;

public interface ILocalDao extends IDao<Local>{
	List<Local> filtrarPorFoodTruck(int fk);
}
