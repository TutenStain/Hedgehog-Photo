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
	
public void setTag(String tag){
		tags.add(tag);
	}
	public void setDate(String date){
		this.date = date;
	}
	public void setComment(String comment){
		this.comment = comment;
	}
	public void setLocation(String location){
		this.location = location;
	}
	public void setImageName(String name){
		this.name = name;
	}
	public void setImagePath(String path){
		this.path = path;
	}
	public void setCoverPath(String coverPath){
		this.coverPath = coverPath;
	}
	public void setAlbumName(String albumName){
		this.albumName = albumName;
	}
	
	
	public List<String> getTags(){
		return tags;
	}
	public String getDate(){
		return date;
	}
	public String getComment(){
		return comment;
	}
	public String getLocation(){
		return location;
	}
	public String getImageName(){
		return name;
	}
	public String getImagePath(){
		return path;
	}
	public String getCoverPath(){
		return coverPath;
	}
	public String getAlbumName(){
		return albumName;
	}
	
	@Override
	public void setTags(List<String> tags) {
		
	}
	
	public String toString(){
		return "[name=" + name + "] [location=" + location + "] [path=" + path + "] [date=" + date + "] [tag=" + tags.toString() + "] [comments= " + comment +"[albumName=" + albumName + "] [coverPath=" + coverPath + "]";
	}
}
