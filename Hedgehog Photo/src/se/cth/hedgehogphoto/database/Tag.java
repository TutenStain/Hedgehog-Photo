package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Tag {

@Id	
private String tag;




@ManyToMany
private List<Picture> pictures;

private List<Album> albums;

	public String getTag() {
		return tag;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Picture> getPictures() {
		return pictures;
	}
	@Override
	public String toString() {
		return "[Tag=" + tag+ "] ";
	}
	public String getTagAsString(){
		return tag;
	}
}
