package se.cth.hedgehogphoto;

import java.util.List;
import se.cth.hedgehogphoto.Location;
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
	public void setLocation(Location location);
	public void setAlbumName(String albumName);
	public List<String> getTags();
	public String getDate();
	public String getComment();
	public Location getLocation();
	public String getFileName();
	public String getFilePath();
	public String getCoverPath();
	public String getAlbumName();
	public String toString();

}
