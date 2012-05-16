package se.cth.hedgehogphoto.database;

import java.util.List;

public interface PictureI extends PictureObject {

	public void setTags(List<? extends TagI> tags);

	public void setComment(CommentI comment);

	public void setLocation(LocationI location);

	public void setDate(String date);

	public void setAlbum(AlbumI album);

	public void setPath(String path);

	public void setName(String name);

}