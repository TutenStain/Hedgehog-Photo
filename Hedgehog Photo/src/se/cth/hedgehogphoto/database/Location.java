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
		return longitude;
	}

	public void setLongitude(double lon) {
		this.longitude = lon;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double lat) {
		this.latitude = lat;
	}

	@OneToMany
	private List<Picture> pictures;
	@OneToMany
	private List<Album> albums;

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
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

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	/**
	 * Returns true if the location has a valid longitude
	 * and latitude-value, ie
	 * 		-180.0 < longitude < 180.0
	 * 		-90.0 < latitude < 90.0
	 * @return true if longitude and latitude are valid values
	 */
	public boolean validPosition() {
		boolean longitudeOK = Math.abs(this.getLongitude()) < 180.0;
		boolean latitudeOK = Math.abs(this.getLatitude()) < 90.0;
		return (longitudeOK && latitudeOK);
	}

}
