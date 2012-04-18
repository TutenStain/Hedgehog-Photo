package se.cth.hedgehogphoto;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import se.cth.hedgehogphoto.database.DatabaseHandler;

public class MainModel extends Observable {
	private List<FileObject> images = new ArrayList<FileObject>();
	
	public MainModel() {
		try {
			 //images = DatabaseHandler.searchPicturesfromDates("2012.12.02");
			//images = DatabaseHandler.searchPicturesfromComments("Gutes bild");
			images = DatabaseHandler.searchPicturesfromTags("Snyggt");
			//System.out.print("IMAGES SIZE-------------------------------------------------------: + " + images.size() + images);
		} catch(Exception e) {	
			//autoexception
		}
	}
	
	public List<FileObject> getImages() {
		for(int i = 0; i<images.size(); i++) {
			System.out.println(images.get(i));
		}
		return images;
	}
	
	public void testNotify() {
		setChanged();
		notifyObservers(this);
	}
}
