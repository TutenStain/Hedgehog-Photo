import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import se.cth.hedgehogphoto.plugin.*;
import se.cth.hedgehogphoto.database.DatabaseAccess;

/**
 * @author Barnabas Sapan
 */

public class TagCloudModel extends Observable {
	private List<String> tags = new ArrayList<String>();
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private DatabaseAccess db;

	public TagCloudModel(DatabaseAccess db){
		this.db = db;
	}
	
	public void setTags(List<String> _tags){
		if(_tags.size() != 0){
			tags = _tags;
			
			//TODO This shuffle the tags, which it does, but they still get returned in the same order,
			//maybe because of the iterator still having references to the old unshuffled order?
			Collections.shuffle(tags);
	
			Iterator<String> itr = tags.iterator();
			
			while(itr.hasNext()){
				String o = itr.next();
				int occurrences = Collections.frequency(tags, o);
				map.put(o, occurrences);
			}
			
			setChanged();
			notifyObservers(this);
		}
	}
	
	public Map<String, Integer> getTagsOccurrence (){
		return map;
	}

	public DatabaseAccess getDatabase(){
		return db;
	}
}
