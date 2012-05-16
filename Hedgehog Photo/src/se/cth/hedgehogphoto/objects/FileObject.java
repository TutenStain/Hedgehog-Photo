package se.cth.hedgehogphoto.objects;

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
	public void setLocationObject(LocationObjectOther locationObject);
	public void setAlbumName(String albumName);
	public void setLocation(String location);
	public List<String> getTags();
	public String getDate();
	public String getComment();
	public String getLocation();
	public LocationObjectOther getLocationObject();
	public String getFileName();
	public String getFilePath();
	public String getCoverPath();
	public String getAlbumName();
	public String toString();

}
