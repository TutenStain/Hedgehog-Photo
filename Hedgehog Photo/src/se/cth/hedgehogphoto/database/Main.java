package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.ImageObject;
import se.cth.hedgehogphoto.LocationObject;

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
		//DatabaseHandler.makeFileObjectfromPath("C:\\Users\\Julia\\Pictures\\20111229\\IMG_0175.JPG");
	//System.out.print(DatabaseHandler.getAllPictures());	
	/*
		FileObject f = new ImageObject();
		f.setComment("lally");
		f.setFileName("lallen");
		f.setDate("d");
		List<String> tags = new ArrayList<String>();
		tags.add("lool");
		f.setTags(tags);
		f.setAlbumName("PASK");
	
		f.setLocation(new Location("Växjö"));
		f.setFilePath("apaN");
		DatabaseHandler.insertPicture(f);
		//System.out.print(DatabaseHandler.getAllPictures());
	//	System.out.print(DatabaseHandler.searchCommentsfromPictures("lally"));
	//DatabaseHandler.addTagtoPicture("Växjös","bettyn" );
		//DatabaseHandler.deletePicture("bettyna");
//DatabaseHandler.deletePath("(gossfddssssdssdssfo");
//DatabaseHandler.addLocationtoPicture("Mas","bettyna" );
	/*	System.out.print(DatabaseHandler.searchDates("d"));
		System.out.print(DatabaseHandler.getTags());
	*/	//DatabaseHandler.deletePicture("bettyna");
//System.out.println(DatabaseHandler.makeImageObjectfromPath("bettyna"));
		
	//DatabaseHandler.removeFileObject(f);
	//DatabaseHandler.deletePath("BANANABASanananamaaaama");*/
}
}
