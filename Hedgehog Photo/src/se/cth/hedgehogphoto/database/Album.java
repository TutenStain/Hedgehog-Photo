package se.cth.hedgehogphoto.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long albumID;
	private String coverPath;
	private String name;

	
	public Long getAlbumID(){
		return albumID;
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath= coverPath;
	}

	@Override
	public String toString() {
		return "Pictures [CoverPath=" + coverPath+ ", Name=" + name + "AlbumID="+albumID+ "]";
	}
}
