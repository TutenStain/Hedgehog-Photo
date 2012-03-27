package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Tag {
private List<String> tags = new ArrayList();
@Id
private String path;
/*@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long tagID;*/



	public void setTag(String tag) {
		tags.add(tag);
	}

	public List<String> getTags() {
		return tags;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath(){
		return path;
	}
	@Override
	public String toString() {
		return "Tags [Path=" + path + ", Tag=" + tags+ "]";
	}
	
}
