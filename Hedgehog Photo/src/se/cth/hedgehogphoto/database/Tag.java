package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Tag {

@Id	
private String tag;



@ManyToMany
private List<Picture> picture;
@ManyToMany
private List<Album> album;

	public String getTag() {
		return tag;
	}

	public void setPicture(List<Picture> picture) {
		this.picture = picture;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Album> getAlbum() {
		return album;
	}

	public void setAlbum(List<Album> album) {
		this.album = album;
	}

	public List<Picture> getPicture() {
		return picture;
	}
	@Override
	public String toString() {
		return "[Tag=" + tag+ "] ";
	}
	public String getTagAsString(){
		return tag;
	}
}
