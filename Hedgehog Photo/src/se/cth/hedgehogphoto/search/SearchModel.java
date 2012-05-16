package se.cth.hedgehogphoto.search;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.database.PictureObject;


/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable{
	private String searchText = "";

	public SearchModel(SearchView sv){
		this.addObserver(sv);
	}
	
	public SearchModel(SearchView sv, SearchPreviewView spv){
		this.addObserver(sv);
		this.addObserver(spv);
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
		setChanged();
		notifyObservers(this);
	}
}
