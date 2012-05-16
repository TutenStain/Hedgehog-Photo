package se.cth.hedgehogphoto.database;

import java.util.List;

public interface AlbumObject {

	public List<Tag> getTags();

	public Comment getComment();

	public Location getLocation();

	public String getDate();

	public List<? extends PictureI> getPictures();

	public String getAlbumName();

	public String getCoverPath();

}