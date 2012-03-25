package se.cth.hedgehogphoto.search;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import org.apache.derby.iapi.types.Orderable;

public class SearchModel extends Observable{
	private String placeHolderText = "Search...";
	private String searchButtonText = "Search";
	private String searchText = "";
	private Dimension searchBoxSize = new Dimension(100, 30);
	private Dimension searchButtonSize = new Dimension(75, 75);
	
	public SearchModel(){
	}
	
	public void setSearchButtonText(String text){
		searchButtonText = text;
	}
	
	public String getSearchButtonText(){
		return searchButtonText;
	}
	
	public String getPlaceholderText(){
		return placeHolderText;
	}
	
	public void setPlacehoderText(String text){
		placeHolderText = text;
	}
	
	public Dimension getSearchBoxSize(){
		return searchBoxSize;
	}
	
	public Dimension setSearchBoxSize(Dimension d){
		return searchBoxSize = d;
	}
	
	public Dimension getSearchButtonSize(){
		return searchButtonSize;
	}
	
	public void setSearchButtonSize(Dimension d){
		searchButtonSize = d;
	}
	
	public String getSearchQueryText(){
		return searchText;
	}
	
	public void setSearchQueryText(String txt){
		searchText = txt;
	}
	
	public void doSearch(){
		System.out.println("Searching with: " + searchText);
		setChanged();
		notifyObservers();
	}
}
