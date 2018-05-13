package dao.jinq;

import java.util.NoSuchElementException;

import dao.ISessionDao;
import model.FoodTruck;
import model.Session;

public class SessionDao extends GenericDaoJinq<Session> implements ISessionDao {
	public SessionDao() {
		super(Session.class);
	}

	@Override
	public Session buscarPorHashValor(String hashValor) {
		try {
			return getStream().where(f -> f.getHashValor().equals(hashValor))
					          .select(p -> p)
					          .getOnlyValue();			
		} catch (NoSuchElementException e) {
			System.err.println("Nenhum item encontrado");
		}
		
		return null;
	}

	@Override
	public Session buscarPorFoodTruck(FoodTruck foodTruck) {
		try {
			return getStream().where(f -> f.getFoodTruck().getId() == foodTruck.getId())
					          .select(p -> p)
					          .getOnlyValue();
		} catch (NoSuchElementException e) {
			System.err.println("Nenhum item encontrado");
		}
		
		return null;
	}

}
