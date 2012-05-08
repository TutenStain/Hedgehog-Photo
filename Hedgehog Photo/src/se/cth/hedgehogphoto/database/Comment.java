package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Comment {

@Id
private String comment;
	
@OneToMany
private List<Picture> pictures;

@OneToMany
private List<Album> albums;

	
	public List<Picture> getPictures() {
	return pictures;
}

public void setPicture(List<Picture> pictures) {
	this.pictures = pictures;
}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}


	@Override
	public String toString() {
		return " [Comment= " + comment+ "] ";
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	public String getCommentAsString(){
		return comment;
	}
	
}

