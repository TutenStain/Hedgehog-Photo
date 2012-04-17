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
public class Album extends File {
	@Id
	private String albumName;
	
	private String coverPath;
	
	@OneToMany
	List<Picture> picture;

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
	
	public String getName() {
		return albumName;
	}

	public void setName(String albumName) {
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
		return "Pictures [CoverPath=" + coverPath+ ", Name=" + albumName+ "]";
	}
}
