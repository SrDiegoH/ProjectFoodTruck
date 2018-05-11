package dao.jinq;

import java.util.List;

import dao.ILocalDao;
import model.Local;

public class LocalDao extends GenericDaoJinq<Local> implements ILocalDao {
	public LocalDao() {
		super(Local.class);
	}
	
	@Override
	public List<Local> filtrarPorFoodTruck(int fk) {
		return getStream().where(o -> o.getFoodTruck().getId() == fk)
						  .select(o -> o)
						  .sortedBy(o -> o.getId())
				          .toList();
	}	
}
