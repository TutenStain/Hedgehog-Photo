package se.cth.hedgehogphoto.search.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import se.cth.hedgehogphoto.database.DatabaseAccess;
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
	
	private DatabaseAccess db;

	public SearchThread(SearchModel model, String searchText, long delay, DatabaseAccess db){
		this.searchText = searchText;
		this.delay = delay;
		this.model = model;
		this.db = db;
	}
	
	public SearchThread(SearchModel model, String searchText, DatabaseAccess db) {
		this(model, searchText, SearchConstants.DEFAULT_PREVIEW_DELAY, db);
	}
	
	public List<PictureObject> search(){
		List<PictureObject> search = new ArrayList<PictureObject>();

		if(this.db.searchPicturefromsLocations(this.searchText) != null){
			search.addAll(this.db.searchPicturefromsLocations(this.searchText));
		}
		
		if(this.db.searchPicturesfromTags(this.searchText) != null){
			search.addAll(this.db.searchPicturesfromTags(this.searchText));
		}
		
		if(this.db.searchPicturesfromComments(this.searchText) != null){
			search.addAll(this.db.searchPicturesfromComments(this.searchText));
		}
		
		removeDuplicates(search);
		
		return search;
	}
	
	/** Removes duplicates from a list.
	 *  List order is not maintained 
	 *  @source http://www.devx.com/tips/Tip/20864 */
	private static void removeDuplicates(List<PictureObject> list) {
		HashSet<PictureObject> h = new HashSet<PictureObject>(list);
		list.clear();
		list.addAll(h);
	}

	//Enhancement possibility: Find a way to break the delayThread and continue with the 
	//normal procedure ie, when you write something, and delay is 1000 ms. After 300ms user
	//clicks enter then the delayThread should stop and the pictures be sent to the searchmodel
	//AND to the Files-class.
	@Override
	public void run(){
		try {
			this.delayThread = new DelayThread(this.delay);
			this.delayThread.start();
		
			this.delayThread.join(); //wait for the delay-time to pass
			this.pictures = search();
			
			this.model.setPictures(this.pictures);
		} catch (InterruptedException e) {
			//Do not do anything if we get interrupted
			//it just means that the thread got replaced by a new,
			//more recent and updated one.
		}
	}
}
