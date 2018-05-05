package dao.jinq;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import dao.IFoodTruckDao;
import model.FoodTruck;

public class FoodTruckDao extends GenericDaoJinq<FoodTruck> implements IFoodTruckDao {
	public FoodTruckDao() {
		super(FoodTruck.class);
	}

	@Override
	public boolean login(String email, String senha) {
		return getStream().where(f -> f.getEmail().equals(email) && f.getSenha().equals(senha))
				          .count() > 0;
	}

	@Override
	public boolean existeEmail(String email) {
		return getStream().where(f-> f.getEmail().equals(email))
				          .count() > 0;
	}
	
	@Override
	public FoodTruck loggar(String email, String senha) {
		try {
			String senhaAtualCriptografada = DigestUtils.sha256Hex(senha);
			return getStream().where(f -> f.getEmail().equals(email) && f.getSenha().equals(senhaAtualCriptografada))
					          .select(p -> p)
					          .getOnlyValue();			
		} catch (NoSuchElementException e) {
			System.err.println("Nenhum item encontrado");
		}
		
		return null;
	}
	
	@Override
	public ArrayList<FoodTruck> foodTruckAoRedor(Double lat, Double lon) {
		Double distancia = 0.01;
		return (ArrayList<FoodTruck>) getStream().where(
							f -> (f.getLatitude()  >= (lat - distancia) && f.getLatitude()  <= (lat + distancia)) && 
							     (f.getLongitude() >= (lon - distancia) && f.getLongitude() <= (lon + distancia))
				           )
					   	  .select(p -> p)
					   	  .toList()
					   	  .stream()
					   	  .map(p -> {
									p.setPratos(new ArrayList<>()); 
									return p;
	   		    	  		   }
					   	  )
					   	  .map( a -> {a.setLocais(new ArrayList<>());
					   		  return a;
					   	  }).collect(Collectors.toList());
	}

	@Override
	public FoodTruck buscarPorCodigo(String codConfirmacao){
		try {
			return getStream().where(f -> f.getCodConfirmacao().equals(codConfirmacao))
					          .select(p -> p)
					          .getOnlyValue();
		} catch (NoSuchElementException e) {
			System.err.println("Nenhum item encontrado");
		}
		
		return null;
	}
	
	@Override
	public FoodTruck buscarPorEmail(String email){
		try{
			return getStream().where(f-> f.getEmail().equals(email))
							  .select(p -> p)
					          .getOnlyValue();
		} catch (NoSuchElementException e) {
			System.err.println("Nenhum item encontrado");
		}
		
		return null;		
	}
}
