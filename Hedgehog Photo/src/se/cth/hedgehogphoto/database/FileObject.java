package se.cth.hedgehogphoto.database;

import java.util.List;
/**
 * @author Julia
 */
public interface FileObject {
	public void setTags(List<String> tags);
	public void setDate(String date);
	public void setComment(String comment);
	public void setFileName(String name);
	public void setFilePath(String path);
	public void setCoverPath(String path);
	public void setLocation(LocationObject location);
	public void setAlbumName(String albumName);
	public List<String> getTags();
	public String getDate();
	public String getComment();
	public LocationObject getLocation();
	public String getFileName();
	public String getFilePath();
	public String getCoverPath();
	public String getAlbumName();
	public String toString();

}
