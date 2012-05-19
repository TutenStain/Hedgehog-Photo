package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Getters for the Comment table in the database. 
 */
public interface CommentObject {

	public List<? extends PictureI> getPictures();

	public String getComment();

	public List<? extends AlbumI> getAlbums();

	public String getCommentAsString();

}