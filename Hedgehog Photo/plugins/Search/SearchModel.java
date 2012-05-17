import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.PictureObject;


/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable{
	private String searchText = "";
	private DatabaseAccess db;

	public SearchModel(SearchView sv, DatabaseAccess db){
		this.addObserver(sv);
		this.db = db;
	}
	
	public SearchModel(SearchView sv, SearchPreviewView spv, DatabaseAccess db){
		this.addObserver(sv);
		this.addObserver(spv);
		this.db = db;
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
		
		/*if(db.searchPicturefromsLocations(searchText) != null){
			search.addAll(db.searchPicturefromsLocations(searchText));
		}*/
		
		if(db.searchPicturesfromTags(searchText) != null){
			search.addAll(db.searchPicturesfromTags(searchText));
		}
		
		if(db.searchPicturesfromComments(searchText) != null){
			search.addAll(db.searchPicturesfromComments(searchText));
		}
		
		return search;
	}
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
