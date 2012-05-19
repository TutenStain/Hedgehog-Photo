package se.cth.hedgehogphoto.tagcloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.database.TagObject;

/**
 * This model is observable while at the same time is an observer
 * for the Files class that displays the current images.
 * @author Barnabas Sapan
 */

public class TagCloudModel extends Observable implements Observer {
	private List<String> tags = new ArrayList<String>();
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private DatabaseAccess db;
	
	public TagCloudModel(DatabaseAccess db){
		this.db = db;
	}
	
	/**
	 * Sets the tags to this model.
	 * @param _tags list of non distinct(!) tags.
	 */
	public void setTags(List<String> _tags){
		if(_tags.size() != 0){
			tags.clear();
			tags = _tags;
			
			Iterator<String> itr = tags.iterator();
			
			map.clear();
			while(itr.hasNext()){
				String o = itr.next();
				int occurrences = Collections.frequency(tags, o);
				map.put(o, occurrences);
			}
			
			setChanged();
			notifyObservers(this);
		}
	}
	
	/**
	 * Does a search in the dababase and displays the images that contains the supplied tag
	 * @param tag The tag to filter and show images on.
	 */
	public void updateSearchPicturesfromTags(String tag){
		db.updateSearchPicturesfromTags(tag);
	}
	
	/**
	 * Number of times the tags occurs.
	 * @return a map containing the tag-name with the number of times
	 * it appears.
	 */
	public Map<String, Integer> getTagsOccurrence (){
		return map;
	}

	/**
	 * If Files has changed, update our tagcloud by calling setTags().
	 */
	@Override
	public void update(Observable o, Object arg) {
		List<String> l = new ArrayList<String>();
		for(PictureObject po : db.getAllPictures()){
			for(TagObject to : po.getTags()){
				l.add(to.getTag());
			}
		}
		
		this.setTags(l);
	}
}
