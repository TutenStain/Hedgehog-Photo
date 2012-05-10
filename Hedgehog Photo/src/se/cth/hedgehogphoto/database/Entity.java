package se.cth.hedgehogphoto.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface Entity {
	static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	static EntityManager entityManager = factory.createEntityManager();;
	 
	
		
	
}
