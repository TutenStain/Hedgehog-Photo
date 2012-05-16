package se.cth.hedgehogphoto.database;

import java.util.List;

public interface CommentI extends CommentObject {

	public void setPicture(List<? extends PictureI> pictures);

	public void setComment(String comment);

	public void setAlbums(List<? extends AlbumI> albums);

}