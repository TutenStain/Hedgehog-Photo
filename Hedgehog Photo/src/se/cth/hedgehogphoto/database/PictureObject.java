package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Getters for the Picture table in the database. 
 */
public interface PictureObject {

	public List<? extends TagI> getTags();

	public CommentI getComment();

	public LocationI getLocation();

	public String getDate();

	public AlbumI getAlbum();

	public String getPath();

	public String getName();

}