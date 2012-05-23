package se.cth.hedgehogphoto.objects;

import java.util.List;




public class ImageObject implements FileObject {
	private String filePath, fileName, date, artist, coverPath,comment, albumName,location = "";
	private List<String> tags;
	private String tag;

	private LocationGPSObject locationObject = new LocationGPSObject("");
	private boolean legitGPSInfo;

	public ImageObject() {
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

	public String getAlbumName() {
		return this.albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArtist() {
		return this.artist;
	}

	private void setArtist(String artist) {
		this.artist = artist;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public  void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocationGPSObject getLocationObject() {
		return this.locationObject;
	}
	public String getLocation() {
		return this.location;
	}

	private void setLongitude(String longitude) {
		if (legitGPSInfo) {
			locationObject.setLongitude(longitude);
		}
	}

	private void setLatitude(String latitude) {
		if (legitGPSInfo) {
			locationObject.setLatitude(latitude);
		}
	}

	private void setFirstGPSDirection(String value) {
		value = value.trim();
		this.legitGPSInfo = value.equals("\'N\'") || value.equals("\'S\'");
	}

	private void setSecondGPSDirection(String value) {
		value = value.trim();
		this.legitGPSInfo = value.equals("\'W\'") || value.equals("\'E\'");
	}

	@Override
	public void setLocationObject(LocationGPSObject locationObject) {
		this.locationObject=locationObject;
		this.location=locationObject.getLocation();

	}

	@Override
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	@Override
	public String getCoverPath(){
		return this.coverPath;
	}
	
	@Override
	public String toString() {
		return "[name=" + fileName + "] [location=" + location + "] [date=" + date + "] [tag=" + tags + "] [comments= " + comment +"] [albumName=" + albumName + "]" + "Path= " + filePath + "]";
	}
}
