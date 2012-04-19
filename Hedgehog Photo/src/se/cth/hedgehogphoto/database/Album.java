package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Album {
	@Id
	private String albumName;
	
	private String coverPath;
	
	@OneToMany
	List<Picture> picture;

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
	public List<Picture> getPicture() {
		return picture;
	}



	protected void setPictures(List<Picture> picture) {
		this.picture = picture;
	}
	public String getAlbumName() {
		return albumName;
	}



	protected void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath= coverPath;
	}

	@Override
	public String toString() {
		super.toString();
		return " Album [CoverPath=  " + coverPath+ ", AlbumName= " + albumName + location + comment + tags + "] ";
	}
}
