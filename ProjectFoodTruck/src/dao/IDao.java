package dao;

import java.util.List;

import dao.jinq.IWhere;
import model.BaseEntity;

public interface IDao<T extends BaseEntity> {
	void insert(T t);
	void update(T t);
	void delete(int id);
	T find(int id);
	List<T> retrieve();
	List<T> retrieve(IWhere<T> predicate);
}
