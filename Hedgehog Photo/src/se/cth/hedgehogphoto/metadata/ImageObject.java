package se.cth.hedgehogphoto.metadata;


public class ImageObject {
	private String filePath, fileName, date, artist, comment, tags;
	
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
}
