package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * Modifiers for the Album table. This interface only contains setters
 * but by extending AlbumObject which just includes getters this
 * is a complete accessor interface to the database. 
 */
public interface AlbumI extends AlbumObject {

	public void setCoverPath(String coverPath);

	public void setPictures(List<? extends PictureI> pictures);
}