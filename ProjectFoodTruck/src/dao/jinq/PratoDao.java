package dao.jinq;

import java.util.List;

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
				          .toList();
	}
	
	@Override
	public List<Prato> filtrarPorFoodTruckENome(int fk, String nomePrato) {
		return getStream().where(o -> o.getFoodTruck().getId() == fk && o.getNome().contains(nomePrato))
				.select(o -> o)
				.sortedBy(o -> o.getId())
				.toList();
	}
}
