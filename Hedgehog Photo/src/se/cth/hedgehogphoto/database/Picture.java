package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Julia
 *
 */
@Entity
public class Picture {
	@Id
	private String path;
	private String name;
	private String date;
	@ManyToOne
	 private Album album;

	@ManyToMany
	private List<Tag> tags;
	
	@ManyToOne
	private Comment comment;
	@ManyToOne
	private Location location;

	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
	
	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		tags.size();
		return "Pictures [Path= " + path + " Name="+name +"Date= " + date
				+  album  + "Taggar" + tags  + comment  +  location +"]";
	}

}
