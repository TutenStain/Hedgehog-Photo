package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Album implements AlbumObject, AlbumI {
	@Id
	private String albumName;
	
	private String coverPath;
	
	private String date;
	
	@OneToMany
	private List<Picture> pictures;
	
	@OneToMany
	private List<Tag> tags;
	
	@ManyToOne
	private Comment comment;
	
	@ManyToOne
	private Location location;

	
	@Override
	public List<Tag> getTags() {
		return tags;
	}
	protected void setTags(List<Tag> tags) {
		this.tags = tags;
	
	}

	@Override
	public Comment getComment() {
		return comment;
	}
	
	protected void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	protected void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String getDate() {
		return date;
	}

	protected void setDate(String date) {
		this.date = date;
	}	
	
	@Override
	public List<? extends PictureI> getPictures() {		
		return pictures;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPictures(List<? extends PictureI> pictures) {
		this.pictures = (List<Picture>) pictures;
	}
	
	@Override
	public String getAlbumName() {
		return albumName;
	}
	
	protected void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	@Override
	public String getCoverPath() {
		return coverPath;
	}

	@Override
	public void setCoverPath(String coverPath) {
		this.coverPath= coverPath;
	}

	@Override
	public String toString() {
		try{
		tags.size();
		}catch(Exception o){
			
		}
		return " Album [CoverPath=  " + coverPath+ ", AlbumName= " + albumName + "Location= " + location + "Comment= "+ comment + "Tags= " + tags +"Date= " + date+  "] ";
	}
}
