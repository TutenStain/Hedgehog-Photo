package se.cth.hedgehogphoto.database;

import java.util.List;

public interface PictureObject {

	public List<? extends TagI> getTags();

	public CommentI getComment();

	public LocationI getLocation();

	public String getDate();

	public AlbumI getAlbum();

	public String getPath();

	public String getName();

}