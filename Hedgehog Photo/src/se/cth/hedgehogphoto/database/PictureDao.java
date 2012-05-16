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
	public List<? extends PictureI> searchfromLocations(String search);
	public List<? extends PictureI> searchfromTags(String search);
	public List<? extends PictureI> searchfromDates(String search);
	public  List<? extends PictureI> searchfromNames(String search);
	public  List<? extends PictureI> searchfromComments(String search);
}
