package se.cth.hedgehogphoto.search.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.PictureObject;

/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable {
	private List<PictureObject> pictures;
	private String searchText = "";

	public SearchModel(){
		
	}
	
	public String getSearchQueryText(){
		return searchText;
	}
	
	public void setSearchQueryText(String txt){
		searchText = txt;
	}

	//TODO Better implementation/better != null checks
	public List<PictureObject> getSearchObjects(){
		List<PictureObject> search = new ArrayList<PictureObject>();
		
		if(DatabaseHandler.getInstance().searchPicturefromsLocations(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturefromsLocations(searchText));
		}
		
		if(DatabaseHandler.getInstance().searchPicturesfromTags(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturesfromTags(searchText));
		}
		
		if(DatabaseHandler.getInstance().searchPicturesfromComments(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturesfromComments(searchText));
		}
		
		return search;
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
//		Files.getInstance().setPictureList(getSearchObjects());
		setChanged();
		notifyObservers(this);
	}
}
