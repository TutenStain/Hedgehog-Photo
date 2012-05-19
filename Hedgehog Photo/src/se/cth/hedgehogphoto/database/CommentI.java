package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Modifiers for the Comment table. This interface only contains setters
 * but by extending CommentObject which just includes getters this
 * is a complete accessor interface to the database. 
 */
public interface CommentI extends CommentObject {

	public void setPicture(List<? extends PictureI> pictures);

	public void setComment(String comment);

	public void setAlbums(List<? extends AlbumI> albums);

}