package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Tag implements TagObject, TagI {

@Id	
private String tag;




@ManyToMany
private List<Picture> pictures;

private List<Album> albums;

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setPictures(List<? extends PictureI> pictures) {
		this.pictures = (List<Picture>) pictures;
	}

	@Override
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public List<Album> getAlbums() {
		return albums;
	}

	@Override
	public void setAlbums(List<? extends AlbumI> albums) {
		this.albums = (List<Album>) albums;
	}

	@Override
	public List<? extends PictureI> getPictures() {
		return pictures;
	}
	
	@Override
	public String toString() {
		return "[Tag=" + tag+ "] ";
	}
	@Override
	public String getTagAsString(){
		return tag;
	}
}
