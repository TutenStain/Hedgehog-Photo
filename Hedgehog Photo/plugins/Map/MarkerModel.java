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
		setIconPath(Global.MARKER_ICON_PATH); //17x22
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
	protected int getXOffset() {
		return getComponentWidth() / 2;
	}

	@Override
	protected int getYOffset() {
		return getComponentHeight();
	}

	@Override
	public List<PictureObject> getPictures(List<PictureObject> pictures) {
		pictures.add(this.getPicture());
		return pictures;
	}

	@Override
	protected int computeNumberOfLocations() {
		return 1;
	}

	@Override
	protected void handleVisibility() {
		this.setVisible(true);
	}

	@Override
	protected Point.Double getLonglat() {
		return new Point.Double(getLongitude(), getLatitude());
	}
	
	/**
	 * Sets the number of locations (the counter).
	 * Only allows value 1, doesn't set the counter otherwise.
	 */
	@Override
	public void setNumberOfLocations(int numberOfLocations) {
		if (numberOfLocations == 1)
			super.setNumberOfLocations(numberOfLocations);
	}

}
