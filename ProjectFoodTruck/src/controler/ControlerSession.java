package controler;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import dao.DaoFactory;
import model.FoodTruck;
import model.Session;

public class ControlerSession extends ControlerBase {
	protected ControlerSession() {}	
	
	//Funcoes de regra de negocios e acesso ao dao	
	public Session buscarPorHashValor (String hashValor){
		return DaoFactory.getSessionDao().buscarPorHashValor(hashValor);
	}
	
	public Session buscarPorFoodTruck (FoodTruck foodTruck){
		return DaoFactory.getSessionDao().buscarPorFoodTruck(foodTruck);
	}
	
	public Session loggar (FoodTruck foodTruck){
		Date prazo = new Date();
		prazo.setDate(prazo.getDate() + 1);
		
		String hashValor = DigestUtils.sha256Hex(foodTruck + "-" + prazo.toString() + "-" + new Date());
		
		Session session = new Session(hashValor, foodTruck, prazo, true);
		
		DaoFactory.getSessionDao().insert(session);
		
		session = DaoFactory.getSessionDao().buscarPorHashValor(session.getHashValor());
		return session;
	}
	
	public void desloggar (String hashValor){
		Session session = DaoFactory.getSessionDao().buscarPorHashValor(hashValor);
				session.setIsAtivo(false);
		DaoFactory.getSessionDao().update(session);
	}
	
	public boolean isOver (String hashValor){
		return DaoFactory.getSessionDao().buscarPorHashValor(hashValor).isOver();
	}
}
