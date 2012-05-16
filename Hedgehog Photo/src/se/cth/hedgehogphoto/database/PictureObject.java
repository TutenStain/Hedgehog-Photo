package se.cth.hedgehogphoto.database;

import java.util.List;

public interface PictureObject {

	public List<Tag> getTags();

	public Comment getComment();

	public Location getLocation();

	public String getDate();

	public AlbumI getAlbum();

	public String getPath();

	public String getName();

}