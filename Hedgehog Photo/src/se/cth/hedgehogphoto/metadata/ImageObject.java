package se.cth.hedgehogphoto.metadata;

import java.util.List;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.Location;
import se.cth.hedgehogphoto.Util;



public class ImageObject implements FileObject {
	private String filePath, fileName, date, artist, comment, albumName, coverPath = "";
	private List<String> tags;
	private String longitude, latitude; /* TODO: Change dependency from these strings, to the location-class! */
	private Location location = new Location(""); /* Should rely on this object. */
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
	
	@Override
	public String getAlbumName() {
		return albumName;
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public List<String> getTags() {
		return tags;
	}

	@Override
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Assuming that multipel tags are separated by a semi-colon (';')
	 */
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
	public String getFilePath() {
		return filePath;
	}

	@Override
	public  void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String toString() {
		return "[name=" + fileName + "] [location=" + location + "] [date=" + date + "] [tag=" + tags + "] [comments= " + comment +"] [albumName=" + albumName + "]" + "Path= " + filePath + "]";
	}

	@Override
	public void setCoverPath(String path) {
		this.coverPath = path;
	}

	@Override
	public String getCoverPath() {
		return coverPath;
	}
}
