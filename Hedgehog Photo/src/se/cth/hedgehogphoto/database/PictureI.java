package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Modifiers for the Picture table. This interface only contains setters
 * but by extending PictureObject which just includes getters this
 * is a complete accessor interface to the database. 
 */
public interface PictureI extends PictureObject {

	public void setTags(List<? extends TagI> tags);

	public void setComment(CommentI comment);

	public void setLocation(LocationI location);

	public void setDate(String date);

	public void setAlbum(AlbumI album);

	public void setPath(String path);

	public void setName(String name);

}