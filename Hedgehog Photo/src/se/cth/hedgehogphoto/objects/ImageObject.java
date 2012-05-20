package se.cth.hedgehogphoto.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;




public class ImageObject implements FileObject {
	private String filePath, fileName, date, artist, coverPath,comment, albumName,location = "";
	private List<String> tags;
	private String tag;

	private LocationObjectOther locationObject = new LocationObjectOther("");
	private boolean legitGPSInfo;

	public ImageObject() {
		//init
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
		System.out.println("location: " + location);
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public LocationObjectOther getLocationObject() {
		return locationObject;
	}
	public String getLocation() {
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
	public void setLocationObject(LocationObjectOther locationObject) {
		this.locationObject=locationObject;
		this.location=locationObject.getLocation();

	}

	@Override
	public String toString() {
		return "[name=" + fileName + "] [location=" + location + "] [date=" + date + "] [tag=" + tags + "] [comments= " + comment +"] [albumName=" + albumName + "]" + "Path= " + filePath + "]";
	}


	public void convertComment() {
		try {
			this.comment = convertDecimalNumbersToString(this.comment);
		}
		catch (NumberFormatException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + this.comment + "\" to a comment.", e);
		}
	}
	
	public void convertTags() {
		try {
			this.tag = convertDecimalNumbersToString(this.tag);
		}
		catch (NumberFormatException e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + this.tag + "\" to a tag.", e);
		}
	}
	
	/**
	 * Converts a string that is on decimal format into a
	 * "normal" string. The string may look something like 
	 * "67, 101, 33, 33"
	 * @param decimalString
	 * @return
	 * @throws NumberFormatException
	 */
	public String convertDecimalNumbersToString(String decimalString) throws NumberFormatException {
		StringBuilder builder = new StringBuilder("");
		List<Integer> i = new ArrayList<Integer>();
		
		String string = decimalString.substring(1);
		for (String s: string.split(", ")) {
			i.add(Integer.parseInt(s));
		}

		for (Integer ii: i) {
			String aChar = Character.valueOf((char)(int)ii).toString();
			aChar = aChar.trim();
			builder.append(aChar);
		}
		
		return builder.toString();
	}


	@Override
	public void setLocation(String location) {
		this.location=location;
		locationObject = new LocationObjectOther(location);

	}

	@Override
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;

	}

	@Override
	public String getCoverPath(){
		return coverPath;
	}


}
