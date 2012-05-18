package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * @author Julia
 */
@Entity
public class Comment implements CommentObject, CommentI {

	@Id
	private String comment;

	@OneToMany
	private List<Picture> pictures;

	@OneToMany
	private List<Album> albums;


	@Override
	public List<Picture> getPictures() {
		return pictures;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPicture(List<? extends PictureI> pictures) {
		this.pictures = (List<Picture>) pictures;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getComment() {
		return comment;
	}


	@Override
	public String toString() {
		return " [Comment= " + comment+ "] ";
	}

	@Override
	public List<Album> getAlbums() {
		return albums;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAlbums(List<? extends AlbumI> albums) {
		this.albums = (List<Album>) albums;
	}
	
	@Override
	public String getCommentAsString(){
		return comment;
	}

}

