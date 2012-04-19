package se.cth.hedgehogphoto.search;


import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.FileObject;


/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable{
	private String searchText = "";

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

	//TODO fix this...
	public List<FileObject> getSearchObjects(){
		//DatabaseHandler.deleteAll();
		//System.out.println(DatabaseHandler.getTags())
		//System.out.println(DatabaseHandler.getLocationd"s());
		//DatabaseHandler.getAlbumName("jaggillarelefanter.jpg");

		return null; //DatabaseHandler.searchPicturesfromComments("Gutes bild");
		//return DatabaseHandler.searchComments(searchText);
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
