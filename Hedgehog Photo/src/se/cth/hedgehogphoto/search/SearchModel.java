package se.cth.hedgehogphoto.search;


import java.util.List;
import java.util.Observable;

import javax.activation.DataHandler;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.ImageObject;
import se.cth.hedgehogphoto.database.DatabaseHandler;

/**
 * 
 * @author Barnabas Sapan
 *
 */

public class SearchModel extends Observable{
	private String searchText = "";

	public String getSearchQueryText(){
		return searchText;
	}
	
	public void setSearchQueryText(String txt){
		searchText = txt;
	}

	//TODO This is in the DB:
	/*	FileObject f = new ImageObject();
		f.setComment("Gutes bild");
		f.setImageName("wei");
		f.setDate("2012.12.02");
		f.setTag("Bra");
		f.setTag("Battre");
		f.setImagePath("gut.jpg");
		f.setCoverPath("jumboooo.jpg");
		f.setLocation("Japan");
		f.setAlbumName("Bra bilder");
		DatabaseHandler.insert(f);
		
		FileObject b = new ImageObject();
		b.setComment("En valdigt trevlig bild");
		b.setImageName("noice");
		b.setDate("2011.04.01");
		b.setTag("Fin");
		b.setTag("Gullig");
		b.setImagePath("jaha.jpg");
		b.setLocation("Chalmers");
		DatabaseHandler.insert(b);*/
	public List<FileObject> /*void*/ getSearchObjects(){
		FileObject f = new ImageObject();
		f.setComment("Gutes bild");
		f.setImageName("wei");
		f.setDate("2012.12.02");
		f.setTag("Brad");
		f.setTag("Battre");
		f.setImagePath("C://Bilder/IMG_001.jpg");
		f.setCoverPath("hej.jpg");
		f.setLocation("Chalmers");
		f.setAlbumName("Bra bilder");
		DatabaseHandler.insert(f);
		
		//DatabaseHandler.deleteAll();
		//System.out.println(DatabaseHandler.getTags())
		//System.out.println(DatabaseHandler.getLocationd"s());
		//DatabaseHandler.getAlbumName("jaggillarelefanter.jpg");
		return DatabaseHandler.searchComments("Gutes bild");
	
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
