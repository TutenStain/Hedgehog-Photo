package se.cth.hedgehogphoto;

import java.util.ArrayList;
import java.util.List;

public class ImageObject implements FileObject{
	private List<String> tags = new ArrayList<String>();
	private String date = "";
	private String comment = "";
	private String location = "";
	private String name = "";
	private String path = "";
	private String coverPath = "";
	private String albumName = "";
	
	/*public ImageObject(List<String> tags, String date, String comment, String location, String path, String coverPath){
		
	}*/
	
	@Override
	public void setTag(String tag){
		tags.add(tag);
	}
	@Override
	public void setDate(String date){
		this.date = date;
	}
	@Override
	public void setComment(String comment){
		this.comment = comment;
	}
	@Override
	public void setLocation(String location){
		this.location = location;
	}
	@Override
	public void setImageName(String name){
		this.name = name;
	}
	@Override
	public void setImagePath(String path){
		this.path = path;
	}
	@Override
	public void setCoverPath(String coverPath){
		this.coverPath = coverPath;
	}
	@Override
	public void setAlbumName(String albumName){
		this.albumName = albumName;
	}
	
	@Override
	public List<String> getTags(){
		return tags;
	}
	@Override
	public String getDate(){
		return date;
	}
	@Override
	public String getComment(){
		return comment;
	}
	@Override
	public String getLocation(){
		return location;
	}
	@Override
	public String getImageName(){
		return name;
	}
	@Override
	public String getImagePath(){
		return path;
	}
	@Override
	public String getCoverPath(){
		return coverPath;
	}
	@Override
	public String getAlbumName(){
		return albumName;
	}
	
	@Override
	public void setTags(List<String> _tags) {
		tags = _tags;
	}
	
	@Override
	public String toString(){
		return "[name=" + name + "] [location=" + location + "] [path=" + path + "] [date=" + date + "] [tag=" + tags.toString() + "] [comments= " + comment +"] [albumName=" + albumName + "] [coverPath=" + coverPath + "]";
	}
}
