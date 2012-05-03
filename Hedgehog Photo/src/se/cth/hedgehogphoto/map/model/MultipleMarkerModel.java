package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.Picture;

/**
 * Logical representation of a "multiple marker".
 * @author Florian
 */
public class MultipleMarkerModel extends AbstractMarkerModel {
	/* Will currently ALWAYS hold 2 markers. */
	private List<AbstractMarkerModel> markers; 
	public MultipleMarkerModel(AbstractMarkerModel markerOne, AbstractMarkerModel markerTwo) {
		this.markers = new ArrayList<AbstractMarkerModel>();
		this.markers.add(markerOne);
		this.markers.add(markerTwo);
		setIconPath("Pictures/markers/marker.png"); //19x19
		initialize();
		setProperPosition();
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
			xSum += marker.getXPosition() * marker.getNumberOfPictures();
			ySum += marker.getYPosition() * marker.getNumberOfPictures();
		}
		int averageX = xSum / this.getNumberOfPictures();
		int averageY = ySum / this.getNumberOfPictures();
		return new Point(averageX, averageY);
	}
	
	public void handleZoom() {
		AbstractMarkerModel markerOne = markers.get(0);
		AbstractMarkerModel markerTwo = markers.get(1);
		if (markerOne.intersects(markerTwo)) {
			markerOne.setVisible(false);
			markerTwo.setVisible(false);
			this.setVisible(true);
		} else {
			this.setVisible(false);
			/* MultipleMarker in MultipleMarker-handling. */
			if (markerOne instanceof MultipleMarkerModel)
				((MultipleMarkerModel) markerOne).handleZoom();
			else
				markerOne.setVisible(true);
			if (markerTwo instanceof MultipleMarkerModel)
				((MultipleMarkerModel) markerTwo).handleZoom();
			else
				markerTwo.setVisible(true);
		}
	}

	/** Responds to map-actions like drag and zoom. */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		markers.get(0).propertyChange(event);
		markers.get(1).propertyChange(event);
		if (event.getPropertyName().startsWith("zoom")) {
			handleZoom(); 
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
	public void setVisible(boolean visible) {
		if (markers.get(0).isVisible() || 
				markers.get(1).isVisible()) {
			super.setVisible(false);
		} else {
			super.setVisible(visible);
		}
	}

}
