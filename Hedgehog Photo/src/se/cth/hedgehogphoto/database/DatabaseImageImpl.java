package se.cth.hedgehogphoto.database;

import java.util.List;

public interface DatabaseImageImpl {
	public String getImagePath(int iID);
	public List<String> getImageTags(int iID);
	public String getImageComment(int iID);
	public String getImageDate(int iID);
}
