package se.cth.hedgehogphoto.database;

import java.util.List;

public interface DatabaseAccess {
	public List<String> getTags(); 
	public void updateSearchPicturesfromTags(String tag);
	public List<? extends PictureObject> searchPicturesfromDates(String search);
	public List<? extends PictureObject> searchPicturesfromTags(String t);
	public List<? extends PictureObject> searchPicturesfromComments(String c);
	public List<? extends PictureObject> getAllPictures();
	public DaoFactory getFactory();
}
