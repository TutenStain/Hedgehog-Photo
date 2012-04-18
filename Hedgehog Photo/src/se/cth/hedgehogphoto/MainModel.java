package se.cth.hedgehogphoto;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;

public class MainModel extends Observable {
	private List<FileObject> images = new ArrayList<FileObject>();
	
	public MainModel() {
		try {
			images = DatabaseHandler.getAllPicturesasFileObject();
		} catch(Exception e) {	
			//TODO Do something if we fail to get the pictures.
		}
	}
	
	public List<FileObject> getImages() {
		for(int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i));
		}
		return images;
	}
	
	public void testNotify() {
		setChanged();
		notifyObservers(this);
	}
}
