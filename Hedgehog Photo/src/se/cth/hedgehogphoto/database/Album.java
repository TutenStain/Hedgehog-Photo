package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	List<Picture> pictures;
	@OneToMany
	private List<Tag> tags;
	private String date;
	@ManyToOne
	private Comment comment;
	@ManyToOne
	private Location location;

	
	public List<Tag> getTags() {
		return tags;
	}
	protected void setTags(List<Tag> tags) {
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
	public List<Picture> getPictures() {
		return pictures;
	}



	protected void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
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
		tags.size();
		return " Album [CoverPath=  " + coverPath+ ", AlbumName= " + albumName + "Location= " + location + "Comment= "+ comment + "Tags= " + tags +"Date= " + date+  "] ";
	}
}
