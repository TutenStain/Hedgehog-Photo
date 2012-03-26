package se.cth.hedgehogphoto.search;

import java.util.Observable;
import se.cth.hedgehogphoto.*;

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
	
	//TODO Use Julia's class instead from GIT.
	/*public FileObject getSearchObjects(){
		
	}*/
	
	public void doSearch(){
		System.out.println("Model searching with: " + searchText);
		setChanged();
		notifyObservers(this);
	}
}
