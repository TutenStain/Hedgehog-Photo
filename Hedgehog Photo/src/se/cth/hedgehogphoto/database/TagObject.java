package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Getters for the Tag table in the database. 
 */
public interface TagObject {

	public String getTag();

	public List<? extends AlbumI> getAlbums();

	public List<? extends PictureI> getPictures();
}