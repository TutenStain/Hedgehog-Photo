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
public class Location implements LocationObject, LocationI {


	@Id	
	private String location;
	private double longitude, latitude;

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(double lon) {
		this.longitude = lon;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(double lat) {
		this.latitude = lat;
	}

	@OneToMany
	private List<Picture> pictures;
	@OneToMany
	private List<Album> albums;

	@Override
	public List<? extends PictureI> getPictures() {
		return pictures;
	}

	@Override
	public void setPictures(List<? extends PictureI> pictures) {
		this.pictures = (List<Picture>) pictures;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "[Location=" + location+ " Longitude= " + longitude + " Latitude= " + latitude+ "] ";
	}
	@Override
	public String getLocationasString(){
		return location;
	}

	@Override
	public List<? extends AlbumI> getAlbums() {
		return albums;
	}

	@Override
	public void setAlbums(List<? extends AlbumI> albums) {
		this.albums = (List<Album>) albums;
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
