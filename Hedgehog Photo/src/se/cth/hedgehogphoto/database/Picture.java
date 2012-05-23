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
	public List<? extends TagI> getTags() {
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTags(List<? extends TagI> tags) {
		this.tags = (List<Tag>)tags;
	}

	@Override
	public Comment getComment() {
		return this.comment;
	}

	@Override
	public void setComment(CommentI comment) {
		this.comment = (Comment) comment;
	}

	@Override
	public LocationI getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(LocationI location) {
		this.location = (Location) location;
	}

	@Override
	public String getDate() {
		return this.date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}	

	@Override
	public Album getAlbum() {
		return this.album;
	}

	@Override
	public void setAlbum(AlbumI album) {
		this.album = (Album)album;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Pictures [Path= " + path + " Name=" + name + " Date= " + date
				+  album  + " Taggar" + tags + comment  +  location +"]";
	}
}
