package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
import java.util.List;

import se.cth.hedgehogphoto.database.PictureObject;

/**
 * Logical representation of a single marker.
 * @author Florian Minges
 */
public class MarkerModel extends AbstractMarkerModel {
	private PictureObject picture;
	
	public MarkerModel(PictureObject picture) {
		this.picture = picture;
		setIconPath(Global.MARKER_ICON_PATH); //26x26
		initialize();
		handleVisibility();
	}
	
	public PictureObject getPicture() {
		return this.picture;
	}
	
	public double getLongitude() {
		return getPicture().getLocation().getLongitude();
	}
	
	public double getLatitude() {
		return getPicture().getLocation().getLatitude();
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
	public List<PictureObject> getPictures(List<PictureObject> pictures) {
		pictures.add(this.getPicture());
		return pictures;
	}

	@Override
	int computeNumberOfLocations() {
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
