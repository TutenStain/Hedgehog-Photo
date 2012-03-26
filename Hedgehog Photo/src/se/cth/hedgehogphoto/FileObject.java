package se.cth.hedgehogphoto;

import java.util.List;

public interface FileObject {
	public List<String> getTags();
	public String getDate();
	public String getComment();
	public String getLocation();
	public String getImageName();
	public String getImagePath();
	public String getCoverPath();
	public String getAlbumName();
}
