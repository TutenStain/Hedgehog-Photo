package se.cth.hedgehogphoto.database;

import java.util.List;

public interface PictureI extends PictureObject {

	public void setTags(List<Tag> tags);

	public void setComment(Comment comment);

	public void setLocation(Location location);

	public void setDate(String date);

	public void setAlbum(Album album);

	public void setPath(String path);

	public void setName(String name);

}