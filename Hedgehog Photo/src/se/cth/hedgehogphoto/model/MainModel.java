package se.cth.hedgehogphoto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Picture;

public class MainModel extends Observable {
	private List<Picture> images = new ArrayList<Picture>();
	
	public MainModel() {
		images = DatabaseHandler.getInstance().getAllPictures();
	}
	
	public List<Picture> getImages() {
		for(Picture p : images){
			System.out.println(p.toString());
		}
		System.out.println("IMAGES SIZE: " + images.size());
		return images;
	}
	
	public void testNotify() {
		setChanged();
		notifyObservers(this);
	}
}
