package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;

/**
 * Logical representation of a "multiple marker".
 * @author Florian
 */
public class MultipleMarkerModel extends AbstractMarkerModel {
	/* Will currently ALWAYS hold 2 markers. */
	private List<AbstractMarkerModel> markers; 
	private Point.Double longlat;
	public MultipleMarkerModel(AbstractMarkerModel markerOne, AbstractMarkerModel markerTwo) {
		this.markers = new ArrayList<AbstractMarkerModel>();
		this.markers.add(markerOne);
		this.markers.add(markerTwo);
		setIconPath(Global.MULTIPLE_MARKER_ICON_PATH);
		initialize();
		setProperPosition();
		handleVisibility();
	}
	
	public List<AbstractMarkerModel> getMarkerModels() {
		return this.markers;
	}
	
	private void setProperPosition() {
		setPointerPosition(computePosition());
	}
	
	private Point computePosition() {
		int xSum = 0;
		int ySum = 0;
		int nbrOfMarkers = markers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			AbstractMarkerModel marker = markers.get(index);
			xSum += marker.getXPointerPosition() * marker.getNumberOfPictures();
			ySum += marker.getYPointerPosition() * marker.getNumberOfPictures();
		}
		int averageX = xSum / this.getNumberOfPictures();
		int averageY = ySum / this.getNumberOfPictures();
		return new Point(averageX, averageY);
	}
	
	public void handleVisibility() {
		AbstractMarkerModel markerOne = markers.get(0);
		AbstractMarkerModel markerTwo = markers.get(1);
		markerOne.handleVisibility(); //let submarkers handle their visibility status first
		markerTwo.handleVisibility(); //if they are invisible, this marker will be that too
		if (markerOne.intersects(markerTwo) && markerOne.isVisible() && markerTwo.isVisible()) {
			this.show();
		} else {
			this.hide();
		}
	}
	
	public void show() {
		markers.get(0).setVisible(false);
		markers.get(1).setVisible(false);
		this.setVisible(true);
	}
	
	public void hide() {
		this.setVisible(false);
	}

	/** Responds to map-actions like drag and zoom. */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		this.markers.get(0).propertyChange(event);
		this.markers.get(1).propertyChange(event);
		if (event.getPropertyName().startsWith(Global.ZOOM)) {
			handleVisibility(); 
		}
	}
	
	@Override
	int getXOffset() {
		return getComponentWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getComponentHeight() / 2;
	}

	@Override
	public List<Picture> getPictures(List<Picture> pictures) {
		int nbrOfMarkers = markers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			pictures = markers.get(index).getPictures(pictures);
		}
		return pictures;
	}

	@Override
	int computeNumberOfPictures() {
		int nbrOfPictures = 0;
		int nbrOfMarkers = markers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			nbrOfPictures += markers.get(index).computeNumberOfPictures();
		}
		return nbrOfPictures;
	}

	@Override
	Point.Double getLonglat() {
		if (this.longlat == null)
			computeLonglat();
		return this.longlat;
	}
	
	private void computeLonglat() {
		List<Picture> pictures = new LinkedList<Picture>();
		pictures = this.getPictures(pictures);
		double averageLongitude = 0.0;
		double averageLatitude = 0.0;
		for (Picture picture : pictures) {
			Location loc = picture.getLocation();
			averageLongitude += loc.getLongitude();
			averageLatitude += loc.getLatitude();
		}
		averageLongitude = averageLongitude / pictures.size();
		averageLatitude = averageLatitude / pictures.size();
		this.longlat = new Point.Double(averageLongitude, averageLatitude);
	}
}
