package se.cth.hedgehogphoto.search;


/**
 * @author Barnabas Sapan
 */

public class SearchThread extends Thread{
	private SearchModel model;
	private long delay = 1000;

	public SearchThread(SearchModel model, long delay){
		this.delay = delay;
		this.model = model;
	}

	public void run(){
		try {
			sleep(delay);
			model.doSearch();
		} catch (InterruptedException e) {
			//Do not do anything if we get interrupted
			//it just means that the thread got replaced by a new,
			//more recent and updated one.
		}
	}
}
