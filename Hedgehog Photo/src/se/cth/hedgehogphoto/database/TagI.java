package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Modifiers for the Tag table. This interface only contains setters
 * but by extending TagObject which just includes getters this
 * is a complete accessor interface to the database. 
 */
public interface TagI extends TagObject {

	public void setPictures(List<? extends PictureI> pictures);
	
	public void setTag(String tag);

	public void setAlbums(List<? extends AlbumI> albums);

}