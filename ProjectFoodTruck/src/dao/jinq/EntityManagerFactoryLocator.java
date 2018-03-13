package dao.jinq;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryLocator {	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectFoodTruck");
	
	public static EntityManagerFactory getFactory() {
		return factory;
	}
}
