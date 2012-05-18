package se.cth.hedgehogphoto.database;

import java.util.List;

public interface AlbumDao extends Dao<Album,String>{
	public void updateAllAlbums();
	public List<? extends AlbumI> getAllAlbums();
	public void updateSearchfromDates(String search);
	public void updateSearchPicturesfromDates(String search);
	public List<? extends AlbumI> searchfromDates(String search);
	public void updateSearchfromNames(String search);
	public List<? extends AlbumI> searchfromNames(String search);
	public void updateSearchfromComments(String search);
	public List<? extends AlbumI> searchfromComments(String search);
	public void updateSearchfromTags(String search);
	public List<? extends AlbumI> searchfromTags(String search);
	public void updateSearchfromLocations(String search);
	public List<? extends AlbumI> searchfromLocations(String search);
	public void updateAddTag(String tag, String albumName);
	public void addTag(String tag, String albumName);
	public void updateAddComment(String comment, String albumName);
	public void addComment(String comment, String albumName);
	public void updateAddLocation(String location, String albumName);
	public void addLocation(String location, String albumName);
	public void updateDeleteComment(String albumName);
	public void deleteComment(String albumName);
	public void updateDeleteLocation(String albumName);
	public void deleteLocation(String albumName);
	public void updateDeleteTags(String albumName);
	public void deleteTags(String albumName);
	public void deletePicture(String filePath);
}
