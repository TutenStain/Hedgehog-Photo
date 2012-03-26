package se.cth.hedgehogphoto.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.ImageObject;
/**
 * 
 * @author Julia
 *
 */
public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	private static EntityManagerFactory factory ;

	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		FileObject f = new ImageObject();
		f.setComment("lal");
		f.setImageName("lallen");
		f.setDate("d");
		f.setTag("blubb");
		f.setImagePath("BANANABASananana");
		DatabaseHandler.insert(f);
		DatabaseHandler.removeFileObject(f);
	
}
}
