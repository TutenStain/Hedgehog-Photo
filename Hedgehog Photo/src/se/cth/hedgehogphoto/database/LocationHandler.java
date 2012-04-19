package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;

public class LocationHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";

	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();


	public static List<String> getLocations(){

		Query q = em.createQuery("select t from Location t");
		List<Location> LocationsList = q.getResultList();
		List<String> LocationsStringList = new ArrayList<String>();
		for (Location Locations : LocationsList ){
			LocationsStringList.add(Locations.getLocation());
		}
		em.close();
		return LocationsStringList;
}
	public static Location getLocation(String loc){
	Query l = em.createQuery("select t from Location t where t.location=:location");
	l.setParameter("location",loc);
	try{
		return (Location)l.getSingleResult();
	}catch(Exception e){
		return null;
	}
	}
	
	public static Location makeLocation(Picture pic, FileObject f){
		em.getTransaction().begin();
		Location location = new Location();
		location.setLatitude((f.getLocation().getLatitude()));
		location.setLongitude(f.getLocation().getLongitude());
		location.setLocation(f.getLocation().getLocation());
		List<Picture> pics = new ArrayList<Picture>();
		pics.add(pic);
		location.setPicture(pics);
		pic.setLocation(location);
		em.persist(pic);
		em.persist(location);
		em.getTransaction().commit();
		return location;
	}
	public static void changeLocation(Location location, Picture pic, FileObject f){
		em.getTransaction().begin();
		location.setLatitude((f.getLocation().getLatitude()));
		location.setLongitude(f.getLocation().getLongitude());
		pic.setLocation(location);
		em.persist(pic);
		em.persist(location);
		em.getTransaction().commit();
	}
}