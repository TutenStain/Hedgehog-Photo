package se.cth.hedgehogphoto.database;
/**
 * @author Julia
 */
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public abstract class File{
	@ManyToMany
	private List<Tag> tags;
	private String date;
	@ManyToOne
	private Comment comment;
	@ManyToOne
	private Location location;

	public List<Tag> getTags() {
		return tags;
	}
	protected void setTag(List<Tag> tags) {
		this.tags = tags;
	}

	public Comment getComment() {
		return comment;
	}
	protected void setComment(Comment comment) {
		this.comment = comment;
	}

	public Location getLocation() {
		return location;
	}

	protected void setLocation(Location location) {
		this.location = location;
	}
	public String getDate() {
		return date;
	}

	protected void setDate(String date) {
		this.date = date;
	}	
	public String toString(){
		return ("Tags:"  + tags + "Comment" +comment + "Date" +date + "location" + location ); 
	}
}
