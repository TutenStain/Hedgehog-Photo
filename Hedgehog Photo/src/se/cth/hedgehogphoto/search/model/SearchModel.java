package se.cth.hedgehogphoto.search.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.PictureObject;

/**
 * @author Barnabas Sapan
 */

public class SearchModel extends Observable {
	private List<PictureObject> pictures;

	public SearchModel() {
		this.pictures = new ArrayList<PictureObject>();
	}
	
	public List<PictureObject> getPictures() {
		return this.pictures != null ? this.pictures : new ArrayList<PictureObject>();
	}
	
	public void setPictures(List<PictureObject> pictures) {
		if (pictures != null)
			this.pictures = pictures;
		else
			this.pictures = new ArrayList<PictureObject>();
		
		setChanged();
		notifyObservers(this);
	}

}
