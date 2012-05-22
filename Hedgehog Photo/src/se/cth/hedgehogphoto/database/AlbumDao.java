package se.cth.hedgehogphoto.database;

import java.util.List;

public interface AlbumDao extends Dao<Album,String>{
	
	public List<? extends AlbumI> getAllAlbums();


	public List<? extends AlbumI> searchfromDates(String search);

	public List<? extends AlbumI> searchfromNames(String search);

	public List<? extends AlbumI> searchfromComments(String search);

	public List<? extends AlbumI> searchfromTags(String search);

	public List<? extends AlbumI> searchfromLocations(String search);

	public void addTag(String tag, String albumName);

	public void addComment(String comment, String albumName);
	
	public void addLocation(String location, String albumName);

	public void deleteComment(String albumName);

	public void deleteLocation(String albumName);

	public void deleteTags(String albumName);
	public void deletePicture(String filePath);
}
