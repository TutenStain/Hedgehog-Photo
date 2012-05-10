package se.cth.hedgehogphoto.search;


import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.objects.FileObject;


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
	public List<Picture> getSearchObjects(){
		//DatabaseHandler.deleteAll();
		//System.out.println(DatabaseHandler.getTags())
		//System.out.println(DatabaseHandler.getLocationd"s());
		//DatabaseHandler.getAlbumName("jaggillarelefanter.jpg");
		return DatabaseHandler.getInstance().searchPicturefromsLocations("Japan");
		//return DatabaseHandler.getInstance().searchPicturesfromComments("Gutes bild");
		//return DatabaseHandler.getInstance().searchPicturesfromTags("snyggt");
		//return DatabaseHandler.searchComments(searchText);
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
