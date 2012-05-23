package se.cth.hedgehogphoto.database;

import java.util.List;

/**
 * The interface that the pugins use to comminicate with the database.
 * This gets set by the pluginloader.
 */
public interface DatabaseAccess {
	public List<String> getTags(); 
	public void updateSearchPicturesfromTags(String tag);
	public List<? extends PictureObject> searchPicturesfromDates(String search);
	public List<? extends PictureObject> searchPicturesfromTags(String t);
	public List<? extends PictureObject> searchPicturesfromComments(String c);
	public List<? extends PictureObject> searchPicturefromsLocations(String l);
	public List<? extends PictureObject> getAllPictures();
	public List<? extends PictureObject> findByDate(String search);
}
