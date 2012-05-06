package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
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
		setIconPath(Global.MARKER_ICON_PATH); //26x26
		initialize();
		handleVisibility();
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

	@Override
	protected void handleVisibility() {
		this.setVisible(true);
	}

	@Override
	Point.Double getLonglat() {
		return new Point.Double(getLongitude(), getLatitude());
	}

}
