package dao.jinq;

import dao.IAvaliacaoDao;
import model.Avaliacao;

public class AvaliacaoDao extends GenericDaoJinq<Avaliacao> implements IAvaliacaoDao {
	public AvaliacaoDao() {
		super(Avaliacao.class);
	}
}
