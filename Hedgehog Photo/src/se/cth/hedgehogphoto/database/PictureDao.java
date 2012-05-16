package se.cth.hedgehogphoto.database;

import java.util.List;

public interface PictureDao extends Dao<Picture, String> {
	
	public void deleteLocation(String filePath);
	public void deleteComment(String filePath);
	public void deleteTags(String filePath);
	public void addLocation(String location, String filePath);
	public  void addComment(String comment, String filePath);
	public void addTag(String tag, String filePath);
	public  PictureObject getfromPath(String search);
	public List<Picture> searchfromLocations(String search);
	public List<Picture> searchfromTags(String search);
	public List<Picture> searchfromDates(String search);
	public  List<Picture> searchfromNames(String search);	
}
