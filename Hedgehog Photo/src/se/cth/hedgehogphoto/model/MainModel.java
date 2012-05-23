package se.cth.hedgehogphoto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;

public class MainModel extends Observable {
	private List<PictureObject> images = new ArrayList<PictureObject>();
	
	public MainModel() {
		this.images.addAll(DatabaseHandler.getInstance().getAllPictures());
		Files.getInstance().setPictureList(this.images);
	}
	
	public List<PictureObject> getImages() {
		return this.images;
	}
	
	public void startNotify() {
		setChanged();
		notifyObservers(this);
	}
}
