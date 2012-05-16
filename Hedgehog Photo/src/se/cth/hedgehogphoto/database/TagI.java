package se.cth.hedgehogphoto.database;

import java.util.List;

public interface TagI extends TagObject {

	public void setPictures(List<? extends PictureI> pictures);
	
	public void setTag(String tag);

	public void setAlbums(List<? extends AlbumI> albums);

}