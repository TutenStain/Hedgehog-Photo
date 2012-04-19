package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Location {


@Id	
private String location;
private double longitude, latitude;

public double getLongitude() {
	return latitude;
}

public void setLongitude(double lon) {
	this.latitude = lon;
}

public double getLatitude() {
	return latitude;
}

public void setLatitude(double lat) {
	this.latitude = lat;
}

@OneToMany
private List<Picture> picture;
@OneToMany
private List<Album> album;

	public List<Picture> getPicture() {
	return picture;
}

public void setPicture(List<Picture> picture) {
	this.picture = picture;
}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "[Location=" + location+ " Longitude= " + longitude + " Latitude= " + latitude+ "] ";
	}
	public String getLocationasString(){
		return location;
	}

	public List<Album> getAlbum() {
		return album;
	}

	public void setAlbum(List<Album> album) {
		this.album = album;
	}
	
}
