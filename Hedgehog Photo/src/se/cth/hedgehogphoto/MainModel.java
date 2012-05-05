package se.cth.hedgehogphoto;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.log.Log;

public class MainModel extends Observable {
	private List<Picture> images = new ArrayList<Picture>();
	
	public MainModel() {
		try {
			images = DatabaseHandler.getInstance().getAllPictures();
		} catch(Exception e) {	
			//TODO Do something if we fail to get the pictures.
		}
	}
	
	public List<Picture> getImages() {
		return images;
	}
	
	public void testNotify() {
		setChanged();
		notifyObservers(this);
	}
}
