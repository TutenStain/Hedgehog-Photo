package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Julia
 *
 */
@Entity
public class Picture implements PictureObject, PictureI {
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

	@Override
	public List<Tag> getTags() {
		return tags;
	}
	@Override
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public Comment getComment() {
		return comment;
	}
	@Override
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}	
	
	@Override
	public Album getAlbum() {
		return album;
	}

	@Override
	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		tags.size();
		
		return "Pictures [Path= " + path + " Name=" + name + " Date= " + date
				+  album  + " Taggar" + tags + comment  +  location +"]";
	}

}
