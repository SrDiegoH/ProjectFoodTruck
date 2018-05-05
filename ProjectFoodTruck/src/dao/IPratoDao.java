package dao;

import java.util.List;
import model.Prato;

public interface IPratoDao  extends IDao<Prato>{
	List<Prato> filtrarPorFoodTruck(int fk);
	List<Prato> filtrarPorFoodTruckENome(int fk, String nomePrato);
}
