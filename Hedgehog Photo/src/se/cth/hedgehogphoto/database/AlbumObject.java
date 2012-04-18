package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;
import se.cth.hedgehogphoto.LocationObject;
import se.cth.hedgehogphoto.FileObject;
/**
 * @author Julia
 */
public class AlbumObject implements FileObject{
	List<String> tags = new ArrayList<String>();
	String date = "";
	String comment = "";
	se.cth.hedgehogphoto.LocationObject location;
	String name = "";
	String coverPath = "";
	String albumName = "";


	
	public void setDate(String date){
		this.date = date;
	}
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setName(String name){
		this.name = name;
	}


	@Override
	public void setTags(List<String> tags) {
		this.tags=tags;
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
	public LocationObject getLocation(){
		return location;
	}



	@Override
	public String toString(){
		return "[name=" + name + "] [location=" + location + "] [date=" + date + "] [tag=" + tags + "] [comments= " + comment +"] [albumName=" + albumName + "]";
	}

	@Override
	public void setFileName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFilePath(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(LocationObject location) {
		 this.location = location;
		
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
		
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public String getFilePath() {
		return null;
	}

	@Override
	public String getAlbumName() {
		return albumName;
	}
	@Override
	public void setCoverPath(String coverPath) {
		this.coverPath=coverPath;
		
	}
	@Override
	public String getCoverPath() {
		return coverPath;
	}

}