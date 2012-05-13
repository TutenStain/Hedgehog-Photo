package se.cth.hedgehogphoto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Picture;

public class MainModel extends Observable {
	private List<Picture> images = new ArrayList<Picture>();
	
	public MainModel() {
		this.images = DatabaseHandler.getInstance().getAllPictures();
		Files.getInstance().setPictureList(this.images);
	}
	
	public List<Picture> getImages() {
		for(Picture p : this.images){
			System.out.println(p.toString());
		}
		System.out.println("IMAGES SIZE: " + this.images.size());
		return this.images;
	}
	
	public void testNotify() {
		setChanged();
		notifyObservers(this);
	}
}
