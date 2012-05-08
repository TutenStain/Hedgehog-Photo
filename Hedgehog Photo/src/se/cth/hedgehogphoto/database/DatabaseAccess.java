package se.cth.hedgehogphoto.database;

import java.util.List;

public interface DatabaseAccess {
	public List<String> getTags(); 
	public void updateSearchPicturesfromTags(String tag);
}
