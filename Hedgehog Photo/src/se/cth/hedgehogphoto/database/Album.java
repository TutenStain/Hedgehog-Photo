package se.cth.hedgehogphoto.database;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import se.cth.hedgehogphoto.log.Log;
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
		return this.tags;
	}
	
	protected void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public Comment getComment() {
		return this.comment;
	}
	
	protected void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	protected void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String getDate() {
		return this.date;
	}

	protected void setDate(String date) {
		this.date = date;
	}	
	
	@Override
	public List<? extends PictureI> getPictures() {		
		return this.pictures;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPictures(List<? extends PictureI> pictures) {
		this.pictures = (List<Picture>) pictures;
	}
	
	@Override
	public String getAlbumName() {
		return this.albumName;
	}
	
	protected void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	@Override
	public String getCoverPath() {
		return this.coverPath;
	}

	@Override
	public void setCoverPath(String coverPath) {
		this.coverPath= coverPath;
	}

	@Override
	public String toString() {
		try{
			tags.size();
		}catch(Exception e){
			Log.getLogger().log(Level.SEVERE, "Exception", e); 
		}
		
		return " Album [CoverPath=  " + coverPath+ ", AlbumName= " + albumName + "Location= " + location + "Comment= "+ comment + "Tags= " + tags +"Date= " + date+  "] ";
	}
}
