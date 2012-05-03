package se.cth.hedgehogphoto.map.model;

import java.util.List;

import se.cth.hedgehogphoto.database.Picture;

/**
 * Logical representation of a single marker.
 * @author Florian
 */
public class MarkerModel extends AbstractMarkerModel {
	private Picture picture;
	
	public MarkerModel(Picture picture) {
		this.picture = picture;
		setIconPath("Pictures/markers/marker2.png"); //26x26
		initialize();
	}
	
	public Picture getPicture() {
		return this.picture;
	}
	
	public double getLongitude() {
		return this.picture.getLocation().getLongitude();
	}
	
	public double getLatitude() {
		return this.picture.getLocation().getLatitude();
	}

	@Override
	int getXOffset() {
		return getComponentWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getComponentHeight();
	}

	@Override
	public List<Picture> getPictures(List<Picture> pictures) {
		pictures.add(this.getPicture());
		return pictures;
	}

	@Override
	int computeNumberOfPictures() {
		return 1;
	}

}
