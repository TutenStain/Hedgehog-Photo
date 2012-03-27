package se.cth.hedgehogphoto.search;


import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.ImageObject;
import se.cth.hedgehogphoto.database.DatabaseHandler;

public class SearchModel extends Observable{
	private String searchText = "";
	
	public SearchModel(){
	}
	
	public String getSearchQueryText(){
		return searchText;
	}
	
	public void setSearchQueryText(String txt){
		searchText = txt;
	}

	//TODO This is in the DB:
	/*FileObject f = new ImageObject();
	f.setComment("Bra bild alltsa");
	f.setImageName("wei");
	f.setDate("2012.12.02");
	f.setTag("Bra");
	f.setTag("Battre");
	f.setImagePath("jumbooo.jpg");
	f.setCoverPath("jumbooo.jpg");
	f.setLocation("Sverige");
	DatabaseHandler.insert(f);*/
	public List<FileObject> getSearchObjects(){
		FileObject f = new ImageObject();
		f.setComment("Gutes bild");
		f.setImageName("wei");
		f.setDate("2012.12.02");
		f.setTag("Bra");
		f.setTag("Battre");
		f.setImagePath("bajjjs.jpg");
		f.setCoverPath("jumboooo.jpg");
		f.setLocation("Japan");
		//DatabaseHandler.insert(f);
	//	DatabaseHandler.removeFileObject(f);
	//	DatabaseHandler.deleteAll();
		//System.out.println(DatabaseHandler.getTags());
		//System.out.println(DatabaseHandler.getLocations());
		return DatabaseHandler.searchComments("Gutes bild");
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
