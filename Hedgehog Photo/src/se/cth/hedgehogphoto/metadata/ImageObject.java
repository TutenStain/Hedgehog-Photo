package se.cth.hedgehogphoto.metadata;

import se.cth.hedgehogphoto.Location;


public class ImageObject {
	private String filePath, fileName, date, artist, comment, tags;
	private String longitude, latitude;
	private Location location = new Location("test");
	private boolean legitGPSInfo;
	
	public ImageObject() {
		//init
	}
	
	public void setProperty(String property, String value) {
		switch(property) {
			case "Modify Date": setDate(value); break;
			case "Artist": setArtist(value); break;
			case "XPComment": setComment(value); break;
			case "XPKeywords": setTags(value); break;
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

	public String getTags() {
		return tags;
	}

	private void setTags(String tags) {
		this.tags = tags;
	}

	public String getDate() {
		return date;
	}

	private void setDate(String date) {
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

	private void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
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
}
