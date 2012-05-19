package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Getters for the Location table in the database. 
 */
public interface LocationObject {

	public double getLongitude();

	public double getLatitude();

	public List<? extends PictureI> getPictures();

	public String getLocation();

	public String getLocationasString();

	public List<? extends AlbumI> getAlbums();

}