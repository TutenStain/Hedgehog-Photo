package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Getters for the Album table in the database. 
 */
public interface AlbumObject {

	public List<? extends TagI> getTags();

	public CommentI getComment();

	public LocationI getLocation();

	public String getDate();

	public List<? extends PictureI> getPictures();

	public String getAlbumName();

	public String getCoverPath();

}