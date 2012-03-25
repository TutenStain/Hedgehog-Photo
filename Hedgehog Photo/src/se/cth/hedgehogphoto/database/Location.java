package se.cth.hedgehogphoto.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
private String location;
@Id
private String path;

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
	public String getPath(){
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Locations [Path=" + path + ", Location=" + location+ "]";
	}
	
}
