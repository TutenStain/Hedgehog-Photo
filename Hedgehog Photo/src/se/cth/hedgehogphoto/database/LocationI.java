package se.cth.hedgehogphoto.database;

import java.util.List;

public interface LocationI extends LocationObject {

	public void setLongitude(double lon);

	public void setLatitude(double lat);

	public void setPictures(List<? extends PictureI> pictures);

	public void setLocation(String location);

	public void setAlbums(List<? extends AlbumI> albums);
	
	public boolean validPosition();

}