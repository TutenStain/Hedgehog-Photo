package se.cth.hedgehogphoto.search.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.PictureObject;



/**
 * @author Barnabas Sapan
 */

public class SearchThread extends Thread {
	private SearchModel model;
	private String searchText;
	private long delay;
	
	private Thread delayThread;
	private List<PictureObject> pictures;

	public SearchThread(SearchModel model, String searchText, long delay){
		this.searchText = searchText;
		this.delay = delay;
		this.model = model;
	}
	
	public SearchThread(SearchModel model, String searchText) {
		this(model, searchText, SearchConstants.DEFAULT_PREVIEW_DELAY);
	}
	
	public List<PictureObject> search(){
		List<PictureObject> search = new ArrayList<PictureObject>();

		if(DatabaseHandler.getInstance().searchPicturefromsLocations(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturefromsLocations(searchText));
		}
		
		if(DatabaseHandler.getInstance().searchPicturesfromTags(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturesfromTags(searchText));
		}
		
		if(DatabaseHandler.getInstance().searchPicturesfromComments(searchText) != null){
			search.addAll(DatabaseHandler.getInstance().searchPicturesfromComments(searchText));
		}
		
		removeDuplicates(search);
		return search;
	}
	
	/** Removes duplicates from a list.
	 *  List order is not maintained 
	 *  @source http://www.devx.com/tips/Tip/20864 */
	public static void removeDuplicates(List<PictureObject> list) {
		HashSet<PictureObject> h = new HashSet<PictureObject>(list);
		list.clear();
		list.addAll(h);
	}

	//TODO: Find a way to break the delayThread and continue with the normal procedure
	//ie, when you write something, and delay is 1000 ms. After 300ms user clicks enter.
	//then the delayThread should stop and the pictures be sent to the searchmodel AND
	//THEN to the Files-class.
	public void run(){
		try {
			delayThread = new DelayThread(this.delay);
			delayThread.start();
			
			this.pictures = search();
			delayThread.join(); //wait for the delay-time to pass

			this.model.setPictures(this.pictures);
		} catch (InterruptedException e) {
			//Do not do anything if we get interrupted
			//it just means that the thread got replaced by a new,
			//more recent and updated one.
		}
	}
}
