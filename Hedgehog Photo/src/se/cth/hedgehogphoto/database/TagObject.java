package se.cth.hedgehogphoto.database;

import java.util.List;

public interface TagObject {

	public String getTag();

	public List<? extends AlbumI> getAlbums();

	public List<? extends PictureI> getPictures();

	public String getTagAsString();

}