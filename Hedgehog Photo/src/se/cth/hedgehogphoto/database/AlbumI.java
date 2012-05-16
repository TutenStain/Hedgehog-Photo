package se.cth.hedgehogphoto.database;

import java.util.List;

public interface AlbumI extends AlbumObject {

	public void setCoverPath(String coverPath);
	
	public void setPictures(List<Picture> p);
}