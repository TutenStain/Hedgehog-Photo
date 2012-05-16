package se.cth.hedgehogphoto.database;

import java.util.List;

public interface CommentObject {

	public List<? extends PictureI> getPictures();

	public String getComment();

	public List<? extends AlbumI> getAlbums();

	public String getCommentAsString();

}