package se.cth.hedgehogphoto;

import java.util.List;

public interface FileObject {
	public void setTags(List<String> tags);
	public void setDate(String date);
	public void setComment(String comment);
	public void setLocation(String location);
	public void setImageName(String name);
	public void setImagePath(String path);
	public void setCoverPath(String coverPath);
	public void setAlbumName(String albumName);
	public void setTag(String tag);
	public List<String> getTags();
	public String getDate();
	public String getComment();
	public String getLocation();
	public String getImageName();
	public String getImagePath();
	public String getCoverPath();
	public String getAlbumName();
}
