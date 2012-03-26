package se.cth.hedgehogphoto.database;

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
public class Comment {
private String comment;
@Id
private String path;

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
	public String getPath(){
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "Comments [Path=" + path + ", Comment=" + comment+ "]";
	}
	
}

