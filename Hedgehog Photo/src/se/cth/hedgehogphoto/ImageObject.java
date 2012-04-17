package se.cth.hedgehogphoto;

import java.util.List;




public class ImageObject implements FileObject {
	private List<String> tags;
	private String date = "";
	private String comment = "";
	private String fileName = "";
	private String filePath = "";
	private String coverPath = "";
	private String albumName = "";
	private String longitude = "";
	private String latitude = "";
	@Deprecated
	private String artist; /* Keep this for now, even though it is not used in the database. */
	private Location location = new Location("");
	private boolean legitGPSInfo;
	
	public ImageObject() {
		//init
	}
	
	@Override
	public String getAlbumName() {
		return albumName;
	}
	
	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setProperty(String property, String value) {
		switch(property) {
			case "Modify Date": setDate(value); break;
			case "Artist": setArtist(value); break;
			case "XPComment": setComment(value); break;
			case "XPKeywords": setTags(value); break;
			case "File Path": setImagePath(value); break;
			case "File Name": setImageName(value); break;
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

	@Override
	public List<String> getTags() {
		return tags;
	}

	@Override
	public void setTags(String tags) {
		List<String> tagList = Util.convertStringToList(tags, ";");
		this.tags = tagList;
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}

	public String getArtist() {
		return artist;
	}

	private void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getImagePath() {
		return filePath;
	}

	@Override
	public  void setImagePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public String getImageName() {
		return fileName;
	}
	
	@Override
	public void setImageName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String getCoverPath(){
		return coverPath;
	}
	
	@Override
	public void setCoverPath(String coverPath){
		this.coverPath = coverPath;
	}
	
	@Override
	public Location getLocation() {
		return location;
	}
	
	private void setLongitude(String longitude) {
		if (legitGPSInfo)
			location.setLongitude(longitude);
	}
	
	private void setLatitude(String latitude) {
		if (legitGPSInfo)
			location.setLatitude(latitude);
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
	public void setLocation(Location location) {
		this.location=location;
	}
	
	@Override
	public String toString(){
		return "[name=" + name + "] [location=" + longitude +", "+ latitude + "] [path=" + path + "] [date=" + date + "] [tag=" + tags.toString() + "] [comments= " + comment +"] [albumName=" + albumName + "] [coverPath=" + coverPath + "]";
	}
}
