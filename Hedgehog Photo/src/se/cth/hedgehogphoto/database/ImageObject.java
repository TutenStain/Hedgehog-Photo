package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;




public class ImageObject implements FileObject {
	private String filePath, fileName, date, artist, comment, albumName,location = "";
	private List<String> tags;
	private String tag;
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


//	private String longitude, latitude;
	private LocationObject locationObject = new LocationObject("");
	private boolean legitGPSInfo;
	
	public ImageObject() {
		//init
	}
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setProperty(String property, String value) {
		switch(property) {
			case "Modify Date": setDate(value); break;
			case "Artist": setArtist(value); break;
			case "XPComment": setComment(value); break;
			case "XPKeywords": setTag(value); break;
			case "File Path": setFilePath(value); break;
			case "File Name": setFileName(value); break;
			case "Interop Index": setFirstGPSDirection(value); break;
			case "Interop Version": setLatitude(value); break;
			case "Unknown Tag (0x3)": setSecondGPSDirection(value); break;
			case "Unknown Tag (0x4)": setLongitude(value); break;
			
			default: break;
		}
	}
	
	public void print() {
		System.out.println("date: " + date);
		System.out.println("fileName: " + fileName);
		System.out.println("filePath: " + filePath);
		System.out.println("artist: " + artist);
		System.out.println("comment: " + comment);
		System.out.println("tags: " + tags);
		System.out.println("location: " + location.toString());
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArtist() {
		return artist;
	}

	private void setArtist(String artist) {
		this.artist = artist;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilePath() {
		return filePath;
	}

	public  void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public LocationObject getLocationObject() {
		return locationObject;
	}
	public String getLocation(){
		return location;
	}

	
	private void setLongitude(String longitude) {
		if (legitGPSInfo)
			locationObject.setLongitude(longitude);
	}
	
	private void setLatitude(String latitude) {
		if (legitGPSInfo)
			locationObject.setLatitude(latitude);
	}
	
	private void setFirstGPSDirection(String value) {
		value = value.trim();
		legitGPSInfo = value.equals("\'N\'") || value.equals("\'S\'");
	}
	
	private void setSecondGPSDirection(String value) {
		value = value.trim();
		legitGPSInfo = value.equals("\'W\'") || value.equals("\'E\'");
	}

	@Override
	public void setLocationObject(LocationObject locationObject) {
		this.locationObject=locationObject;
		this.location=locationObject.getLocation();
		
	}

	@Override
	public String toString() {
		return "[name=" + fileName + "] [location=" + location + "] [date=" + date + "] [tag=" + tags + "] [comments= " + comment +"] [albumName=" + albumName + "]" + "Path= " + filePath + "]";
	}

	
	public void convertComment(){
		String string = "";
		List<Integer> i = new ArrayList<Integer>();
	//	io.getComment().substring(1);
		try{
		  for(String s:comment.substring(1).split(", ")){
			  i.add(Integer.parseInt(s));
		  }
		  for(Integer ii: i){

		  String aChar = new Character((char)(int)ii).toString();
		//  if(!(aChar.equals("")))
		  string = string + aChar;
		  string = string.trim();
		 
		  }
		}catch(Exception e){
		}
	this.comment =string;
	}
	public void convertTags(){
		String string = "";
		List<Integer> i = new ArrayList<Integer>();
	//	io.getComment().substring(1);
		try{
		
		  for(String s:tag.substring(1).split(", ")){
			  i.add(Integer.parseInt(s));
		  }
		
		  for(Integer ii: i){

		  String aChar = new Character((char)(int)ii).toString();
		  if(!(aChar.equals("")))
		  string = string + aChar;
		  string =  string.trim();
		  }
		  tag = string;
		}
		catch(Exception e){
			System.out.println("Exception tags");
		}
	
	}
	

	@Override
	public void setLocation(String location) {
		this.location=location;
		locationObject = new LocationObject(location);
		
	}

	
}
