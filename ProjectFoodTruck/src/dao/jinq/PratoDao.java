package dao.jinq;

import java.util.List;
import java.util.stream.Collectors;

import dao.IPratoDao;
import model.Prato;

public class PratoDao extends GenericDaoJinq<Prato> implements IPratoDao {
	public PratoDao() {
		super(Prato.class);
	}
	
	@Override
	public List<Prato> filtrarPorFoodTruck(int fk) {
		return getStream().where(o -> o.getFoodTruck().getId() == fk)
						  .select(o -> o)
						  .sortedBy(o -> o.getId())
				          .toList()
				          .stream()
				          .map(o -> o.descriptografar())
				          .collect(Collectors.toList());
	}
	
	@Override
	public List<Prato> filtrarPorFoodTruckENome(int fk, String nomePrato) {
		return getStream().where(o -> o.getFoodTruck().getId() == fk)
				.select(o -> o)
				.sortedBy(o -> o.getId())
				.toList()
	            .stream()
	            .map(o -> o.descriptografar())
	            .filter(o -> o.getNome().contains(nomePrato))
	            .collect(Collectors.toList());
	}
}
