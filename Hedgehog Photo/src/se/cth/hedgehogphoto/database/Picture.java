package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Picture {
	@Id
	private String path;
	private String date;
	private String name;
	private long album_ID;

	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public long getAlbum_ID() {
		return album_ID;
	}

	public void setAlbum_ID(int album_ID) {
		this.album_ID = album_ID;
	}

	
	@Override
	public String toString() {
		return "Pictures [Path=" + path + ", Date=" + date + "Name="+name+"Album_ID="+album_ID
				+ "]";
	}
}
