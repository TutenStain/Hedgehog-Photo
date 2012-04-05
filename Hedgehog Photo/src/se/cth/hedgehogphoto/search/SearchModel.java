package se.cth.hedgehogphoto.search;


import java.util.List;
import java.util.Observable;

import javax.activation.DataHandler;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.ImageObject;
import se.cth.hedgehogphoto.database.DatabaseHandler;

/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable{
	private String searchText = "";

	public String getSearchQueryText(){
		return searchText;
	}
	
	public void setSearchQueryText(String txt){
		searchText = txt;
	}

	//TODO fix this...
	public List<FileObject> getSearchObjects(){
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