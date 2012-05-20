package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Modifiers for the Location table. This interface only contains setters
 * but by extending LocationObject which just includes getters this
 * is a complete accessor interface to the database. 
 */
public interface LocationI extends LocationObject {

	public void setLongitude(double lon);

	public void setLatitude(double lat);

	public void setPictures(List<? extends PictureI> pictures);

	public void setLocation(String location);

	public void setAlbums(List<? extends AlbumI> albums);
}